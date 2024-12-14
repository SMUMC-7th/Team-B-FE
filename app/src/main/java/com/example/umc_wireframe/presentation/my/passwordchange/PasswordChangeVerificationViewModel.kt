package com.example.umc_wireframe.presentation.my.passwordchange

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umc_wireframe.domain.repository.MemberRepository
import com.example.umc_wireframe.domain.repository.RepositoryFactory
import com.example.umc_wireframe.presentation.UmcClothsOfTempApplication
import kotlinx.coroutines.launch

class PasswordChangeVerificationViewModel : ViewModel() {
    private val memberRepository: MemberRepository = RepositoryFactory.createMemberRepository()

    fun verifyPasswordChangeReq() = viewModelScope.launch {
        memberRepository.postPasswordChange().run{
            Toast.makeText(UmcClothsOfTempApplication.context, "인증번호가 발송되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    fun verifyPasswordChange(verificationCode: String, isSuccess: () -> Unit) =
        viewModelScope.launch {
            try {
                memberRepository.postPasswordVerify(
                    verificationCode = verificationCode
                )
                isSuccess()
            } catch (e: Exception) {
                Toast.makeText(UmcClothsOfTempApplication.context, e.toString(), Toast.LENGTH_SHORT).show()
            }
        }
}