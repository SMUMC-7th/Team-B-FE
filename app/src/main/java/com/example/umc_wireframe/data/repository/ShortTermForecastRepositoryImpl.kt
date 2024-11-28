package com.example.umc_wireframe.data.repository

import com.example.umc_wireframe.data.remote.ShortTermForecastDatasource
import com.example.umc_wireframe.domain.model.ShortTermCategory
import com.example.umc_wireframe.domain.model.entity.ShortTermBodyEntity
import com.example.umc_wireframe.domain.model.entity.ShortTermForecastEntity
import com.example.umc_wireframe.domain.model.entity.ShortTermHeaderEntity
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

    override suspend fun getMaxMinTemp(
        pageNo: Int,
        numOfRows: Int,
        dataType: String,
        baseDate: String,
        baseTime: String,
        nx: Int,
        ny: Int
    ): ShortTermForecastEntity {
        val items = datasource.getWeatherForecast(
            pageNo,
            numOfRows,
            dataType,
            baseDate,
            baseTime,
            nx,
            ny
        ).toEntity().body.items.filter { it.category == ShortTermCategory.TMP }

        // Double로 비교하여 최대값과 최소값을 찾습니다.
        val maxItem = items.maxBy { it.value }
        val minItem = items.minBy { it.value }

        // 최대값과 최소값을 ShortTermForecastEntity에 포함하여 반환
        return ShortTermForecastEntity(
            header = ShortTermHeaderEntity(resultCode = "00", resultMsg = "Success"),
            body = ShortTermBodyEntity(
                items = listOf(maxItem, minItem),
                pageNo = pageNo,
                numOfRows = numOfRows,
                totalCount = items.size
            )
        )
    }
}