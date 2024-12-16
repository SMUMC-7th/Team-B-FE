package com.example.umc_wireframe.data.model


import com.google.gson.annotations.SerializedName

data class ServerResponse<T>(
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

data class LoginResultResponse(
    @SerializedName("accessToken") val accessToken: String?,
    @SerializedName("refreshToken") val refreshToken: String
)

data class NicknameResultResponse(
    @SerializedName("newNickname") val newNickname: String?
)

data class JoinRequestResultResponse(
    @SerializedName("userId") val userId: Long?
)

data class MyProfileResultResponse(
    val email: String,
    val name: String,
    val nickname: String,
    val gender: String,
    val alarmStatus: Boolean,
    val alarmTime: String
)


data class PostListResponse(
    @SerializedName("postList") val postList: List<PostResponse>?,
    @SerializedName("listSize") val listSize: Int?,
    @SerializedName("totalPage") val totalPage: Int?,
    @SerializedName("totalElements") val totalElements: Int?,
    @SerializedName("isFirst") val isFirst: Boolean?,
    @SerializedName("isLast") val isLast: Boolean?
)

data class PostResponse(
    @SerializedName("title") val title: String?,
    @SerializedName("content") val content: String?
)
