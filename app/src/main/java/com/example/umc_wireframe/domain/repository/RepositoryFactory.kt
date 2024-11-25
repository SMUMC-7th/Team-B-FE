package com.example.umc_wireframe.domain.repository

import com.example.umc_wireframe.data.repository.MemberRepositoryImpl
import com.example.umc_wireframe.data.repository.MidTermForecastRepositoryImpl
import com.example.umc_wireframe.data.repository.OotdRepositoryImpl
import com.example.umc_wireframe.data.repository.ShortTermForecastRepositoryImpl
import com.example.umc_wireframe.network.RetrofitClient

object RepositoryFactory {
    fun createShortTermForecastRepository(): ShortTermForecastRepository {
        val datasource = RetrofitClient.shortTermForecastDatasource
        return ShortTermForecastRepositoryImpl(datasource)
    }

    fun createOotdRepository(): OotdRepository {
        return OotdRepositoryImpl(RetrofitClient.serverDatasource)
    }

    fun createMemberRepository(): MemberRepository{
        return MemberRepositoryImpl(RetrofitClient.serverDatasource)
    }
}
