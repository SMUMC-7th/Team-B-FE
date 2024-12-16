package com.example.umc_wireframe.util

import android.content.Context
import android.content.SharedPreferences
import com.example.umc_wireframe.presentation.UmcClothsOfTempApplication

class SharedPreferencesManager(context: Context) {
    companion object {
        private const val PREFS_NAME = "app_prefs" // SharedPreferences 파일 이름
        private const val KEY_ACCESS_TOKEN = "access_token" // Access Token 키
        private const val KEY_REFRESH_TOKEN = "refresh_token" // Refresh Token 키
        private const val KEY_VERIFICATION_CODE = "verification_code" // 인증 코드 키

        private val prefs: SharedPreferences = UmcClothsOfTempApplication.context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    // Access Token 저장
    fun saveAccessToken(token: String) {
        prefs.edit().putString(KEY_ACCESS_TOKEN, token).apply()
    }

    // Access Token 가져오기
    fun getAccessToken(): String? {
        return prefs.getString(KEY_ACCESS_TOKEN, null)
    }

    // Access Token 삭제
    fun clearAccessToken() {
        prefs.edit().remove(KEY_ACCESS_TOKEN).apply()
    }

    // Refresh Token 저장
    fun saveRefreshToken(token: String) {
        prefs.edit().putString(KEY_REFRESH_TOKEN, token).apply()
    }

    // 이 아래부터는 필요 없나 싶음

    // Refresh Token 가져오기
    fun getRefreshToken(): String? {
        return prefs.getString(KEY_REFRESH_TOKEN, null)
    }

    // Refresh Token 삭제
    fun clearRefreshToken() {
        prefs.edit().remove(KEY_REFRESH_TOKEN).apply()
    }

    // 인증 코드 저장
    fun saveVerificationCode(code: String) {
        prefs.edit().putString(KEY_VERIFICATION_CODE, code).apply()
    }

    // 인증 코드 가져오기
    fun getVerificationCode(): String? {
        return prefs.getString(KEY_VERIFICATION_CODE, null)
    }

    // 인증 코드 삭제
    fun clearVerificationCode() {
        prefs.edit().remove(KEY_VERIFICATION_CODE).apply()
    }

    // 모든 데이터 삭제 (로그아웃 등)
    fun clearAll() {
        prefs.edit().clear().apply()
    }
}
