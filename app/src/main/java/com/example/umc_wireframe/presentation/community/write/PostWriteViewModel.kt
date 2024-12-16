package com.example.umc_wireframe.presentation.community.write

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PostWriteViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(0)
    val uiState = _uiState.asStateFlow()
}