package com.example.umc_wireframe.presentation.community.communtiy

import androidx.paging.PagingData
import com.example.umc_wireframe.domain.model.entity.PostEntity

data class CommunityUiState(
    val list: PagingData<PostEntity>
) {
    companion object {
        fun init() = CommunityUiState(PagingData.empty())
    }
}