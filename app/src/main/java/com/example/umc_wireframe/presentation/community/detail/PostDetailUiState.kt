package com.example.umc_wireframe.presentation.community.detail

import androidx.paging.PagingData

sealed class PostDetailUiState {
    data object init : PostDetailUiState()
    data class success(
        val postId: String,
        val wirter: String = "",
        val title: String = "",
        val content: String = "",
    ) : PostDetailUiState()

    data object fail : PostDetailUiState()
}