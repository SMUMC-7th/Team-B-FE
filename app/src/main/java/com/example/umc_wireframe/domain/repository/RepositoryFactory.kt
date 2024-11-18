package com.example.umc_wireframe.domain.repository

import com.example.umc_wireframe.data.repository.MidTermForecastRepositoryImpl
import com.example.umc_wireframe.data.repository.OotdRepositoryImpl
import com.example.umc_wireframe.data.repository.ShortTermForecastRepositoryImpl
import com.example.umc_wireframe.network.RetrofitClient

class RepositoryFactory {
    fun createShortTermForecastRepository(): ShortTermForecastRepository {
        val datasource = RetrofitClient.shortTermForecastDatasource
        return ShortTermForecastRepositoryImpl(datasource)
    }

    fun createMidTermForecastRepository(): MidTermForecastRepository {
        val datasource = RetrofitClient.midTermForecastDatasource
        return MidTermForecastRepositoryImpl(datasource)
    }

    fun createOotdRepository(): OotdRepository {
        val datasource = RetrofitClient.serverDatasource
        return OotdRepositoryImpl(datasource)
    }
}
