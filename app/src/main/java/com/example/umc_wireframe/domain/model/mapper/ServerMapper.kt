package com.example.umc_wireframe.domain.model.mapper

import com.example.umc_wireframe.data.model.CommentResultResponse
import com.example.umc_wireframe.data.model.JoinRequestResultResponse
import com.example.umc_wireframe.data.model.LoginResultResponse
import com.example.umc_wireframe.data.model.MyProfileResultResponse
import com.example.umc_wireframe.data.model.NicknameResultResponse
import com.example.umc_wireframe.data.model.ServerResponse
import com.example.umc_wireframe.data.model.OotdResultResponse
import com.example.umc_wireframe.data.model.PostDetailResponse
import com.example.umc_wireframe.data.model.PostListResponse
import com.example.umc_wireframe.data.model.RecommendedHashtagResultResponse
import com.example.umc_wireframe.domain.model.entity.CommentResultEntity
import com.example.umc_wireframe.domain.model.entity.JoinRequestResultEntity
import com.example.umc_wireframe.domain.model.entity.LoginResultEntity
import com.example.umc_wireframe.domain.model.entity.MyProfileResultEntity
import com.example.umc_wireframe.domain.model.entity.NicknameResultEntity
import com.example.umc_wireframe.domain.model.entity.ServerEntity
import com.example.umc_wireframe.domain.model.entity.OotdResultEntity
import com.example.umc_wireframe.domain.model.entity.PostDetailEntity
import com.example.umc_wireframe.domain.model.entity.PostEntity
import com.example.umc_wireframe.domain.model.entity.PostListEntity
import com.example.umc_wireframe.domain.model.entity.RecommendedHashtagResultEntity
import com.example.umc_wireframe.domain.model.toGender

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

fun ServerResponse<JoinRequestResultResponse>.toJoinRequestEntity(): ServerEntity<JoinRequestResultEntity> {
    return ServerEntity(
        status = this.status ?: "",
        code = this.code ?: "",
        message = this.message ?: "",
        isSuccess = this.isSuccess ?: false,
        result = this.result?.toEntity()
    )
}

fun ServerResponse<MyProfileResultResponse>.toMyProfileEntity(): ServerEntity<MyProfileResultEntity> {
    return ServerEntity(
        status = this.status ?: "",
        code = this.code ?: "",
        message = this.message ?: "",
        isSuccess = this.isSuccess ?: false,
        result = this.result?.toEntity()
    )
}

fun ServerResponse<PostListResponse>.toPostListEntity(): ServerEntity<PostListEntity> {
    return ServerEntity(
        status = this.status ?: "",
        code = this.code ?: "",
        message = this.message ?: "",
        isSuccess = this.isSuccess ?: false,
        result = this.result?.toEntity() ?: PostListEntity(
            postList = emptyList(),
            listSize = 0,
            totalPage = 0,
            totalElements = 0,
            isFirst = false,
            isLast = false
        )
    )
}

fun ServerResponse<CommentResultResponse>.toCommentResultEntity(): ServerEntity<CommentResultEntity> {
    return ServerEntity(
        status = this.status ?: "",
        code = this.code ?: "",
        message = this.message ?: "",
        isSuccess = this.isSuccess ?: false,
        result = this.result?.toEntity() ?: CommentResultEntity(
            list = emptyList(),
            lastId = 0
        )
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
        accessToken = this.accessToken,
        refreshToken = refreshToken
    )
}

fun NicknameResultResponse.toEntity(): NicknameResultEntity {
    return NicknameResultEntity(
        newNickname = newNickname
    )
}

fun JoinRequestResultResponse.toEntity(): JoinRequestResultEntity {
    return JoinRequestResultEntity(
        isSuccess = userId?.toInt() == 1
    )
}

fun MyProfileResultResponse.toEntity(): MyProfileResultEntity {
    return MyProfileResultEntity(
        email = email,
        name = name,
        nickname = nickname,
        gender = gender.toGender(),
        alarmStatus = alarmStatus,
        alarmTime = alarmTime.toAlarmTime()
    )
}

fun PostListResponse.toEntity(): PostListEntity {
    return PostListEntity(
        postList = postList?.map { postResponse ->
            PostEntity(
                postId = postResponse.postId ?: "",
                title = postResponse.title ?: "",
                content = postResponse.content ?: ""
            )
        } ?: emptyList(),
        listSize = listSize ?: 0,
        totalPage = totalPage ?: 0,
        totalElements = totalElements ?: 0,
        isFirst = isFirst ?: false,
        isLast = isLast ?: false
    )
}

fun CommentResultResponse.toEntity() = CommentResultEntity(
    list = this.list.map { it.toEntity() },
    lastId = this.lastId
)

fun CommentResultResponse.CommentResponse.toEntity(): CommentResultEntity.CommentEntity {
    return CommentResultEntity.CommentEntity(
        id = this.id,
        content = this.content,
        parentId = this.parentId,
        memberId = this.memberId,
        memberNickname = this.memberNickname,
        reportCount = this.reportCount,
        createdAt = this.createdAt,
        children = this.children.map { it.toEntity() } // 재귀적으로 children 변환
    )
}

fun PostDetailResponse.toEntity(): PostDetailEntity {
    return PostDetailEntity(
        memberName = memberName,
        postId = postId,
        title = title,
        content = content
    )
}


fun String.toAlarmTime(): MyProfileResultEntity.AlarmTime {
    val parts = this.split(":")
    if (parts.size != 3) throw IllegalArgumentException("Invalid time format. Expected HH:MM:ss.")
    val hour = parts[0].toIntOrNull() ?: throw IllegalArgumentException("Invalid hour format.")
    val min = parts[1].toIntOrNull() ?: throw IllegalArgumentException("Invalid minute format.")
    val sec = parts[2].toIntOrNull() ?: throw IllegalArgumentException("Invalid second format.")
    if (hour !in 0..23 || min !in 0..59 || sec !in 0..59) {
        throw IllegalArgumentException("Hour must be between 0-23, minute and second between 0-59.")
    }
    return MyProfileResultEntity.AlarmTime(hour, min)
}
