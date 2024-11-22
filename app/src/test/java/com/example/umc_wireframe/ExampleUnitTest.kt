package com.example.umc_wireframe

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.umc_wireframe.domain.model.entity.LoginResultEntity
import com.example.umc_wireframe.domain.repository.MemberRepository
import com.example.umc_wireframe.domain.repository.OotdRepository
import com.example.umc_wireframe.domain.repository.RepositoryFactory
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}

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
        }
    }

    suspend fun testLoginSuccess() {
        val loginResponse = memberRepository.postLogin(
            email = testId,
            password = testPassword
        )

        val loginResult = loginResponse.result
        assertNotNull(loginResult)

        loginResult?.token?.let {
            token = "Bearer $it"
            println("Login successful. Token: $token")
        } ?: fail("Login result does not contain a token")
    }
}
