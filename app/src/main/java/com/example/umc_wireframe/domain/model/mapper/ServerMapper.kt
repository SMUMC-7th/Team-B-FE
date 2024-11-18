package com.example.umc_wireframe.domain.model.mapper

import com.example.umc_wireframe.data.model.LoginResultResponse
import com.example.umc_wireframe.data.model.NicknameResultResponse
import com.example.umc_wireframe.data.model.ServerResponse
import com.example.umc_wireframe.data.model.OotdResultResponse
import com.example.umc_wireframe.data.model.RecommendedHashtagResultResponse
import com.example.umc_wireframe.domain.model.entity.LoginResultEntity
import com.example.umc_wireframe.domain.model.entity.NicknameResultEntity
import com.example.umc_wireframe.domain.model.entity.ServerEntity
import com.example.umc_wireframe.domain.model.entity.OotdResultEntity
import com.example.umc_wireframe.domain.model.entity.RecommendedHashtagResultEntity

fun ServerResponse<String>.toTempEntity(): ServerEntity<String> {
    return ServerEntity(
        status = this.status ?: "",
        code = this.code ?: "",
        message = this.message ?: "",
        isSuccess = this.isSuccess ?: false,
        result = null
    )
}


fun ServerResponse<RecommendedHashtagResultResponse>.toHashtagEntity(): ServerEntity<RecommendedHashtagResultEntity> {
    return ServerEntity(
        status = this.status ?: "",
        code = this.code ?: "",
        message = this.message ?: "",
        isSuccess = this.isSuccess ?: false,
        result = this.result?.toEntity()
    )
}

fun ServerResponse<OotdResultResponse>.toEntity(): ServerEntity<OotdResultEntity> {
    return ServerEntity(
        status = this.status ?: "",
        code = this.code ?: "",
        message = this.message ?: "",
        isSuccess = this.isSuccess ?: false,
        result = this.result?.toEntity()
    )
}

fun ServerResponse<LoginResultResponse>.toLoginEntity(): ServerEntity<LoginResultEntity> {
    return ServerEntity(
        status = this.status ?: "",
        code = this.code ?: "",
        message = this.message ?: "",
        isSuccess = this.isSuccess ?: false,
        result = this.result?.toEntity()
    )
}

fun ServerResponse<NicknameResultResponse>.toNicknameEntity(): ServerEntity<NicknameResultEntity> {
    return ServerEntity(
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

fun LoginResultResponse.toEntity(): LoginResultEntity {
    return LoginResultEntity(
        token = this.token
    )
}

fun NicknameResultResponse.toEntity(): NicknameResultEntity {
    return NicknameResultEntity(
        newNickname = newNickname
    )
}
