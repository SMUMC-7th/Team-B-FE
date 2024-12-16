package com.example.umc_wireframe.presentation.community.communtiy

import androidx.paging.PagingData

data class CommunityUiState (
    val list: PagingData<CommunityListItem>
){
    companion object{
        fun init() = CommunityUiState(PagingData.empty())
    }
}