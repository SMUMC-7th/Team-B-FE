package com.example.umc_wireframe.data.repository

import com.example.umc_wireframe.data.remote.ShortTermForecastDatasource
import com.example.umc_wireframe.domain.model.entity.ShortTermForecastEntity
import com.example.umc_wireframe.domain.model.mapper.toEntity
import com.example.umc_wireframe.domain.repository.ShortTermForecastRepository

class ShortTermForecastRepositoryImpl(
    private val datasource: ShortTermForecastDatasource
) : ShortTermForecastRepository {
    override suspend fun getWeatherForecast(
        pageNo: Int,
        numOfRows: Int,
        dataType: String,
        baseDate: String,
        baseTime: String,
        nx: Int,
        ny: Int
    ): ShortTermForecastEntity = datasource.getWeatherForecast(
        pageNo,
        numOfRows,
        dataType,
        baseDate,
        baseTime,
        nx,
        ny
    ).toEntity()
}