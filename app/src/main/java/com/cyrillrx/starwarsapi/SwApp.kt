package com.cyrillrx.starwarsapi

import android.app.Application
import com.cyrillrx.logger.Logger
import com.cyrillrx.logger.Severity
import com.cyrillrx.logger.extension.LogCat
import com.cyrillrx.swapi.SwApi
import com.cyrillrx.swapi.SwApiFactory
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient

/**
 * @author Cyril Leroux
 *         Created on 06/04/2018.
 */
class SwApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)

        Logger.initialize()
        val logSeverity = if (BuildConfig.DEBUG) Severity.VERBOSE else Severity.FATAL
        Logger.addChild(LogCat(logSeverity))

        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggerInterceptor())
                .addNetworkInterceptor(StethoInterceptor())
                .build()

        swApi = SwApiFactory.init(okHttpClient)
    }

    companion object {
        lateinit var swApi: SwApi
    }
}