package com.example.umc_wireframe.presentation.community.detail

sealed class PostDetailUiState {
    data object init : PostDetailUiState()
    data class success(
        val postId: String,
        val writer: String = "",
        val title: String = "",
        val content: String = "",
        val getComment: Boolean = false
    ) : PostDetailUiState()

    data object fail : PostDetailUiState()
}