package com.example.umc_wireframe.presentation.community.detail

import androidx.paging.PagingData

sealed class PostDetailUiState {
    data object init : PostDetailUiState()
    data class success(
        val id: String,
        val wirter: String,
        val title: String,
        val content: String,
        val commentList: PagingData<PostDetailCommentItem>
    ) : PostDetailUiState()

    data object fail : PostDetailUiState()
}