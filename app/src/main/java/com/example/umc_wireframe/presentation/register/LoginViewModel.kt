package com.example.umc_wireframe.presentation.register

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umc_wireframe.domain.repository.RepositoryFactory
import com.example.umc_wireframe.presentation.UmcClothsOfTempApplication
import com.example.umc_wireframe.util.SharedPreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _loginState = MutableStateFlow<LoginReqState>(LoginReqState.init())
    val loginState = _loginState.asStateFlow()

    val memberRepository = RepositoryFactory.createMemberRepository()

    fun postLogin(email: String, password:String, isSuccess: () -> Unit) = viewModelScope.launch{
        try {
            memberRepository.postLogin(
                email, password
            ).result?.let{
                val prefManager = SharedPreferencesManager(UmcClothsOfTempApplication.context)
                prefManager.saveAccessToken(it.accessToken!!)
                prefManager.saveRefreshToken(it.refreshToken!!)
            }
            isSuccess()
        }catch (e:Exception){
            Toast.makeText(UmcClothsOfTempApplication.context, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }


    fun postJoinReq(email: String, password: String) = viewModelScope.launch {
        _loginState.update {
            LoginReqState.ReqRegisterState(
                email = email,
                password = password
            )
        }
        (loginState.value as? LoginReqState.ReqRegisterState)?.let {
            memberRepository.postJoinResquest(
                email = it.email,
                password = it.password
            )
        }
    }

    fun postJoinVerify() = viewModelScope.launch {
        (loginState.value as? LoginReqState.VerifyRegisterState)?.let {
            memberRepository.postJoinVerify(
                email = it.email,
                verificationCode = it.verificationCode
            )
        }
    }

    fun postJoinSuccess() = viewModelScope.launch {
        (loginState.value as? LoginReqState.PostRegisterState)?.let {
            memberRepository.postJoinSuccess(
                name = it.name,
                email = it.email,
                nickname = it.nickName,
                gender = it.gender
            )
        }
    }
}