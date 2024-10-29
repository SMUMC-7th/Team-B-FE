package com.example.umc_wireframe.domain.repository

import com.example.umc_wireframe.data.repository.ShortTermForecastRepositoryImpl
import com.example.umc_wireframe.network.RetrofitClient

class RepositoryFactory {
    fun createShortTermForecastRepository(): ShortTermForecastRepository {
        val datasource = RetrofitClient.shortTermForecastDatasource
        return ShortTermForecastRepositoryImpl(datasource)
    }
}
