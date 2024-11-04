package com.example.umc_wireframe.presentation.home

data class HomeItem(
    val selectLocation: String,
    val temp: Int
) {
    companion object {
        fun init() = HomeItem(
            selectLocation = "null",
            temp = 0
        )
    }
}