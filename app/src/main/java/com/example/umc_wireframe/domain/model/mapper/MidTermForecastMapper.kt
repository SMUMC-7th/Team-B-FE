package com.example.umc_wireframe.domain.model.mapper

import com.example.umc_wireframe.data.model.*
import com.example.umc_wireframe.domain.model.entity.MidTermBodyEntity
import com.example.umc_wireframe.domain.model.entity.MidTermForecastEntity
import com.example.umc_wireframe.domain.model.entity.MidTermForecastItemEntity
import com.example.umc_wireframe.domain.model.entity.MidTermHeaderEntity
import com.example.umc_wireframe.domain.model.entity.MidTermResponseEntity

fun MidTermForecastResponse?.toEntity() = this?.let {
    MidTermForecastEntity(
        response = it.response.toEntity()
    )
} ?: MidTermForecastEntity(
    response = MidTermResponseEntity(
        header = MidTermHeaderEntity(resultCode = "unknown", resultMsg = "No message"),
        body = MidTermBodyEntity(items = listOf(), pageNo = 0, numOfRows = 0, totalCount = 0)
    )
)

fun MidTermResponse?.toEntity() = this?.let {
    MidTermResponseEntity(
        header = it.header.toEntity(),
        body = it.body.toEntity()
    )
} ?: MidTermResponseEntity(
    header = MidTermHeaderEntity(resultCode = "unknown", resultMsg = "No message"),
    body = MidTermBodyEntity(items = listOf(), pageNo = 0, numOfRows = 0, totalCount = 0)
)

fun MidTermHeaderResponse?.toEntity() = this?.let {
    MidTermHeaderEntity(
        resultCode = it.resultCode ?: "unknown",
        resultMsg = it.resultMsg ?: "No message"
    )
} ?: MidTermHeaderEntity(resultCode = "unknown", resultMsg = "No message")

fun MidTermBodyResponse?.toEntity() = this?.let {
    MidTermBodyEntity(
        items = it.items?.item?.map { item -> item.toEntity() } ?: emptyList(),
        pageNo = it.pageNo ?: 0,
        numOfRows = it.numOfRows ?: 0,
        totalCount = it.totalCount ?: 0
    )
} ?: MidTermBodyEntity(items = listOf(), pageNo = 0, numOfRows = 0, totalCount = 0)

fun MidTermForecastItemResponse?.toEntity() = this?.let {
    MidTermForecastItemEntity(
        regId = it.regId ?: "unknown",  // 기본값 설정
        taMin3 = it.taMin3 ?: 0,
        taMin3Low = it.taMin3Low ?: 0,
        taMin3High = it.taMin3High ?: 0,
        taMax3 = it.taMax3 ?: 0,
        taMax3Low = it.taMax3Low ?: 0,
        taMax3High = it.taMax3High ?: 0,
        taMin4 = it.taMin4 ?: 0,
        taMin4Low = it.taMin4Low ?: 0,
        taMin4High = it.taMin4High ?: 0,
        taMax4 = it.taMax4 ?: 0,
        taMax4Low = it.taMax4Low ?: 0,
        taMax4High = it.taMax4High ?: 0,
        taMin5 = it.taMin5 ?: 0,
        taMin5Low = it.taMin5Low ?: 0,
        taMin5High = it.taMin5High ?: 0,
        taMax5 = it.taMax5 ?: 0,
        taMax5Low = it.taMax5Low ?: 0,
        taMax5High = it.taMax5High ?: 0,
        taMin6 = it.taMin6 ?: 0,
        taMin6Low = it.taMin6Low ?: 0,
        taMin6High = it.taMin6High ?: 0,
        taMax6 = it.taMax6 ?: 0,
        taMax6Low = it.taMax6Low ?: 0,
        taMax6High = it.taMax6High ?: 0,
        taMin7 = it.taMin7 ?: 0,
        taMin7Low = it.taMin7Low ?: 0,
        taMin7High = it.taMin7High ?: 0,
        taMax7 = it.taMax7 ?: 0,
        taMax7Low = it.taMax7Low ?: 0,
        taMax7High = it.taMax7High ?: 0,
        taMin8 = it.taMin8 ?: 0,
        taMin8Low = it.taMin8Low ?: 0,
        taMin8High = it.taMin8High ?: 0,
        taMax8 = it.taMax8 ?: 0,
        taMax8Low = it.taMax8Low ?: 0,
        taMax8High = it.taMax8High ?: 0,
        taMin9 = it.taMin9 ?: 0,
        taMin9Low = it.taMin9Low ?: 0,
        taMin9High = it.taMin9High ?: 0,
        taMax9 = it.taMax9 ?: 0,
        taMax9Low = it.taMax9Low ?: 0,
        taMax9High = it.taMax9High ?: 0,
        taMin10 = it.taMin10 ?: 0,
        taMin10Low = it.taMin10Low ?: 0,
        taMin10High = it.taMin10High ?: 0,
        taMax10 = it.taMax10 ?: 0,
        taMax10Low = it.taMax10Low ?: 0,
        taMax10High = it.taMax10High ?: 0
    )
} ?: MidTermForecastItemEntity(
    regId = "unknown",
    taMin3 = 0,
    taMin3Low = 0,
    taMin3High = 0,
    taMax3 = 0,
    taMax3Low = 0,
    taMax3High = 0,
    taMin4 = 0,
    taMin4Low = 0,
    taMin4High = 0,
    taMax4 = 0,
    taMax4Low = 0,
    taMax4High = 0,
    taMin5 = 0,
    taMin5Low = 0,
    taMin5High = 0,
    taMax5 = 0,
    taMax5Low = 0,
    taMax5High = 0,
    taMin6 = 0,
    taMin6Low = 0,
    taMin6High = 0,
    taMax6 = 0,
    taMax6Low = 0,
    taMax6High = 0,
    taMin7 = 0,
    taMin7Low = 0,
    taMin7High = 0,
    taMax7 = 0,
    taMax7Low = 0,
    taMax7High = 0,
    taMin8 = 0,
    taMin8Low = 0,
    taMin8High = 0,
    taMax8 = 0,
    taMax8Low = 0,
    taMax8High = 0,
    taMin9 = 0,
    taMin9Low = 0,
    taMin9High = 0,
    taMax9 = 0,
    taMax9Low = 0,
    taMax9High = 0,
    taMin10 = 0,
    taMin10Low = 0,
    taMin10High = 0,
    taMax10 = 0,
    taMax10Low = 0,
    taMax10High = 0
)
