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
    ): ServerEntity<String>

    //login
    suspend fun postLogin(
        email: String,
        password: String
    ): ServerEntity<LoginResultEntity>

    //manage
    suspend fun postUserWithdraw(
        authorization: String
    ): ServerEntity<String>

    suspend fun postPasswordChange(
        authorization: String
    ): ServerEntity<String>

    suspend fun postPasswordVerify(
        authorization: String,
        verificationCode: String
    ): ServerEntity<String>

    suspend fun postPasswordSuccess(
        authorization: String,
        newPassword: String
    ): ServerEntity<String>

    suspend fun postNicknameChange(
        authorization: String,
        newNickname: String
    ): ServerEntity<NicknameResultEntity>

    suspend fun postAlarmSet(
        authorization: String,
        alarmStatus: SetAlarm,
        alarmTime: String
    ): ServerEntity<String>

    suspend fun getMyProfile(
        authorization: String
    ): ServerEntity<MyProfileResultEntity>

    suspend fun postRefreshToken(
        authorization: String,
        refreshToken: String
    ):ServerEntity<LoginResultEntity>
}