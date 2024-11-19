package com.example.umc_wireframe

import org.junit.Test

import org.junit.Assert.*

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

fun main(){
    val testId = "test"
    val testPassword = "1234"
}

class TestServer(val testId: String, val testPassword: String){

}

fun getToken(){
}
