package com.example.umc_wireframe.presentation.register

import com.example.umc_wireframe.domain.model.Gender

sealed interface LoginReqState {
    data class LoginState(
        val email: String,
        val password: String
    ) : LoginReqState

    data class ReqRegisterState(
        val email: String,
        val password: String
    ): LoginReqState

    data class VerifyRegisterState(
        val email: String,
        val password: String,
        val verificationCode: String
    ):LoginReqState

    data class PostRegisterState(
        val email: String,
        val password: String,
        val gender: Gender,
        val name: String,
        val nickName: String,
        val verificationCode: String
    ) : LoginReqState

    companion object {
        fun init() = LoginState(
            email = "",
            password = ""
        )
    }
}