package com.example.umc_wireframe.data.repository

import com.example.umc_wireframe.domain.model.Hashtag
import com.example.umc_wireframe.data.remote.ServerDatasource
import com.example.umc_wireframe.domain.model.entity.ServerEntity
import com.example.umc_wireframe.domain.model.entity.OotdResultEntity
import com.example.umc_wireframe.domain.model.entity.RecommendedHashtagResultEntity
import com.example.umc_wireframe.domain.model.mapper.toHashtagEntity
import com.example.umc_wireframe.domain.model.mapper.toEntity
import com.example.umc_wireframe.domain.model.mapper.toTempEntity
import com.example.umc_wireframe.domain.repository.OotdRepository
import okhttp3.MultipartBody

class OotdRepositoryImpl(
    private val datasource: ServerDatasource
) : OotdRepository {
    override suspend fun getRecommendedHashtag(
        authorization: String,
        maxTemperature: Int,
        minTemperature: Int
    ): ServerEntity<RecommendedHashtagResultEntity> = datasource.getRecommendedHashtag(
        authorization = authorization,
        maxTemperature = maxTemperature,
        minTemperature = minTemperature
    ).toHashtagEntity()

    override suspend fun getOotdPastForTemp(
        authorization: String,
        maxTemperature: Int,
        minTemperature: Int
    ): ServerEntity<OotdResultEntity> = datasource.getOotdPastForTemp(
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
    ): ServerEntity<String> = datasource.postOotd(
        authorization = authorization,
        image = image,
        maxTemperature = maxTemperature,
        minTemperature = minTemperature,
        hashtags = hashtags
    ).toTempEntity()

    override suspend fun getOotdPastForYearMonth(
        authorization: String,
        year: Int,
        month: Int
    ): ServerEntity<OotdResultEntity> = datasource.getOotdPastForYearMonth(
        authorization = authorization,
        year = year,
        month = month
    ).toEntity()
}