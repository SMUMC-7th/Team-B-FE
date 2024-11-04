package com.example.umc_wireframe.presentation.home

import com.example.umc_wireframe.domain.model.ShortTermRegionObject

data class HomeItem(
    val selectLocation: ShortTermRegionObject?,
    val temp: Int
) {
    companion object {
        fun init() = HomeItem(
            selectLocation = null,
            temp = 0
        )
    }
}