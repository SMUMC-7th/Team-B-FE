package com.example.umc_wireframe.presentation.community.detail

import androidx.lifecycle.ViewModel
import com.example.umc_wireframe.domain.repository.RepositoryFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PostDetailViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(PostDetailUiState.init)
    val uiState = _uiState.asStateFlow()

    val communityRepository = RepositoryFactory.createCommunityRepository()
}