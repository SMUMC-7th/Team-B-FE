package com.example.umc_wireframe.network

import com.example.umc_wireframe.presentation.UmcClothsOfTempApplication
import com.example.umc_wireframe.util.SharedPreferencesManager

class NetworkSettings private constructor() {
    var token: String = ""
        set(value) {
            field = value
            sharedPreferencesManager.saveAccessToken(value)
        }

    companion object {
        private val sharedPreferencesManager = SharedPreferencesManager(
            UmcClothsOfTempApplication.context
        )

        private var instance: NetworkSettings? = null
        @JvmStatic
        fun getInstance(): NetworkSettings {
            if (instance == null) {
                instance = NetworkSettings().apply {
                    // 앱 시작 시 저장된 토큰 로드
                    token = sharedPreferencesManager.getAccessToken() ?: ""
                }
            }
            return instance!!
        }
    }
}
