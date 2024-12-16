package com.example.umc_wireframe.network

import android.content.Context
import com.example.umc_wireframe.BuildConfig
import com.example.umc_wireframe.presentation.UmcClothsOfTempApplication
import com.example.umc_wireframe.util.SharedPreferencesManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(
    private val isForcast:Boolean
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        // 네트워크 요청에 ServiceKey 추가
        val newUrl = chain.request().url.newBuilder()
            .apply {
                if(isForcast) addQueryParameter("ServiceKey", BuildConfig.SHORT_TERM_FORECAST_KEY)
            }.build()

         val sharedPreferencesManager = SharedPreferencesManager(
            UmcClothsOfTempApplication.context
        )
        val token: String = sharedPreferencesManager.getAccessToken() ?: ""

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


