package com.example.umc_wireframe.network

import com.example.umc_wireframe.data.remote.ServerDatasource
import com.example.umc_wireframe.data.remote.ShortTermForecastDatasource
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    const val SHORT_TERM_FORECAST_BASE_URL =
        "https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/"

    const val MID_TERM_FORECAST_BASE_URL = "https://apis.data.go.kr/1360000/MidFcstInfoService/"

    const val SERVER_BASE_URL = "http://43.202.248.120:8080/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val shortTermOkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(AuthorizationInterceptor())
            .build()
    }

    private val shortTermRetrofit by lazy {
        Retrofit.Builder()
            .baseUrl(SHORT_TERM_FORECAST_BASE_URL)
            .client(shortTermOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val serverOkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(AuthorizationInterceptor())
            .authenticator(TokenAuthenticator(TokenManager()))
            .build()
    }

    private val serverRetrofit by lazy {
        Retrofit.Builder()
            .baseUrl(SERVER_BASE_URL)
            .client(serverOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    val shortTermForecastDatasource: ShortTermForecastDatasource by lazy {
        shortTermRetrofit.create(ShortTermForecastDatasource::class.java)
    }


    val serverDatasource: ServerDatasource by lazy {
        serverRetrofit.create(ServerDatasource::class.java)
    }

    val refreshDatasource = Retrofit.Builder().baseUrl(SERVER_BASE_URL).client(
        OkHttpClient.Builder().addInterceptor(
            loggingInterceptor
        ).build()
    ).addConverterFactory(GsonConverterFactory.create()).build().create(ServerDatasource::class.java)
}