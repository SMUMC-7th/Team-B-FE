package com.example.umc_wireframe.domain.model

data class ShortTermForecastEntity(
    val header: HeaderEntity,
    val body: BodyEntity
)

data class HeaderEntity(
    val resultCode: String,
    val resultMsg: String
)

data class BodyEntity(
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




