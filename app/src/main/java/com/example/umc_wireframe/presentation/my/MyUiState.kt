package com.example.umc_wireframe.presentation.my

import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime

data class MyUiState(
    val name: String,
    val nickName: String,
    val profileImageUrl: String,
    val alarmState: AlarmState
) {
    data class AlarmState(
        val hour: Int,
        val min: Int,
        val isSet: Boolean
    ):Serializable

    companion object {
        fun init() = MyUiState(
            name = "",
            nickName = "",
            profileImageUrl = "",
            alarmState = AlarmState(hour = 0, min = 0, isSet = false)
        )
    }
}