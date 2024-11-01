package com.example.umc_wireframe.domain.model.entity

import com.example.umc_wireframe.domain.model.ShortTermCategory

data class ShortTermForecastEntity(
    val header: ShortTermHeaderEntity,
    val body: ShortTermBodyEntity
)

data class ShortTermHeaderEntity(
    val resultCode: String,
    val resultMsg: String
)

data class ShortTermBodyEntity(
    val items: List<ShortTermForecastItemEntity>,
    val pageNo: Int,
    val numOfRows: Int,
    val totalCount: Int
)

data class ShortTermForecastItemEntity(
    val date: String,   // 기준 날짜 (YYYYMMDD)
    val time: String,   // 기준 시간 (HHmm)
    val category: ShortTermCategory, // 예: "TMP" (기온)
    val value: Double, // 관측 값 (기온)
    val locationX: Int,  // X 좌표
    val locationY: Int   // Y 좌표
)




