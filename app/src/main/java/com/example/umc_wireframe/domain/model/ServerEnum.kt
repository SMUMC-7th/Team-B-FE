package com.example.umc_wireframe.domain.model

enum class Gender {
    MALE, FEMALE, ERROR
}

enum class SetAlarm(val setType: Boolean){
    POST(true), DELETE(false)
}

fun String.toGender() = when(this){
    "MALE" ->Gender.MALE
    "FEMALE" -> Gender.FEMALE
    else -> Gender.ERROR
}

