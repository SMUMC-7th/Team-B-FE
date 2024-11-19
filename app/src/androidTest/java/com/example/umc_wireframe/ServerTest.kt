package com.example.umc_wireframe

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.umc_wireframe.domain.model.Hashtag
import com.example.umc_wireframe.domain.model.entity.LoginResultEntity
import com.example.umc_wireframe.domain.repository.MemberRepository
import com.example.umc_wireframe.domain.repository.OotdRepository
import com.example.umc_wireframe.domain.repository.RepositoryFactory
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TestServerInstrumented {

    private val testId = "test"
    private val testPassword = "1234"
    private lateinit var memberRepository: MemberRepository
    private lateinit var ootdRepository: OotdRepository
    private var token: String = ""

    @Before
    fun setup() {
        // 테스트용 Mock Repository 설정
        memberRepository = RepositoryFactory.createMemberRepository()
        ootdRepository = RepositoryFactory.createOotdRepository()
    }

    @Test
    fun test() {
        runBlocking {
            testLoginSuccess()

            if(token != ""){
                testOotdCheck()
            }
        }
    }

    suspend fun testLoginSuccess() {
        try {

            val loginResponse = memberRepository.postLogin(
                email = testId,
                password = testPassword
            )

            val loginResult = loginResponse.result
            assertNotNull(loginResult)

            loginResult?.accessToken?.let {
                token = "Bearer $it"
                Log.d("Login success", "Token: $token")
            } ?: fail("Login result does not contain a token")
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    suspend fun testOotdCheck(){
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

            Log.d("checkOotd success", "$hashTagResponse" +
                    "\n$pastOotdResponseForTemp" +
                    "\n$pastOotdResponseForMonth")
        }
//        fun postOotdData() {
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
}
