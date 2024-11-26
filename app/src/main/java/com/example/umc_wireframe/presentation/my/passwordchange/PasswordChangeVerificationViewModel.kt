package com.example.umc_wireframe.presentation.my.passwordchange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umc_wireframe.domain.repository.MemberRepository
import com.example.umc_wireframe.domain.repository.RepositoryFactory
import kotlinx.coroutines.launch

class PasswordChangeVerificationViewModel : ViewModel() {
    private val memberRepository: MemberRepository = RepositoryFactory.createMemberRepository()

    fun verifyPasswordChangeReq() = viewModelScope.launch {
        memberRepository.postPasswordChange()
    }

    fun verifyPasswordChange(verificationCode: String, isSuccess: () -> Unit) = viewModelScope.launch {
        memberRepository.postPasswordVerify(
            verificationCode = verificationCode
        )
    }
}