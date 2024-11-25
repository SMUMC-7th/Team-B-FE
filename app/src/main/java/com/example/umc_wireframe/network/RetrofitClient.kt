package com.example.umc_wireframe.network

import com.example.umc_wireframe.data.remote.ServerDatasource
import com.example.umc_wireframe.data.remote.ShortTermForecastDatasource
import com.example.umc_wireframe.presentation.UmcClothsOfTempApplication
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    const val SHORT_TERM_FORECAST_BASE_URL =
        "https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/"


    const val SERVER_BASE_URL = "http://43.202.248.120:8080/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }


    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(AuthorizationInterceptor(UmcClothsOfTempApplication.context))
            .build()
    }

    private val shortTermRetrofit by lazy {
        Retrofit.Builder()
            .baseUrl(SHORT_TERM_FORECAST_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val serverRetrofit by lazy {
        Retrofit.Builder()
            .baseUrl(SERVER_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    val shortTermForecastDatasource: ShortTermForecastDatasource by lazy {
        shortTermRetrofit.create(ShortTermForecastDatasource::class.java)
    }


    val serverDatasource: ServerDatasource by lazy {
        serverRetrofit.create(ServerDatasource::class.java)
    }
}