package com.example.umc_wireframe.data.repository

import com.example.umc_wireframe.data.remote.ShortTermForecastDatasource
import com.example.umc_wireframe.domain.model.ShortTermForecastEntity
import com.example.umc_wireframe.domain.model.toEntity
import com.example.umc_wireframe.domain.repository.ShortTermForecastRepository
import javax.inject.Inject

class ShortTermForecastRepositoryImpl @Inject constructor(
    private val datasource: ShortTermForecastDatasource
) : ShortTermForecastRepository {
    override suspend fun getRegionalTemperature(
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