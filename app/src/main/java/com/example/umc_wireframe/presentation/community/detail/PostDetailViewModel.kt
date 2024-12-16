package com.example.umc_wireframe.presentation.community.detail

import android.util.Log
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostDetailViewModel : ViewModel() {
    private val _uiState: MutableStateFlow<PostDetailUiState> =
        MutableStateFlow(PostDetailUiState.init)
    val uiState = _uiState.asStateFlow()

    val communityRepository = RepositoryFactory.createCommunityRepository()

    val commentsFlow = uiState
        .flatMapLatest { state ->
            if (state is PostDetailUiState.success) {
                Pager(
                    config = PagingConfig(
                        pageSize = 20,
                        enablePlaceholders = false
                    ),
                    pagingSourceFactory = {
                        CommentPagingSource(
                            communityRepository,
                            state.postId // 성공 상태의 postId 사용
                        )
                    }
                ).flow
            } else {
                // uiState가 success가 아닐 경우 빈 Flow 반환
                flowOf()
            }
        }.cachedIn(viewModelScope) // Flow를 캐싱



    fun getPost(postId: String?) = viewModelScope.launch {
        try {
            if (postId.isNullOrBlank()) _uiState.update { PostDetailUiState.fail }
            else {
                _uiState.update { prev -> PostDetailUiState.success(postId = postId) }
                communityRepository.getPost(postId).let {
                    it.result?.run {
                        _uiState.update { prev ->
                            (prev as PostDetailUiState.success).copy(
                                writer = memberName,
                                title = title,
                                content = content
                            )
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _uiState.update { PostDetailUiState.fail }
            Toast.makeText(UmcClothsOfTempApplication.context, e.toString(), Toast.LENGTH_SHORT)
                .show()
        }
    }

    fun postComment(comment: String) = viewModelScope.launch {
        try {
            if (uiState.value is PostDetailUiState.success) {
                communityRepository.postComment(
                    postId = (uiState.value as PostDetailUiState.success).postId,
                    comment = comment,
                    parentId = 0
                )
                _uiState.update { prev->
                    (prev as PostDetailUiState.success).copy(
                        getComment = !prev.getComment
                    )
                }
            } else {
                Toast.makeText(UmcClothsOfTempApplication.context, "게시글 로드 실패", Toast.LENGTH_SHORT)
                    .show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(UmcClothsOfTempApplication.context, e.toString(), Toast.LENGTH_SHORT)
                .show()
        }
    }

    fun postReply(reply: String, parentId: Int) = viewModelScope.launch {
        try {
            if (uiState.value is PostDetailUiState.success) {
                communityRepository.postComment(
                    postId = (uiState.value as PostDetailUiState.success).postId,
                    comment = reply,
                    parentId = parentId
                )
                _uiState.update { prev->
                    (prev as PostDetailUiState.success).copy(
                        getComment = !prev.getComment
                    )
                }
            } else {
                Toast.makeText(UmcClothsOfTempApplication.context, "게시글 로드 실패", Toast.LENGTH_SHORT)
                    .show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(UmcClothsOfTempApplication.context, e.toString(), Toast.LENGTH_SHORT)
                .show()
        }
    }
}