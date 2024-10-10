package com.example.umc_wireframe.data.model

data class ShortTermForecastResponse(
    val response: Response?
)

data class Response(
    val body: BodyResponse?
)

data class BodyResponse(
    val items: ItemsResponse?
)

data class ItemsResponse(
    val item: List<WeatherItemResponse>?
)

data class WeatherItemResponse(
    val baseDate: String?,
    val baseTime: String?,
    val category: String?,  // 예보 카테고리 (TMP, POP 등)
    val fcstDate: String?,
    val fcstTime: String?,
    val fcstValue: String?, // 예보 값 (온도, 강수확률 등)
    val nx: Int?,
    val ny: Int?
)
