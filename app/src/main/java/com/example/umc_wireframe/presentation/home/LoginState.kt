package com.example.umc_wireframe.presentation.home

import com.example.umc_wireframe.data.remote.RefreshToken

sealed interface LoginState {
    data class Login(
        val jwtToken: String,
        val refreshToken: String
    ) : LoginState

    data class RefreshRequire(
        val jwtToken: String,
        val refreshToken: String
    ) : LoginState

    data object LoginRequire : LoginState

    data object Init:LoginState
}