package com.example.umc_wireframe.presentation.home

sealed interface LoginState {
    data class Login(
        val accessToken: String,
        val refreshToken: String
    ) : LoginState

    data object LoginRequire : LoginState

    data object Init:LoginState
}