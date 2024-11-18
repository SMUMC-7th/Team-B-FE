package com.example.umc_wireframe.domain.repository

import com.example.umc_wireframe.data.model.ServerResponse
import com.example.umc_wireframe.domain.model.entity.LoginResultEntity
import com.example.umc_wireframe.domain.model.entity.NicknameResultEntity
import com.example.umc_wireframe.domain.model.entity.ServerEntity
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface MemberRepository {
    //join

    //login
    suspend fun postLogin(
        email:String,
        password:String
    ): ServerEntity<LoginResultEntity>

    //manage
    suspend fun postUserWithdraw(
        authorization: String
    ): ServerEntity<String>

    suspend fun postNicknameChange(
        authorization: String,
        newNickname: String
    ): ServerEntity<NicknameResultEntity>

    suspend fun postAlarmSet(
        authorization: String,
        alarmStatus: Boolean,
        alarmTime: String
    ): ServerEntity<String>
}