package com.example.umc_wireframe.presentation.community.communtiy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.umc_wireframe.domain.CommunityPostListPagingSource
import com.example.umc_wireframe.domain.repository.RepositoryFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class CommunityViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CommunityUiState.init())
    val uiState = _uiState.asStateFlow()

    private val communityRepository = RepositoryFactory.createCommunityRepository()
    private val pagingSource = CommunityPostListPagingSource(communityRepository)

    val communityPagingList = Pager(
        config = PagingConfig(
            pageSize = 20, // 한 페이지에 보여줄 아이템 수
            enablePlaceholders = false // 페이지가 비어있을 때 보여줄 자리 표시자
        ),
        pagingSourceFactory = { pagingSource } // PagingSource를 사용하는 팩토리 함수
    ).flow.cachedIn(viewModelScope)

}