package com.example.umc_wireframe.domain.repository

import com.example.umc_wireframe.domain.model.entity.ShortTermForecastEntity

interface ShortTermForecastRepository {
    suspend fun getWeatherForecast(
        pageNo: Int,
        numOfRows: Int = 12, // 1시간당 12개의 카테고리
        dataType: String = "JSON",
        baseDate: String,  // 기준 날짜 (yyyyMMdd)
        baseTime: String,  // 기준 시간 (HHmm) - 0200, 0500, 0800, 1100, 1400, 1700, 2000, 2300 만 가능
        nx: Int,
        ny: Int
    ): ShortTermForecastEntity

    suspend fun getMaxMinTemp(
        pageNo: Int,
        numOfRows: Int = 12, // 1시간당 12개의 카테고리
        dataType: String = "JSON",
        baseDate: String,  // 기준 날짜 (yyyyMMdd)
        baseTime: String,  // 기준 시간 (HHmm) - 0200, 0500, 0800, 1100, 1400, 1700, 2000, 2300 만 가능
        nx: Int,
        ny: Int
    ): ShortTermForecastEntity
}