package com.example.umc_wireframe.domain.model

data class ShortTermForecastEntity(
    val response: ResponseEntity?
)

data class ResponseEntity(
    val body: BodyEntity?
)

data class BodyEntity(
    val items: ItemsEntity?
)

data class ItemsEntity(
    val item: List<WeatherItemEntity>
)

data class WeatherItemEntity(
    val baseDate: String,
    val baseTime: String,
    val category: ShortTermCategory,  // 예보 카테고리 (TMP, POP 등)
    val fcstDate: String,
    val fcstTime: String,
    val fcstValue: String, // 예보 값 (온도, 강수확률 등)
    val nx: Int,
    val ny: Int
)
