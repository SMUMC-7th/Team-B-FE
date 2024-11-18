package com.example.umc_wireframe.data.repository

import com.example.umc_wireframe.data.remote.ServerDatasource
import com.example.umc_wireframe.domain.model.entity.LoginResultEntity
import com.example.umc_wireframe.domain.model.entity.NicknameResultEntity
import com.example.umc_wireframe.domain.model.entity.ServerEntity
import com.example.umc_wireframe.domain.model.mapper.toLoginEntity
import com.example.umc_wireframe.domain.model.mapper.toNicknameEntity
import com.example.umc_wireframe.domain.model.mapper.toTempEntity
import com.example.umc_wireframe.domain.repository.MemberRepository

class MemberRepositoryImpl(
    private val datasource: ServerDatasource
) : MemberRepository {
    //join


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

    override suspend fun postNicknameChange(
        authorization: String,
        newNickname: String
    ): ServerEntity<NicknameResultEntity> = datasource.postNicknameChange(
        authorization = authorization,
        newNickname = newNickname
    ).toNicknameEntity()

    override suspend fun postAlarmSet(
        authorization: String,
        alarmStatus: Boolean,
        alarmTime: String
    ): ServerEntity<String> = datasource.postAlarmSet(
        authorization = authorization,
        alarmStatus = alarmStatus,
        alarmTime = alarmTime
    ).toTempEntity()
}
