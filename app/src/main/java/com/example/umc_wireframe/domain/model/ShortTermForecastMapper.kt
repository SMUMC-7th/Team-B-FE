package com.example.umc_wireframe.domain.model

import com.example.umc_wireframe.data.model.*

fun ShortTermForecastResponse.toEntity(): ShortTermForecastEntity {
    return ShortTermForecastEntity(
        response = response?.toEntity()
    )
}

fun Response.toEntity(): ResponseEntity {
    return ResponseEntity(
        body = body?.toEntity()
    )
}

fun BodyResponse.toEntity(): BodyEntity {
    return BodyEntity(
        items = items?.toEntity()
    )
}

fun ItemsResponse.toEntity(): ItemsEntity {
    return ItemsEntity(
        item = item?.map { it.toEntity() } ?: emptyList()
    )
}

fun WeatherItemResponse.toEntity(): WeatherItemEntity {
    return WeatherItemEntity(
        baseDate = this.baseDate ?: "",
        baseTime = this.baseTime ?: "",
        category = this.category?.toShortTermCategory() ?: ShortTermCategory.ERROR,
        fcstDate = this.fcstDate ?: "",
        fcstTime = this.fcstTime ?: "",
        fcstValue = this.fcstValue ?: "",
        nx = this.nx ?: 0,
        ny = this.ny ?: 0
    )
}
