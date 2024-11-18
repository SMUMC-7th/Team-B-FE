package com.example.umc_wireframe.data.repository

import android.graphics.Bitmap
import com.example.umc_wireframe.domain.model.Hashtag
import com.example.umc_wireframe.data.remote.ServerDatasource
import com.example.umc_wireframe.domain.model.entity.OOtdEntity
import com.example.umc_wireframe.domain.model.entity.OotdResultEntity
import com.example.umc_wireframe.domain.model.entity.RecommendedHashtagResultEntity
import com.example.umc_wireframe.domain.model.mapper.toEntity
import com.example.umc_wireframe.domain.repository.OotdRepository
import okhttp3.MultipartBody

class OotdRepositoryImpl(
    private val datasource: ServerDatasource
) : OotdRepository {
    override suspend fun getRecommendedHashtag(
        authorization: String,
        maxTemperature: Int,
        minTemperature: Int
    ): OOtdEntity<RecommendedHashtagResultEntity> = datasource.getRecommendedHashtag(
        authorization = authorization,
        maxTemperature = maxTemperature,
        minTemperature = minTemperature
    ).toEntity()

    override suspend fun getOotdPastForTemp(
        authorization: String,
        maxTemperature: Int,
        minTemperature: Int
    ): OOtdEntity<OotdResultEntity> =datasource.getOotdPastForTemp(
        authorization = authorization,
        maxTemperature = maxTemperature,
        minTemperature = minTemperature
    ).toEntity()

    override suspend fun postOotd(
        authorization: String,
        image: MultipartBody.Part,
        maxTemperature: Int,
        minTemperature: Int,
        hashtags: List<Hashtag>
    ): OOtdEntity<OotdResultEntity> = datasource.postOotd(
        authorization = authorization,
        image = image,
        maxTemperature = maxTemperature,
        minTemperature = minTemperature,
        hashtags = hashtags
    ).toEntity()

    override suspend fun getOotdPastForYearMonth(
        authorization: String,
        year: Int,
        month: Int
    ): OOtdEntity<OotdResultEntity> = datasource.getOotdPastForYearMonth(
        authorization = authorization,
        year = year,
        month = month
    ).toEntity()
}