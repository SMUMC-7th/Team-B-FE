package com.example.umc_wireframe.presentation.my

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umc_wireframe.domain.repository.MemberRepository
import com.example.umc_wireframe.domain.repository.RepositoryFactory
import com.example.umc_wireframe.presentation.UmcClothsOfTempApplication
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<MyUiState>(MyUiState.init())
    val uiState = _uiState.asStateFlow()

    val memberRepository: MemberRepository = RepositoryFactory.createMemberRepository()

    fun getMyProfile(isFailed: () -> Unit) = viewModelScope.launch {
        try {
            memberRepository.getMyProfile().result?.let {
                _uiState.update { prev ->
                    prev.copy(
                        name = it.name,
                        nickName = it.nickname
                    )
                }
            }
        } catch (e: Exception) {
            Toast.makeText(UmcClothsOfTempApplication.context, e.toString(), Toast.LENGTH_SHORT).show()
            e.printStackTrace()
            isFailed()
        }
    }
}