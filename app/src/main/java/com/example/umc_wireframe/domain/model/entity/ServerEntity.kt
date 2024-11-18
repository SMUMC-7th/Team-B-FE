package com.example.umc_wireframe.domain.model.entity

import com.google.gson.annotations.SerializedName

data class ServerEntity<T>(
    val status: String,
    val code: String,
    val message: String,
    val isSuccess: Boolean,
    val result: T?
)

data class RecommendedHashtagResultEntity(
    val recommendations: List<Recommendation>
) {
    data class Recommendation(
        val hashtag: String,
        val image: String
    )
}

data class OotdResultEntity(
    val ootds: List<Ootd>?
) {
    data class Ootd(
        val image: String,
        val minTemperature: Int,
        val maxTemperature: Int,
        val weatherDescription: String,
        val hashtags: List<String>,
        val date: String
    )
}

data class LoginResultEntity(
    val token: String?
)


data class NicknameResultEntity(
    val newNickname: String?
)