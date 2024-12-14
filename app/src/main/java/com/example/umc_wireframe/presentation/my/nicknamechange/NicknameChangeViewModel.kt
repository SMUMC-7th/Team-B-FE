package com.example.umc_wireframe.presentation.my.nicknamechange

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umc_wireframe.domain.repository.MemberRepository
import com.example.umc_wireframe.domain.repository.RepositoryFactory
import com.example.umc_wireframe.presentation.UmcClothsOfTempApplication
import kotlinx.coroutines.launch

class NicknameChangeViewModel : ViewModel() {
    val memberRepository: MemberRepository = RepositoryFactory.createMemberRepository()

    fun changeNickname(newNickname: String, isSuccess: () -> Unit) = viewModelScope.launch {
        try {
            memberRepository.patchNicknameChange(
                newNickname = newNickname
            )

            isSuccess()
        } catch (e: Exception) {
            Toast.makeText(UmcClothsOfTempApplication.context, e.toString(), Toast.LENGTH_SHORT)
                .show()
            e.printStackTrace()
        }

    }

}