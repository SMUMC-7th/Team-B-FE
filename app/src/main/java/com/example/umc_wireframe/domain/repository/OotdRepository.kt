package com.example.umc_wireframe.domain.repository

import android.graphics.Bitmap
import com.example.umc_wireframe.data.model.Hashtag
import com.example.umc_wireframe.domain.model.entity.OOtdEntity
import com.example.umc_wireframe.domain.model.entity.OotdResultEntity
import com.example.umc_wireframe.domain.model.entity.RecommendedHashtagResultEntity

interface OotdRepository {
    suspend fun getRecommendedHashtag(
        authorization: String,
        maxTemperature: Int,
        minTemperature: Int
    ): OOtdEntity<RecommendedHashtagResultEntity>

    suspend fun getOotdPastForTemp(
        authorization: String,
        maxTemperature: Int,
        minTemperature: Int
    ): OOtdEntity<OotdResultEntity>

    suspend fun postOotd(
        authorization: String,
        image: Bitmap,
        maxTemperature: Int,
        minTemperature: Int,
        hashtags: List<Hashtag>
    ): OOtdEntity<OotdResultEntity>

    suspend fun getOotdPastForYearMonth(
        authorization: String,
        year: Int,
        month: Int
    ): OOtdEntity<OotdResultEntity>
}