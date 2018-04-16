package com.cyrillrx.swapi

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Cyril Leroux
 *          Created on 04/04/2018.
 */
object SwApiFactory {

    fun init(okHttpClient: OkHttpClient): SwApi {

        val retrofit = Retrofit.Builder()
                .baseUrl("https://swapi.co/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        return retrofit.create(SwApi::class.java)
    }
}
