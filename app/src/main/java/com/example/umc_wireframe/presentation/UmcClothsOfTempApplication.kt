package com.example.umc_wireframe.presentation

import android.app.Application
import android.content.Context
import com.kakao.sdk.common.KakaoSdk


class UmcClothsOfTempApplication : Application() {
    companion object {
        lateinit var instance: UmcClothsOfTempApplication
            private set

        val context: Context
            get() = instance.applicationContext
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        // Kakao Sdk 초기화
        KakaoSdk.init(this, "3faf19abb28143602dc14674582ae874")
    }
}