package com.example.umc_wireframe.presentation.home

import com.example.umc_wireframe.domain.model.ShortTermRegionObject
import java.time.LocalDateTime

data class HomeUiState(
    val selectLocation: ShortTermRegionObject?,
    val temp: List<Pair<String, String>>,
    val pop: List<Pair<String, String>>, // 강수확률
    val pcp: List<Pair<String, String>>, // 강수형태
    val recommendedClothes: List<String>,
    val historyList: historyItem?
) {
    companion object {
        fun init() = HomeUiState(
            selectLocation = null,
            temp = emptyList(),
            pop = emptyList(),
            pcp = emptyList(),
            recommendedClothes = emptyList(),
            historyList = null
        )
    }

    data class historyItem(
        val img: String,
        val tag: List<String>,
        val registeredTime: LocalDateTime
    )
}
