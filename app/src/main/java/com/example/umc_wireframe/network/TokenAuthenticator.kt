package com.example.umc_wireframe.network

import com.example.umc_wireframe.domain.repository.MemberRepository
import com.example.umc_wireframe.util.SharedPreferencesManager
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route


class TokenAuthenticator(
    private val tokenManager: TokenManager
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        // 새 토큰 요청
        val newAccessToken = try {
            runBlocking { tokenManager.refreshAccessToken() }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        // 새 토큰 없으면 null 반환 (인증 실패)
        return if (newAccessToken != null) {
            response.request.newBuilder()
                .header("Authorization", "Bearer $newAccessToken")
                .build()
        } else {
            null
        }
    }
}




