package com.example.umc_wireframe.domain.model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.toPatchAlarmTime(): String{
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return this.format(formatter)
}