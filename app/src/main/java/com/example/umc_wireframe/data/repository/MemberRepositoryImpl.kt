package com.example.umc_wireframe.data.repository

import com.example.umc_wireframe.data.model.ServerResponse
import com.example.umc_wireframe.data.remote.ServerDatasource
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
import com.example.umc_wireframe.domain.repository.MemberRepository

class MemberRepositoryImpl(
    private val datasource: ServerDatasource
) : MemberRepository {
    //join
    override suspend fun postJoinResquest(
        email: String,
        password: String
    ): ServerEntity<JoinRequestResultEntity> = datasource.postJoinResquest(
        email = email,
        password = password
    ).toJoinRequestEntity()

    override suspend fun postVerifyJoin(
        email: String,
        verificationCode: String
    ): ServerEntity<String> = datasource.postVerifyJoin(
        email = email,
        verificationCode = verificationCode
    ).toTempEntity()

    override suspend fun postJoinSuccess(
        email: String,
        password: String,
        nickname: String,
        gender: Gender
    ): ServerEntity<String> = datasource.postJoinSuccess(
        email = email,
        password = password,
        nickname = nickname,
        gender = gender.toString()
    ).toTempEntity()

    //login
    override suspend fun postLogin(
        email: String,
        password: String
    ): ServerEntity<LoginResultEntity> = datasource.postLogin(
        email = email,
        password = password
    ).toLoginEntity()

    //manage
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
        verificationCode: String
    ): ServerEntity<String> = datasource.postPasswordVerify(
        verificationCode
    ).toTempEntity()

    override suspend fun postPasswordSuccess(
        newPassword: String
    ): ServerEntity<String> = datasource.postPasswordSuccess(
        newPassword
    ).toTempEntity()

    override suspend fun postNicknameChange(
        authorization: String,
        newNickname: String
    ): ServerEntity<NicknameResultEntity> = datasource.postNicknameChange(
        authorization = authorization,
        newNickname = newNickname
    ).toNicknameEntity()

    override suspend fun postAlarmSet(
        authorization: String,
        alarmStatus: SetAlarm,
        alarmTime: String
    ): ServerEntity<String> = datasource.postAlarmSet(
        authorization = authorization,
        alarmStatus = alarmStatus.setType,
        alarmTime = alarmTime
    ).toTempEntity()

    override suspend fun getMyProfile(
        authorization: String
    ): ServerEntity<MyProfileResultEntity> = datasource.getMyProfile(
        authorization
    ).toMyProfileEntity()
}
