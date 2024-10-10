package com.example.umc_wireframe.presentation

import com.example.umc_wireframe.domain.model.ShortTermCategory

data class MainItem (
    val fcstDate: String,
    val fcstTime: String,
    val category: ShortTermCategory,
    val fcstValue: String
)