package com.tony.journeytest.restApi

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiServiceBuilder {
    private const val connectTimeout = 60L
    private const val readTimeout = 60L

    fun getApiService(baseUrl: String) : IJsonPlaceholderApi {
        val httpClient = getHttpClientBuilder().build()

        return getRetrofitBuilder(baseUrl, httpClient)
            .build()
            .create(IJsonPlaceholderApi::class.java)
    }

    private fun getHttpClientBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)
    }

    private fun getRetrofitBuilder(baseUrl: String, httpClient: OkHttpClient) : Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
    }
}