package com.cyrillrx.starwarsapi;

import com.cyrillrx.logger.Logger;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;

/**
 * @author Cyril Leroux
 *         Created 26/11/2016.
 */
public class HttpLoggerInterceptor implements Interceptor {

    private static final String TAG = HttpLoggerInterceptor.class.getSimpleName();

    private static final Charset UTF8 = Charset.forName("UTF-8");

    public enum Level {
        /** No logs. */
        NONE,
        /**
         * Logs request and response lines.
         * <p>
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting http/1.1 (3-byte body)
         *
         * <-- 200 OK (22ms, 6-byte body)
         * }</pre>
         */
        BASIC,
        /**
         * Logs request and response lines and their respective headers.
         * <p>
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting http/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         * --> END POST
         *
         * <-- 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         * <-- END HTTP
         * }</pre>
         */
        HEADERS,
        /**
         * Logs request and response lines and their respective headers and bodies (if present).
         * <p>
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting http/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         *
         * Hi?
         * --> END POST
         *
         * <-- 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         *
         * Hello!
         * <-- END HTTP
         * }</pre>
         */
        BODY
    }

    private volatile Level level;

    public HttpLoggerInterceptor() { level = Level.NONE; }

    public HttpLoggerInterceptor(Level level) { setLevel(level); }

    /**
     * Change the level at which this interceptor logs.
     */
    public HttpLoggerInterceptor setLevel(Level level) {
        if (level == null) {
            throw new NullPointerException("level == null. Use Level.NONE instead.");
        }
        this.level = level;
        return this;
    }

    public Level getLevel() { return level; }

    @Override
    public Response intercept(Chain chain) throws IOException {

        final Level level = this.level;

        final Request request = chain.request();

        if (level == Level.NONE) {
            return chain.proceed(request);
        }

        final boolean logBody = level == Level.BODY;
        final boolean logHeaders = logBody || level == Level.HEADERS;

        final RequestBody requestBody = request.body();
        final boolean hasRequestBody = requestBody != null;

        final Connection connection = chain.connection();
        final Protocol protocol = connection != null ? connection.protocol() : Protocol.HTTP_1_1;

        final StringBuilder logTrace = new StringBuilder();

        // Message header
        logTrace.append("--> ").append(request.method()).append(' ').append(request.url()).append(' ').append(protocol);
        if (!logHeaders && hasRequestBody) {
            logTrace.append(" (")
                    .append(requestBody.contentLength())
                    .append("-byte body)");
        }

        logTrace.append("\n");

        if (logHeaders) {
            if (hasRequestBody) {
                // Request body headers are only present when installed as a network interceptor.
                // Force them to be included (when available) so there values are known.
                if (requestBody.contentType() != null) {
                    logTrace.append("Content-Type: ")
                            .append(requestBody.contentType())
                            .append("\n");
                }

                if (requestBody.contentLength() != -1) {
                    logTrace.append("Content-Length: ")
                            .append(requestBody.contentLength())
                            .append("\n");
                }
            }

            final Headers headers = request.headers();
            for (int i = 0, count = headers.size(); i < count; i++) {
                final String name = headers.name(i);
                // Skip headers from the request body as they are explicitly logged above.
                if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                    logTrace.append(name)
                            .append(": ")
                            .append(headers.value(i))
                            .append("\n");
                }
            }

            if (!logBody || !hasRequestBody) {
                logTrace.append("--> END ")
                        .append(request.method())
                        .append("\n");

            } else if (bodyEncoded(request.headers())) {
                logTrace.append("--> END ")
                        .append(request.method())
                        .append(" (encoded body omitted)")
                        .append("\n");

            } else {
                final Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);

                Charset charset = UTF8;
                final MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }

                logTrace.append("\n");

                if (isPlaintext(buffer)) {
                    logTrace.append(buffer.readString(charset)).append("\n");
                    logTrace.append("--> END ")
                            .append(request.method())
                            .append(" (")
                            .append(requestBody.contentLength())
                            .append("-byte body)")
                            .append("\n");

                } else {
                    logTrace.append("--> END ")
                            .append(request.method())
                            .append(" (binary ")
                            .append(requestBody.contentLength())
                            .append("-byte body omitted)")
                            .append("\n");
                }
            }
        }

        final long startNs = System.nanoTime();
        Response response;
        try {
            response = chain.proceed(request);

        } catch (Exception e) {
            logTrace.append("<-- HTTP FAILED: ")
                    .append(e)
                    .append("\n");
            Logger.debug(TAG, logTrace.toString());
            throw e;
        }

        final long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        final ResponseBody responseBody = response.body();
        final long contentLength = responseBody.contentLength();
        final String bodySize = contentLength != -1 ? contentLength + "-byte" : "unknown-length";

        logTrace.append("<-- ")
                .append(response.code())
                .append(' ')
                .append(response.message())
                .append(' ')
                .append(response.request().url())
                .append(" (").append(tookMs).append("ms")
                .append(!logHeaders ? ", " + bodySize + " body" : "")
                .append(')').append("\n");

        if (logHeaders) {
            final Headers headers = response.headers();
            for (int i = 0, count = headers.size(); i < count; i++) {
                logTrace.append(headers.name(i))
                        .append(": ")
                        .append(headers.value(i))
                        .append("\n");
            }

            if (!logBody || !HttpHeaders.hasBody(response)) {
                logTrace.append("<-- END HTTP")
                        .append("\n");

            } else if (bodyEncoded(response.headers())) {
                logTrace.append("<-- END HTTP (encoded body omitted)")
                        .append("\n");

            } else {
                final BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                final Buffer buffer = source.buffer();

                Charset charset = UTF8;
                final MediaType contentType = responseBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }

                if (!isPlaintext(buffer)) {
                    logTrace.append("\n");
                    logTrace.append("<-- END HTTP (binary ")
                            .append(buffer.size())
                            .append("-byte body omitted)")
                            .append("\n");

                    Logger.debug(TAG, logTrace.toString());
                    return response;
                }

                if (contentLength != 0) {
                    logTrace.append("\n");
                    logTrace.append(buffer.clone().readString(charset))
                            .append("\n");
                }

                logTrace.append("<-- END HTTP (")
                        .append(buffer.size())
                        .append("-byte body)")
                        .append("\n");
            }
        }

        Logger.debug(TAG, logTrace.toString());
        return response;
    }

    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    private static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    private boolean bodyEncoded(Headers headers) {
        final String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }
}