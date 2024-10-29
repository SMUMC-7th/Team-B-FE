package com.example.umc_wireframe.domain.repository

import com.example.umc_wireframe.domain.model.entity.MidTermForecastEntity

interface MidTermForecastRepository {
    suspend fun getWeatherForecast(
        pageNo: Int = 1,
        numOfRows: Int = 10,
        dataType: String = "JSON",
        stnId: String,
        tmFc: String // 일 2회(06:00,18:00)회 생성 되며 발표시각을 입력 YYYYMMDD0600 (1800)-최근 24시간 자료만 제공 -> 201310170600
    ): MidTermForecastEntity
}