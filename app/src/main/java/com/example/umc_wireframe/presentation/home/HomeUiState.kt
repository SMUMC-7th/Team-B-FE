package com.example.umc_wireframe.presentation.home

import com.example.umc_wireframe.domain.model.ShortTermRegionObject
import com.example.umc_wireframe.domain.model.entity.OotdResultEntity
import com.example.umc_wireframe.domain.model.entity.RecommendedHashtagResultEntity
import java.time.LocalDateTime

data class HomeUiState(
    val selectLocation: ShortTermRegionObject?,
    val maxTemp: Pair<String, String>,
    val minTemp: Pair<String, String>,
    val pop: List<Pair<String, String>>, // 강수확률
    val pcp: List<Pair<String, String>>, // 강수형태
    val recommendedClothes: List<RecommendedHashtagResultEntity.Recommendation>,
    val historyList: List<OotdResultEntity.Ootd>
) {
    companion object {
        fun init() = HomeUiState(
            selectLocation = null,
            maxTemp = "0" to "0",
            minTemp = "0" to "0",
            pop = emptyList(),
            pcp = emptyList(),
            recommendedClothes = emptyList(),
            historyList = emptyList()
        )
    }
}
