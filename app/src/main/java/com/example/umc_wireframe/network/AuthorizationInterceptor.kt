package com.example.umc_wireframe.network

import com.example.umc_wireframe.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response


class AuthorizationInterceptor(
    private val type: AuthorizationType
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newUrl = chain.request().url.newBuilder()
            .apply {
                when (type) {
                    AuthorizationType.SHORT_TERM_FORECAST ->
                        addQueryParameter("ServiceKey", BuildConfig.SHORT_TERM_FORECAST_KEY)

                    AuthorizationType.MID_TERM_FORECAST ->
                        addQueryParameter("ServiceKey", BuildConfig.MID_TERM_FORECAST_KEY)
                }
            }
            .build()

        val newRequest = chain.request().newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}

enum class AuthorizationType {
    SHORT_TERM_FORECAST,  // 단기 예보
    MID_TERM_FORECAST,    // 중기 예보
}

