package com.example.umc_wireframe.data.model

import com.google.gson.annotations.SerializedName

data class MidTermForecastResponse(
    @SerializedName("response") val response: MidTermResponse?
)

data class MidTermResponse(
    @SerializedName("header") val header: MidTermHeaderResponse?,
    @SerializedName("body") val body: MidTermBodyResponse?
)

data class MidTermHeaderResponse(
    @SerializedName("resultCode") val resultCode: String?,
    @SerializedName("resultMsg") val resultMsg: String?
)

data class MidTermBodyResponse(
    @SerializedName("items") val items: MidTermItemsResponse?,
    @SerializedName("pageNo") val pageNo: Int?,
    @SerializedName("numOfRows") val numOfRows: Int?,
    @SerializedName("totalCount") val totalCount: Int?
)

data class MidTermItemsResponse(
    @SerializedName("item") val item: List<MidTermForecastItemResponse>?
)

data class MidTermForecastItemResponse(
    @SerializedName("regId")
    val regId: String?,           // 지역 코드
    @SerializedName("wfSv")
    val forecastSummary: String?,  // 날씨 요약
    @SerializedName("taMin3")
    val taMin3: Int,
    @SerializedName("taMin3Low")
    val taMin3Low: Int?,
    @SerializedName("taMin3High")
    val taMin3High: Int?,
    @SerializedName("taMax3")
    val taMax3: Int,
    @SerializedName("taMax3Low")
    val taMax3Low: Int?,
    @SerializedName("taMax3High")
    val taMax3High: Int?,
    @SerializedName("taMin4")
    val taMin4: Int,
    @SerializedName("taMin4Low")
    val taMin4Low: Int?,
    @SerializedName("taMin4High")
    val taMin4High: Int?,
    @SerializedName("taMax4")
    val taMax4: Int,
    @SerializedName("taMax4Low")
    val taMax4Low: Int?,
    @SerializedName("taMax4High")
    val taMax4High: Int?,
    @SerializedName("taMin5")
    val taMin5: Int,
    @SerializedName("taMin5Low")
    val taMin5Low: Int?,
    @SerializedName("taMin5High")
    val taMin5High: Int?,
    @SerializedName("taMax5")
    val taMax5: Int,
    @SerializedName("taMax5Low")
    val taMax5Low: Int?,
    @SerializedName("taMax5High")
    val taMax5High: Int?,
    @SerializedName("taMin6")
    val taMin6: Int,
    @SerializedName("taMin6Low")
    val taMin6Low: Int?,
    @SerializedName("taMin6High")
    val taMin6High: Int?,
    @SerializedName("taMax6")
    val taMax6: Int,
    @SerializedName("taMax6Low")
    val taMax6Low: Int?,
    @SerializedName("taMax6High")
    val taMax6High: Int?,
    @SerializedName("taMin7")
    val taMin7: Int,
    @SerializedName("taMin7Low")
    val taMin7Low: Int?,
    @SerializedName("taMin7High")
    val taMin7High: Int?,
    @SerializedName("taMax7")
    val taMax7: Int?,
    @SerializedName("taMax7Low")
    val taMax7Low: Int?,
    @SerializedName("taMax7High")
    val taMax7High: Int?,
    @SerializedName("taMin8")
    val taMin8: Int,
    @SerializedName("taMin8Low")
    val taMin8Low: Int?,
    @SerializedName("taMin8High")
    val taMin8High: Int?,
    @SerializedName("taMax8")
    val taMax8: Int,
    @SerializedName("taMax8Low")
    val taMax8Low: Int?,
    @SerializedName("taMax8High")
    val taMax8High: Int?,
    @SerializedName("taMin9")
    val taMin9: Int,
    @SerializedName("taMin9Low")
    val taMin9Low: Int?,
    @SerializedName("taMin9High")
    val taMin9High: Int?,
    @SerializedName("taMax9")
    val taMax9: Int,
    @SerializedName("taMax9Low")
    val taMax9Low: Int?,
    @SerializedName("taMax9High")
    val taMax9High: Int?,
    @SerializedName("taMin10")
    val taMin10: Int,
    @SerializedName("taMin10Low")
    val taMin10Low: Int?,
    @SerializedName("taMin10High")
    val taMin10High: Int?,
    @SerializedName("taMax10")
    val taMax10: Int,
    @SerializedName("taMax10Low")
    val taMax10Low: Int?,
    @SerializedName("taMax10High")
    val taMax10High: Int?
)
