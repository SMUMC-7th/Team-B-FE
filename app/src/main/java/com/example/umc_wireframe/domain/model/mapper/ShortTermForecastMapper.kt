package com.example.umc_wireframe.domain.model.mapper

import com.example.umc_wireframe.data.model.*
import com.example.umc_wireframe.domain.model.ShortTermCategory
import com.example.umc_wireframe.domain.model.entity.ShortTermBodyEntity
import com.example.umc_wireframe.domain.model.entity.ShortTermForecastEntity
import com.example.umc_wireframe.domain.model.entity.ShortTermForecastItemEntity
import com.example.umc_wireframe.domain.model.entity.ShortTermHeaderEntity
import com.example.umc_wireframe.domain.model.toShortTermCategory

fun ShortTermForecastResponse?.toEntity(): ShortTermForecastEntity {
    return this?.let {
        ShortTermForecastEntity(
            header = it.response?.header.toEntity(),
            body = it.response?.body.toEntity()
        )
    } ?: ShortTermForecastEntity(
        header = ShortTermHeaderEntity(resultCode = "unknown", resultMsg = "No message"),
        body = ShortTermBodyEntity(items = emptyList(), pageNo = 0, numOfRows = 0, totalCount = 0)
    )
}

fun ShortTermHeaderResponse?.toEntity(): ShortTermHeaderEntity {
    return this?.let {
        ShortTermHeaderEntity(
            resultCode = it.resultCode ?: "unknown",
            resultMsg = it.resultMsg ?: "No message"
        )
    } ?: ShortTermHeaderEntity(resultCode = "unknown", resultMsg = "No message")
}

fun ShortTermBodyResponse?.toEntity(): ShortTermBodyEntity {
    return this?.let {
        ShortTermBodyEntity(
            items = it.items?.item?.map { item -> item.toEntity() } ?: emptyList(),
            pageNo = it.pageNo ?: 0,
            numOfRows = it.numOfRows ?: 0,
            totalCount = it.totalCount ?: 0
        )
    } ?: ShortTermBodyEntity(items = emptyList(), pageNo = 0, numOfRows = 0, totalCount = 0)
}

fun ShortTermForecastItemResponse?.toEntity(): ShortTermForecastItemEntity {
    return this?.let {
        ShortTermForecastItemEntity(
            date = it.baseDate ?: "unknown",
            time = it.baseTime ?: "unknown",
            category = it.category?.toShortTermCategory() ?: ShortTermCategory.ERROR,
            value = it.value ?: "unknown", // Default value if null
            locationX = it.nx ?: 0,
            locationY = it.ny ?: 0,
            fcstDate = it.fcstDate ?: "unknown",
            fcstTime = it.fcstTime ?: "unknown"
        )
    } ?: ShortTermForecastItemEntity(
        date = "unknown",
        time = "unknown",
        category = ShortTermCategory.ERROR,
        value = "unknown",
        locationX = 0,
        locationY = 0,
        fcstDate = "unknown",
        fcstTime = "unknown"
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

fun ShortTermHeaderEntity.toResponse(): ShortTermHeaderResponse {
    return ShortTermHeaderResponse(
        resultCode = this.resultCode,
        resultMsg = this.resultMsg
    )
}

fun ShortTermBodyEntity.toResponse(): ShortTermBodyResponse {
    return ShortTermBodyResponse(
        items = ShortTermItemsResponse(
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
        value = this.value,
        fcstDate = fcstDate,
        fcstTime = fcstTime
    )
}
