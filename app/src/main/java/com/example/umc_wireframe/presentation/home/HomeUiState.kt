package com.example.umc_wireframe.presentation.home

import com.example.umc_wireframe.domain.model.ShortTermRegionObject

data class HomeUiState(
    val selectLocation: ShortTermRegionObject?,
    val temp: String,
    val pop: String, //강수확률
    val pcp: String, //강수형태
    val recommendedClothes: List<String>
) {
    companion object {
        fun init() = HomeUiState(
            selectLocation = null,
            temp = "",
            "",
            "",
            recommendedClothes = emptyList()
        )
    }
}