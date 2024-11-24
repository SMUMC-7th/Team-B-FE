package com.example.umc_wireframe.data.remote

import com.example.umc_wireframe.data.model.JoinRequestResultResponse
import com.example.umc_wireframe.data.model.LoginResultResponse
import com.example.umc_wireframe.data.model.MyProfileResultResponse
import com.example.umc_wireframe.data.model.NicknameResultResponse
import com.example.umc_wireframe.data.model.OotdResultResponse
import com.example.umc_wireframe.data.model.RecommendedHashtagResultResponse
import com.example.umc_wireframe.data.model.ServerResponse
import com.example.umc_wireframe.domain.model.Hashtag
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ServerDatasource { // 회원가입, 비밀번호 변경 추가 필요

    //ootd
    @GET("api/recommendation")
    suspend fun getRecommendedHashtag(
        @Header("Authorization") authorization: String,
        @Query("maxTemperature") maxTemperature: Int,
        @Query("minTemperature") minTemperature: Int
    ): ServerResponse<RecommendedHashtagResultResponse>


    @GET("api/ootds/past")
    suspend fun getOotdPastForTemp(
        @Header("Authorization") authorization: String,
        @Query("maxTemperature") maxTemperature: Int,
        @Query("minTemperature") minTemperature: Int
    ): ServerResponse<OotdResultResponse>

    @POST("api/ootds")
    suspend fun postOotd(
        @Header("Authorization") authorization: String,
        @Part image: MultipartBody.Part,
        @Query("maxTemperature") maxTemperature: Int,
        @Query("minTemperature") minTemperature: Int,
        @Query("hashtags") hashtags: List<Hashtag> // 최대 3개
    ): ServerResponse<String>

    @GET("api/ootds")
    suspend fun getOotdPastForYearMonth(
        @Header("Authorization") authorization: String,
        @Query("year") year: Int,
        @Query("month") month: Int
    ): ServerResponse<OotdResultResponse>

    //member(login, join, manage)
    //join
    @POST("api/users/signup/request-and-verify")
    suspend fun postJoinResquest(
        @Body loginRequest: AccountRequest
    ): ServerResponse<JoinRequestResultResponse>

    @POST("api/users/signup/verify-code")
    suspend fun postJoinVerify(
        @Body joinVerify: JoinVerify
    ): ServerResponse<String>

    @POST("api/users/signup")
    suspend fun postJoinSuccess(
        @Body joinInfo: JoinInfo
    ): ServerResponse<String>

    //login
    @POST("api/users/login")
    suspend fun postLogin(
        @Body loginRequest: AccountRequest
    ): ServerResponse<LoginResultResponse>

    //manage
    @POST("api/users/withdraw")
    suspend fun postUserWithdraw(
        @Header("Authorization") authorization: String
    ): ServerResponse<String>

    @POST("api/users/password/change/request")
    suspend fun postPasswordChange(
        @Header("Authorization") authorization: String
    ): ServerResponse<String>

    @POST("api/users/password/change/verify")
    suspend fun postPasswordVerify(
        @Header("Authorization") authorization: String,
        @Body verificationCode: VerifyCode
    ): ServerResponse<String>

    @POST("api/users/password/change/complete")
    suspend fun postPasswordSuccess(
        @Header("Authorization") authorization: String,
        @Body newPassword: NewPassword
    ): ServerResponse<String>

    @POST("api/users/nickname")
    suspend fun postNicknameChange(
        @Header("Authorization") authorization: String,
        @Body newNickname: NewNickname
    ): ServerResponse<NicknameResultResponse>

    @POST("api/users/alarm-settings")
    suspend fun postAlarmSet(
        @Header("Authorization") authorization: String,
        @Body alarmSet: AlarmSet
    ): ServerResponse<String>

    @GET("api/users/profile")
    suspend fun getMyProfile(
        @Header("Authorization") authorization: String
    ): ServerResponse<MyProfileResultResponse>

    @POST("api/users/token/reissue")
    suspend fun postRefreshToken(
        @Header("Authorization") authorization: String,
        @Body refreshToken: RefreshToken
    ): ServerResponse<LoginResultResponse>
}

data class AccountRequest(
    @Query("email") val email: String,
    @Query("password") val password: String
)

data class JoinVerify(
    @Query("email") val email: String,
    @Query("verificationCode") val verificationCode: String
)

data class JoinInfo(
    @Query("email") val email: String,
    @Query("name") val name: String,
    @Query("nickname") val nickname: String,
    @Query("gender") val gender: String
)

data class MaxMinTemperature(
    @Query("maxTemperature") val maxTemperature: Int,
    @Query("minTemperature") val minTemperature: Int
)

data class VerifyCode(
    @Query("verificationCode") val verificationCode: String
)

data class NewPassword(
    @Query("newPassword") val newPassword: String
)

data class RefreshToken(
    @Query("refreshToken") val refreshToken: String
)

data class NewNickname(
    @Query("newNickname") val newNickname: String
)

data class AlarmSet(
    @Query("alarmStatus") val alarmStatus: Boolean, // true 추가, false 삭제
    @Query("alarmTime") val alarmTime: String // "09:00" 무조건 이 형식 5글자
)