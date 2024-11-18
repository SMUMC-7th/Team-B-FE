package com.example.umc_wireframe.network

import com.example.umc_wireframe.data.remote.MidTermForecastDatasource
import com.example.umc_wireframe.data.remote.ServerDatasource
import com.example.umc_wireframe.data.remote.ShortTermForecastDatasource
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

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
            .addInterceptor(AuthorizationInterceptor(AuthorizationType.SHORT_TERM_FORECAST))
            .build()
    }

    private val shortTermRetrofit by lazy {
        Retrofit.Builder()
            .baseUrl(SHORT_TERM_FORECAST_BASE_URL)
            .client(shortTermOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    private val midTermOkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(AuthorizationInterceptor(AuthorizationType.MID_TERM_FORECAST))
            .build()
    }

    private val midTermRetrofit by lazy {
        Retrofit.Builder()
            .baseUrl(MID_TERM_FORECAST_BASE_URL)
            .client(midTermOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    private val serverOkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    private val serverRetrofit by lazy {
        Retrofit.Builder()
            .baseUrl(SERVER_BASE_URL)
            .client(serverOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }


    val shortTermForecastDatasource: ShortTermForecastDatasource by lazy {
        shortTermRetrofit.create(ShortTermForecastDatasource::class.java)
    }

    val midTermForecastDatasource: MidTermForecastDatasource by lazy {
        midTermRetrofit.create(MidTermForecastDatasource::class.java)
    }

    val serverDatasource: ServerDatasource by lazy {
        serverRetrofit.create(ServerDatasource::class.java)
    }
}