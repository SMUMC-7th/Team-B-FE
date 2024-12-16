package com.example.umc_wireframe.network

import android.widget.Toast
import com.example.umc_wireframe.data.remote.RefreshToken
import com.example.umc_wireframe.presentation.UmcClothsOfTempApplication
import com.example.umc_wireframe.util.SharedPreferencesManager

class TokenManager(
) {
    private val preferencesManager = SharedPreferencesManager(UmcClothsOfTempApplication.context)
    private val repository = RetrofitClient.refreshDatasource

    suspend fun refreshAccessToken(): String? {
        // 저장된 refreshToken 가져오기
        val refreshToken = preferencesManager.getRefreshToken()

        // refreshToken이 없으면 갱신 불가능
        if (refreshToken.isNullOrEmpty()) return null

        return try {
            // 갱신 API 호출
            val response = repository.postRefreshToken(RefreshToken(refreshToken))

            if (response?.isSuccess == true) {
                // 새 토큰 저장
                response.result?.let { result ->
                    result?.accessToken?.let { newAccessToken ->
                        preferencesManager.saveAccessToken(newAccessToken)
                    }
                    result?.refreshToken?.let { newRefreshToken ->
                        preferencesManager.saveRefreshToken(newRefreshToken)
                    }
                }
                preferencesManager.getAccessToken()
            } else {
                null // 갱신 실패
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
