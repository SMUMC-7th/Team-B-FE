package com.example.umc_wireframe.data.repository

import com.example.umc_wireframe.data.remote.MidTermForecastDatasource
import com.example.umc_wireframe.domain.model.entity.MidTermForecastEntity
import com.example.umc_wireframe.domain.model.mapper.toEntity
import com.example.umc_wireframe.domain.repository.MidTermForecastRepository

class MidTermForecastRepositoryImpl(
    private val datasource: MidTermForecastDatasource
): MidTermForecastRepository {
    override suspend fun getWeatherForecast(
        pageNo: Int,
        numOfRows: Int,
        dataType: String,
        regId: String,
        tmFc: String
    ): MidTermForecastEntity = datasource.getWeatherForecast(
        pageNo = pageNo,
        numOfRows = numOfRows,
        dataType = dataType,
        regId = regId,
        tmFc = tmFc
    ).toEntity()
}