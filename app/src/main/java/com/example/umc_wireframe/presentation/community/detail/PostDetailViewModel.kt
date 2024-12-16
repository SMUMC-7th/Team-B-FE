package com.example.umc_wireframe.presentation.community.detail

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.umc_wireframe.domain.CommentPagingSource
import com.example.umc_wireframe.domain.model.entity.CommentResultEntity
import com.example.umc_wireframe.domain.repository.RepositoryFactory
import com.example.umc_wireframe.presentation.UmcClothsOfTempApplication
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostDetailViewModel : ViewModel() {
    private val _uiState: MutableStateFlow<PostDetailUiState> =
        MutableStateFlow(PostDetailUiState.init)
    val uiState = _uiState.asStateFlow()

    val commentsFlow =
        MutableStateFlow<PagingData<CommentResultEntity.CommentEntity>>(PagingData.empty())

    init {
        collectPostDetails()
    }

    private fun collectPostDetails() {
        viewModelScope.launch {
            uiState.collectLatest { state ->
                if (state is PostDetailUiState.success) {
                    val postId = state.postId
                    getComments(postId)
                }
            }
        }
    }

    private fun getComments(postId: String) {
        viewModelScope.launch {
            Pager(
                config = PagingConfig(
                    pageSize = 20, // 한 페이지에 보여줄 아이템 수
                    enablePlaceholders = false // 페이지가 비어있을 때 보여줄 자리 표시자
                ),
                pagingSourceFactory = {
                    CommentPagingSource(
                        communityRepository,
                        postId
                    )
                } // PagingSource를 사용하는 팩토리 함수
            ).flow.cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    commentsFlow.value = pagingData
                }
        }
    }

    val communityRepository = RepositoryFactory.createCommunityRepository()

    fun getPost(postId: String?) = viewModelScope.launch {
        try {
            if (postId.isNullOrBlank()) _uiState.update { PostDetailUiState.fail }
            else {
                _uiState.update { prev -> PostDetailUiState.success(postId = postId) }
                communityRepository.getPost(postId).let {

                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _uiState.update { PostDetailUiState.fail }
            Toast.makeText(UmcClothsOfTempApplication.context, e.toString(), Toast.LENGTH_SHORT)
                .show()
        }
    }

    fun postComment(comment: String) = viewModelScope.launch { }

    fun postReply(reply: String, parentId: String) = viewModelScope.launch { }
}