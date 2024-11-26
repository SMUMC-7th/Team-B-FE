package com.example.umc_wireframe.domain.repository

import com.example.umc_wireframe.data.remote.RefreshToken
import com.example.umc_wireframe.domain.model.Gender
import com.example.umc_wireframe.domain.model.SetAlarm
import com.example.umc_wireframe.domain.model.entity.JoinRequestResultEntity
import com.example.umc_wireframe.domain.model.entity.LoginResultEntity
import com.example.umc_wireframe.domain.model.entity.MyProfileResultEntity
import com.example.umc_wireframe.domain.model.entity.NicknameResultEntity
import com.example.umc_wireframe.domain.model.entity.ServerEntity
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import java.time.LocalDateTime

interface MemberRepository {
    //join
    suspend fun postJoinResquest(
        email: String,
        password: String
    ): ServerEntity<JoinRequestResultEntity>

    suspend fun postJoinVerify(
        email: String,
        verificationCode: String
    ): ServerEntity<String>

    suspend fun postJoinSuccess(
        email: String,
        name: String,
        nickname: String,
        gender: Gender
    ): ServerEntity<LoginResultEntity>

    //login
    suspend fun postLogin(
        email: String,
        password: String
    ): ServerEntity<LoginResultEntity>

    //manage
    suspend fun postUserWithdraw(
    ): ServerEntity<String>

    suspend fun postPasswordChange(
    ): ServerEntity<String>

    suspend fun postPasswordVerify(
        verificationCode: String
    ): ServerEntity<String>

    suspend fun patchPasswordSuccess(
        newPassword: String
    ): ServerEntity<String>

    suspend fun patchNicknameChange(
        newNickname: String
    ): ServerEntity<NicknameResultEntity>

    suspend fun patchAlarmSet(
        alarmStatus: SetAlarm,
        alarmTime: LocalDateTime
    ): ServerEntity<String>

    suspend fun getMyProfile(
    ): ServerEntity<MyProfileResultEntity>

    suspend fun postRefreshToken(
        refreshToken: String
    ):ServerEntity<LoginResultEntity>
}