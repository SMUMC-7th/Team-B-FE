package com.example.umc_wireframe.data.model


import com.google.gson.annotations.SerializedName

data class OOtdResponse<T>(
    @SerializedName("status") val status: String?,
    @SerializedName("code") val code: String?,
    @SerializedName("message") val message: String?,
    @SerializedName("isSuccess") val isSuccess: Boolean?,
    @SerializedName("result") val result: T?
)

data class RecommendedHashtagResultResponse(
    @SerializedName("recommendations") val recommendations: List<Recommendation>?
) {
    data class Recommendation(
        @SerializedName("hashtag") val hashtag: String?,
        @SerializedName("image") val image: String?
    )
}

data class OotdResultResponse(
    @SerializedName("ootds") val ootds: List<Ootd>?
) {
    data class Ootd(
        @SerializedName("image") val image: String?,
        @SerializedName("minTemperature") val minTemperature: Int?,
        @SerializedName("maxTemperature") val maxTemperature: Int?,
        @SerializedName("weatherDescription") val weatherDescription: String?,
        @SerializedName("hashtags") val hashtags: List<String>?,
        @SerializedName("date") val date: String?
    )
}
