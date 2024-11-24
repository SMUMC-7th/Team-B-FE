package com.example.umc_wireframe.network

import android.content.Context
import com.example.umc_wireframe.BuildConfig
import com.example.umc_wireframe.util.SharedPreferencesManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(
    private val context: Context,
    private val type: AuthorizationType
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val sharedPreferencesManager = SharedPreferencesManager(context)
        val accessToken = sharedPreferencesManager.getAccessToken() // 저장된 토큰 가져오기

        val newUrl = chain.request().url.newBuilder()
            .apply {
                when (type) {
                    AuthorizationType.SHORT_TERM_FORECAST -> {
                        addQueryParameter("ServiceKey", BuildConfig.SHORT_TERM_FORECAST_KEY)
                    }
                    AuthorizationType.MID_TERM_FORECAST -> {
                        addQueryParameter("ServiceKey", BuildConfig.MID_TERM_FORECAST_KEY)
                    }
                }
            }
            .build()

        // Authorization 헤더 추가
        val newRequest = chain.request().newBuilder()
            .url(newUrl)
            .apply {
                if (!accessToken.isNullOrEmpty()) {
                    addHeader("Authorization", "Bearer $accessToken")
                }
            }
            .build()

        return chain.proceed(newRequest)
    }
}

// AuthorizationType Enum 정의
enum class AuthorizationType {
    SHORT_TERM_FORECAST,  // 단기 예보
    MID_TERM_FORECAST     // 중기 예보
}
