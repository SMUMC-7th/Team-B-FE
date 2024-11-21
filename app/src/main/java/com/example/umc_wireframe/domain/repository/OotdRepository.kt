package com.example.umc_wireframe.domain.repository

import com.example.umc_wireframe.domain.model.Hashtag
import com.example.umc_wireframe.domain.model.entity.ServerEntity
import com.example.umc_wireframe.domain.model.entity.OotdResultEntity
import com.example.umc_wireframe.domain.model.entity.RecommendedHashtagResultEntity
import okhttp3.MultipartBody

interface OotdRepository {
    suspend fun getRecommendedHashtag(
        authorization: String,
        maxTemperature: Int,
        minTemperature: Int
    ): ServerEntity<RecommendedHashtagResultEntity>

    suspend fun getOotdPastForTemp(
        authorization: String,
        maxTemperature: Int,
        minTemperature: Int
    ): ServerEntity<OotdResultEntity>

    suspend fun postOotd(
        authorization: String,
        image: MultipartBody.Part,
        maxTemperature: Int,
        minTemperature: Int,
        hashtags: List<Hashtag>
    ): ServerEntity<String>

    suspend fun getOotdPastForYearMonth(
        authorization: String,
        year: Int,
        month: Int
    ): ServerEntity<OotdResultEntity>
}