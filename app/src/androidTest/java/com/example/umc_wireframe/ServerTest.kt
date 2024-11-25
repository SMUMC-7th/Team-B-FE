package com.example.umc_wireframe

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.umc_wireframe.data.remote.JoinInfo
import com.example.umc_wireframe.domain.model.Gender
import com.example.umc_wireframe.domain.model.SetAlarm
import com.example.umc_wireframe.domain.repository.MemberRepository
import com.example.umc_wireframe.domain.repository.OotdRepository
import com.example.umc_wireframe.domain.repository.RepositoryFactory
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import java.time.LocalDateTime


@RunWith(AndroidJUnit4::class)
class TestServerInstrumented {

    private val testId = "test"
    private val testPassword = "1234"

    private val testJoinId = "didehddnjs89@gmail.com"
    private val testJoinPassword = "1234"
    private lateinit var memberRepository: MemberRepository
    private lateinit var ootdRepository: OotdRepository
    private var token: String = ""
    private var refreshToken: String = ""

    @Before
    fun setup() {
        // 테스트용 Mock Repository 설정
        memberRepository = RepositoryFactory.createMemberRepository()
        ootdRepository = RepositoryFactory.createOotdRepository()
    }

    @Test
    fun test() {
        runBlocking {
//            testJoinRequest()
//            testJoinVerify("451697")
//            testJoinSuccess()
//            testWithdraw()


            testLoginSuccess()

            if (token != "") {
//                postRefreshToken()
//                testOotdCheck()

//                postChangeNickname()
//                postAlaramSet()
//                getMyInfo()

//                testChangePwRequest()
//            testChangePwVerify("165144")
                testChangePwSuccess() // 오류, front 측에선 문제를 찾지 못했음.
            }
        }
    }

    suspend fun testLoginSuccess() {
        try {
            val loginResponse = memberRepository.postLogin(
                email = testJoinId,
                password = testJoinPassword
            )

            val loginResult = loginResponse.result
            assertNotNull(loginResult)

            loginResult?.let {
                token = "Bearer ${it.accessToken}"
                refreshToken = "${it.refreshToken}"
                Log.d("LoginSuccess", "Token: $token")
            } ?: fail("Login result does not contain a token")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun testJoinRequest() {
        memberRepository.postJoinResquest(
            email = "didehddnjs89@gmail.com",
            password = "22961051"
        ).let {
            Log.d("joinRequest", it.toString())
        }
    }

    suspend fun testJoinVerify(code: String) {
        memberRepository.postJoinVerify(
            email = "didehddnjs89@gmail.com",
            verificationCode = code
        ).let {
            Log.d("joinVerify", it.toString())
        }
    }

    suspend fun testJoinSuccess() {
        memberRepository.postJoinSuccess(
            email = "didehddnjs89@gmail.com",
            name = "teset!",
            gender = Gender.MALE,
            nickname = "test"
        ).let {
            Log.d("joinSuccess", it.toString())
        }
    }

    suspend fun testWithdraw() {
        val token = memberRepository.postLogin(
            email = testJoinId,
            password = "string"
        ).result?.accessToken

        memberRepository.postUserWithdraw(
            authorization = "Bearer $token"
        ).let {
            Log.d("withdraw success", it.toString())
        }
    }

    suspend fun testOotdCheck() {
        val maxTemp = 10
        val minTemp = 2

        suspend fun checkOotdData() {
            val hashTagResponse = ootdRepository.getRecommendedHashtag(
                authorization = token,
                maxTemperature = maxTemp,
                minTemperature = minTemp
            )

            val pastOotdResponseForTemp = ootdRepository.getOotdPastForTemp(
                authorization = token,
                maxTemperature = maxTemp,
                minTemperature = minTemp
            )

            val pastOotdResponseForMonth = ootdRepository.getOotdPastForYearMonth(
                authorization = token,
                year = 2024,
                month = 11
            )

            Log.d(
                "checkOotdSuccess", "$hashTagResponse" +
                        "\n$pastOotdResponseForTemp" +
                        "\n$pastOotdResponseForMonth"
            )
        }
//        suspend fun postOotdData() {
//            ootdRepository.postOotd(
//                authorization = token,
//                maxTemperature = maxTemp,
//                minTemperature = minTemp,
//                hashtags = listOf(Hashtag.ADDED_COAT, Hashtag.COAT, Hashtag.FLEECE),
//                image =
//            )
//        }

        checkOotdData()
    }


    suspend fun testChangePwRequest() {
        memberRepository.postPasswordChange(
            authorization = token
        ).let {
            Log.d("cahngePw", it.toString())
        }
    }

    suspend fun testChangePwVerify(code: String) {
        memberRepository.postPasswordVerify(
            authorization = token,
            verificationCode = code
        ).let {
            Log.d("cahngePw", it.toString())
        }
    }

    suspend fun testChangePwSuccess() {
        try {
            memberRepository.patchPasswordSuccess(
                authorization = token,
                newPassword = "1234"
            ).let {
                Log.d("cahngePw", it.toString())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun getMyInfo() {
        memberRepository.getMyProfile(
            authorization = token
        )
    }

    suspend fun postRefreshToken() {
        memberRepository.postRefreshToken(
            authorization = token,
            refreshToken = refreshToken
        ).let {
            it.result?.let {
                token = "Bearer ${it.accessToken}"
                refreshToken = "${it.refreshToken}"
            }
        }
    }

    suspend fun postChangeNickname() {
        try {
            memberRepository.patchNicknameChange(
                authorization = token,
                newNickname = "change success"
            ).let {
                Log.d("cahngeNickname", it.toString())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun postAlaramSet() {
        try {
            memberRepository.patchAlarmSet(
                authorization = token,
                alarmStatus = SetAlarm.POST,
                alarmTime = LocalDateTime.now()
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
