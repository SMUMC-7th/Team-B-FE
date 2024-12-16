package com.example.umc_wireframe.data.repository

import com.example.umc_wireframe.data.remote.AccountRequest
import com.example.umc_wireframe.data.remote.AlarmSet
import com.example.umc_wireframe.data.remote.JoinInfo
import com.example.umc_wireframe.data.remote.JoinVerify
import com.example.umc_wireframe.data.remote.NewNickname
import com.example.umc_wireframe.data.remote.NewPassword
import com.example.umc_wireframe.data.remote.RefreshToken
import com.example.umc_wireframe.data.remote.ServerDatasource
import com.example.umc_wireframe.data.remote.VerifyCode
import com.example.umc_wireframe.domain.model.Gender
import com.example.umc_wireframe.domain.model.SetAlarm
import com.example.umc_wireframe.domain.model.entity.JoinRequestResultEntity
import com.example.umc_wireframe.domain.model.entity.LoginResultEntity
import com.example.umc_wireframe.domain.model.entity.MyProfileResultEntity
import com.example.umc_wireframe.domain.model.entity.NicknameResultEntity
import com.example.umc_wireframe.domain.model.entity.ServerEntity
import com.example.umc_wireframe.domain.model.mapper.toJoinRequestEntity
import com.example.umc_wireframe.domain.model.mapper.toLoginEntity
import com.example.umc_wireframe.domain.model.mapper.toMyProfileEntity
import com.example.umc_wireframe.domain.model.mapper.toNicknameEntity
import com.example.umc_wireframe.domain.model.mapper.toTempEntity
import com.example.umc_wireframe.domain.model.toPatchAlarmTime
import com.example.umc_wireframe.domain.repository.MemberRepository
import com.example.umc_wireframe.presentation.UmcClothsOfTempApplication
import com.example.umc_wireframe.util.SharedPreferencesManager
import java.time.LocalDateTime

class MemberRepositoryImpl(
    private val datasource: ServerDatasource
) : MemberRepository {
    //join
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
    ): ServerEntity<LoginResultEntity> = datasource.postJoinSuccess(
        JoinInfo(
            email = email,
            name = name,
            nickname = nickname,
            gender = gender.toString()
        )
    ).toLoginEntity()

    //login
    override suspend fun postLogin(
        email: String,
        password: String
    ): ServerEntity<LoginResultEntity> = datasource.postLogin(
        loginRequest = AccountRequest(
            email = email,
            password = password
        )
    ).toLoginEntity()

    //manage
    override suspend fun postUserWithdraw(
    ): ServerEntity<String> = datasource.postUserWithdraw(
    ).toTempEntity()

    override suspend fun postPasswordChange(
    ): ServerEntity<String> = datasource.postPasswordChange(
    ).toTempEntity()

    override suspend fun postPasswordVerify(
        verificationCode: String
    ): ServerEntity<String> = datasource.postPasswordVerify(
        VerifyCode(
            verificationCode
        )
    ).toTempEntity()

    override suspend fun patchPasswordSuccess(
        newPassword: String
    ): ServerEntity<String> = datasource.patchPasswordSuccess(
        newPassword = NewPassword(newPassword)
    ).toTempEntity()

    override suspend fun patchNicknameChange(
        newNickname: String
    ): ServerEntity<NicknameResultEntity> = datasource.patchNicknameChange(
        newNickname = NewNickname(newNickname)
    ).toNicknameEntity()

    override suspend fun patchAlarmSet(
        alarmStatus: SetAlarm,
        alarmTime: LocalDateTime
    ): ServerEntity<String> = datasource.patchAlarmSet(
        alarmSet = AlarmSet(
            alarmStatus = alarmStatus.setType,
            alarmTime = alarmTime.toPatchAlarmTime()
        )
    ).toTempEntity()

    override suspend fun getMyProfile(
    ): ServerEntity<MyProfileResultEntity> = datasource.getMyProfile(
    ).toMyProfileEntity()

    override suspend fun postRefreshToken(
        refreshToken: String
    ): ServerEntity<LoginResultEntity> = datasource.postRefreshToken(
        refreshToken = refreshToken
    ).toLoginEntity()
}
