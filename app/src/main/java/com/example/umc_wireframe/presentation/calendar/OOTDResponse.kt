package com.example.umc_wireframe.presentation.calendar

data class OOTDResponse(
    val status: String,
    val code: String,
    val message: String,
    val isSuccess: Boolean,
    val result: Result
)

data class Result(
    val ootds: List<Ootd>
)

data class Ootd(
    val image: String,
    val minTemperature: Int,
    val maxTemperature: Int,
    val weatherDescription: String,
    val hashtags: List<String>,
    val date: String
)
