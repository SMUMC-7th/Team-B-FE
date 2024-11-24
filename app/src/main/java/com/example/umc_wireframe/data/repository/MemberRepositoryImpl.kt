package com.example.umc_wireframe.data.repository

import com.example.umc_wireframe.data.remote.*
import com.example.umc_wireframe.domain.model.Gender
import com.example.umc_wireframe.domain.model.SetAlarm
import com.example.umc_wireframe.domain.model.entity.*
import com.example.umc_wireframe.domain.model.mapper.*
import com.example.umc_wireframe.domain.repository.MemberRepository
import com.example.umc_wireframe.util.SharedPreferencesManager

class MemberRepositoryImpl(
    private val datasource: ServerDatasource,
    private val sharedPreferencesManager: SharedPreferencesManager // SharedPreferencesManager 주입
) : MemberRepository {

    // Join
    override suspend fun postJoinResquest(
        email: String,
        password: String
    ): ServerEntity<JoinRequestResultEntity> = datasource.postJoinResquest(
        loginRequest = AccountRequest(
            email = email,
            password = password
        )
    ).toJoinRequestEntity()

    override suspend fun postJoinVerify(
        email: String,
        verificationCode: String
    ): ServerEntity<String> = datasource.postJoinVerify(
        JoinVerify(
            email = email,
            verificationCode = verificationCode
        )
    ).toTempEntity()

    override suspend fun postJoinSuccess(
        email: String,
        name: String,
        nickname: String,
        gender: Gender
    ): ServerEntity<String> = datasource.postJoinSuccess(
        JoinInfo(
            email = email,
            name = name,
            nickname = nickname,
            gender = gender.toString()
        )
    ).toTempEntity()

    // Login
    override suspend fun postLogin(
        email: String,
        password: String
    ): ServerEntity<LoginResultEntity> {
        val response = datasource.postLogin(
            loginRequest = AccountRequest(
                email = email,
                password = password
            )
        )

        // 토큰 저장 로직 추가
        if (response.isSuccess == true) {
            response.result?.let {
                sharedPreferencesManager.saveAccessToken(it.accessToken ?: "")
                sharedPreferencesManager.saveRefreshToken(it.refreshToken)
            }
        }

        return response.toLoginEntity()
    }

    // Manage
    override suspend fun postUserWithdraw(
        authorization: String
    ): ServerEntity<String> = datasource.postUserWithdraw(
        authorization
    ).toTempEntity()

    override suspend fun postPasswordChange(
        authorization: String
    ): ServerEntity<String> = datasource.postPasswordChange(
        authorization
    ).toTempEntity()

    override suspend fun postPasswordVerify(
        authorization: String,
        verificationCode: String
    ): ServerEntity<String> = datasource.postPasswordVerify(
        authorization = authorization,
        VerifyCode(
            verificationCode
        )
    ).toTempEntity()

    override suspend fun postPasswordSuccess(
        authorization: String,
        newPassword: String
    ): ServerEntity<String> {
        val response = datasource.postPasswordSuccess(
            authorization = authorization,
            newPassword = NewPassword(newPassword)
        )

        // 토큰 저장 로직 추가 (필요하다면)
        if (response.isSuccess == true) {
            response.result?.let {
                sharedPreferencesManager.saveAccessToken(it) // 필요한 경우 저장
            }
        }

        return response.toTempEntity()
    }

    override suspend fun postNicknameChange(
        authorization: String,
        newNickname: String
    ): ServerEntity<NicknameResultEntity> = datasource.postNicknameChange(
        authorization = authorization,
        newNickname = NewNickname(newNickname)
    ).toNicknameEntity()

    override suspend fun postAlarmSet(
        authorization: String,
        alarmStatus: SetAlarm,
        alarmTime: String
    ): ServerEntity<String> = datasource.postAlarmSet(
        authorization = authorization,
        alarmSet = AlarmSet(
            alarmStatus = alarmStatus.setType,
            alarmTime = alarmTime
        )
    ).toTempEntity()

    override suspend fun getMyProfile(
        authorization: String
    ): ServerEntity<MyProfileResultEntity> = datasource.getMyProfile(
        authorization
    ).toMyProfileEntity()

    override suspend fun postRefreshToken(
        authorization: String,
        refreshToken: String
    ): ServerEntity<LoginResultEntity> {
        val response = datasource.postRefreshToken(
            authorization = authorization,
            refreshToken = RefreshToken(refreshToken)
        )

        // 토큰 저장 로직 추가
        if (response.isSuccess == true) {
            response.result?.let {
                sharedPreferencesManager.saveAccessToken(it.accessToken ?: "")
                sharedPreferencesManager.saveRefreshToken(it.refreshToken ?: "")
            }
        }

        return response.toLoginEntity()
    }
}
