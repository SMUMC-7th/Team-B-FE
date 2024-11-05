package com.example.umc_wireframe.presentation.home

import com.example.umc_wireframe.domain.model.ShortTermRegionObject

data class HomeUiState(
    val selectLocation: ShortTermRegionObject?,
    val temp: Int
) {
    companion object {
        fun init() = HomeUiState(
            selectLocation = null,
            temp = 0
        )
    }
}