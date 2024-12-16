package com.example.umc_wireframe.presentation.community.write

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.umc_wireframe.domain.repository.RepositoryFactory
import com.example.umc_wireframe.presentation.UmcClothsOfTempApplication
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostWriteViewModel : ViewModel() {
    private val _uiState: MutableStateFlow<PostWriteUiStates> =
        MutableStateFlow(PostWriteUiStates.init)
    val uiState = _uiState.asStateFlow()

    val communityRepository = RepositoryFactory.createCommunityRepository()

    fun postPost(
        title: String, content: String
    ) = viewModelScope.launch {
        try {
            communityRepository.postPost(title = title, content = content).let {
                if (it.isSuccess) {
                    _uiState.update { prev ->
                        PostWriteUiStates.success
                    }
                } else {
                    _uiState.update { prev ->
                        PostWriteUiStates.fail
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(UmcClothsOfTempApplication.context, e.toString(), Toast.LENGTH_SHORT)
                .show()
        }
    }
}
