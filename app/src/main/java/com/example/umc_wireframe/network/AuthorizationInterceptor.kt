package com.example.umc_wireframe.network

import android.content.Context
import com.example.umc_wireframe.BuildConfig
import com.example.umc_wireframe.util.SharedPreferencesManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        // 네트워크 요청에 ServiceKey 추가
        val newUrl = chain.request().url.newBuilder()
            .addQueryParameter("ServiceKey", BuildConfig.SHORT_TERM_FORECAST_KEY)
            .build()

        // NetworkSettings 싱글톤에서 최신 토큰 가져오기
        val token = NetworkSettings.getInstance().token

        // Authorization 헤더 추가
        val newRequest = chain.request().newBuilder()
            .url(newUrl)
            .apply {
                if (token.isNotEmpty()) {
                    addHeader("Authorization", "Bearer $token")
                }
            }
            .build()

        return chain.proceed(newRequest)
    }
}


