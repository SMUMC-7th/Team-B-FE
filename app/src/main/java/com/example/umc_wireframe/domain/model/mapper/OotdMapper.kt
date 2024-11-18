package com.example.umc_wireframe.domain.model.mapper

import com.example.umc_wireframe.data.model.OOtdResponse
import com.example.umc_wireframe.data.model.OotdResultResponse
import com.example.umc_wireframe.data.model.RecommendedHashtagResultResponse
import com.example.umc_wireframe.domain.model.entity.OOtdEntity
import com.example.umc_wireframe.domain.model.entity.OotdResultEntity
import com.example.umc_wireframe.domain.model.entity.RecommendedHashtagResultEntity

fun OOtdResponse<RecommendedHashtagResultResponse>.toEntity(): OOtdEntity<RecommendedHashtagResultEntity> {
    return OOtdEntity(
        status = this.status ?: "",
        code = this.code ?: "",
        message = this.message ?: "",
        isSuccess = this.isSuccess ?: false,
        result = this.result?.toEntity()
    )
}

fun OOtdResponse<OotdResultResponse>.toEntity(): OOtdEntity<OotdResultEntity> {
    return OOtdEntity(
        status = this.status ?: "",
        code = this.code ?: "",
        message = this.message ?: "",
        isSuccess = this.isSuccess ?: false,
        result = this.result?.toEntity()
    )
}

fun RecommendedHashtagResultResponse.toEntity(): RecommendedHashtagResultEntity {
    return RecommendedHashtagResultEntity(
        recommendations = this.recommendations?.map { recommendation ->
            RecommendedHashtagResultEntity.Recommendation(
                hashtag = recommendation.hashtag ?: "",
                image = recommendation.image ?: ""
            )
        } ?: emptyList()
    )
}

fun OotdResultResponse.toEntity(): OotdResultEntity {
    return OotdResultEntity(
        ootds = this.ootds?.map { ootd ->
            OotdResultEntity.Ootd(
                image = ootd.image ?: "",
                minTemperature = ootd.minTemperature ?: 0,
                maxTemperature = ootd.maxTemperature ?: 0,
                weatherDescription = ootd.weatherDescription ?: "",
                hashtags = ootd.hashtags ?: emptyList(),
                date = ootd.date ?: ""
            )
        }
    )
}
