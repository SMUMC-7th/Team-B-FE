package com.example.umc_wireframe.data.model

import com.google.gson.annotations.SerializedName

data class ShortTermForecastResponse(
    @SerializedName("response") val response: ShortTermResponse?
)

data class ShortTermResponse(
    @SerializedName("header") val header: ShortTermHeaderResponse?,
    @SerializedName("body") val body: ShortTermBodyResponse?
)

data class ShortTermHeaderResponse(
    @SerializedName("resultCode") val resultCode: String?,
    @SerializedName("resultMsg") val resultMsg: String?
)

data class ShortTermBodyResponse(
    @SerializedName("items") val items: ShortTermItemsResponse?,
    @SerializedName("pageNo") val pageNo: Int?,
    @SerializedName("numOfRows") val numOfRows: Int?,
    @SerializedName("totalCount") val totalCount: Int?
)

data class ShortTermItemsResponse(
    @SerializedName("item") val item: List<ShortTermForecastItemResponse>?
)

data class ShortTermForecastItemResponse(
    @SerializedName("category") val category: String?,  // 예: "PCP" (강수)
    @SerializedName("baseDate") val baseDate: String?, // 기준 날짜 (YYYYMMDD)
    @SerializedName("baseTime") val baseTime: String?, // 기준 시간 (HHmm)
    @SerializedName("nx") val nx: Int?,                // X 좌표
    @SerializedName("ny") val ny: Int?,                // Y 좌표
    @SerializedName("fcstValue") val value: String?    // 관측 값 (기온 또는 강수 상태)
)