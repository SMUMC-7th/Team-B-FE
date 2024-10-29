package com.example.umc_wireframe.domain.model

import com.example.umc_wireframe.data.model.*

fun ShortTermForecastResponse.toEntity(): ShortTermForecastEntity {
    return ShortTermForecastEntity(
        header = this.response.header.toEntity(),
        body = this.response.body.toEntity()
    )
}

fun HeaderResponse.toEntity(): HeaderEntity {
    return HeaderEntity(
        resultCode = this.resultCode,
        resultMsg = this.resultMsg
    )
}

fun BodyResponse.toEntity(): BodyEntity {
    return BodyEntity(
        items = this.items.item.map { it.toEntity() },
        pageNo = this.pageNo,
        numOfRows = this.numOfRows,
        totalCount = this.totalCount
    )
}

fun ShortTermForecastItemResponse.toEntity(): ShortTermForecastItemEntity {
    return ShortTermForecastItemEntity(
        date = this.baseDate,
        time = this.baseTime,
        category = this.category.toShortTermCategory(),
        value = this.value,
        locationX = this.nx,
        locationY = this.ny
    )
}

// Reverse mappers (Entity to Response)

fun ShortTermForecastEntity.toResponse(): ShortTermForecastResponse {
    return ShortTermForecastResponse(
        response = ShortTermResponse(
            header = this.header.toResponse(),
            body = this.body.toResponse()
        )
    )
}

fun HeaderEntity.toResponse(): HeaderResponse {
    return HeaderResponse(
        resultCode = this.resultCode,
        resultMsg = this.resultMsg
    )
}

fun BodyEntity.toResponse(): BodyResponse {
    return BodyResponse(
        items = ItemsResponse(
            item = this.items.map { it.toResponse() }
        ),
        pageNo = this.pageNo,
        numOfRows = this.numOfRows,
        totalCount = this.totalCount
    )
}

fun ShortTermForecastItemEntity.toResponse(): ShortTermForecastItemResponse {
    return ShortTermForecastItemResponse(
        category = this.category.toString(),
        baseDate = this.date,
        baseTime = this.time,
        nx = this.locationX,
        ny = this.locationY,
        value = this.value
    )
}

