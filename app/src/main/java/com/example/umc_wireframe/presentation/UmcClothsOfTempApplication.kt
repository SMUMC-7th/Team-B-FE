package com.example.umc_wireframe.presentation

import android.app.Application
import android.content.Context


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
    }
}