package com.example.umc_wireframe.presentation.register

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umc_wireframe.data.remote.NewNickname
import com.example.umc_wireframe.domain.model.Gender
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

    fun postLogin(email: String, password: String, isSuccess: () -> Unit) = viewModelScope.launch {
        try {
            memberRepository.postLogin(
                email, password
            ).result?.let {
                val prefManager = SharedPreferencesManager(UmcClothsOfTempApplication.context)
                prefManager.saveAccessToken(it.accessToken!!)
                prefManager.saveRefreshToken(it.refreshToken!!)
            }.run {
                isSuccess()
            }
        } catch (e: Exception) {
            Toast.makeText(UmcClothsOfTempApplication.context, e.toString(), Toast.LENGTH_SHORT)
                .show()
        }
    }


    fun postJoinReq(email: String, password: String, isSuccess: () -> Unit) =
        viewModelScope.launch {
            try {
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

                isSuccess()
            } catch (e: Exception) {
                Toast.makeText(UmcClothsOfTempApplication.context, e.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        }

    fun postJoinVerify(verificationCode: String, isSuccess: () -> Unit) = viewModelScope.launch {
        try {
            if (loginState.value is LoginReqState.ReqRegisterState) {
                _loginState.update { prev ->
                    LoginReqState.VerifyRegisterState(
                        email = (prev as LoginReqState.ReqRegisterState).email,
                        password = (prev as LoginReqState.ReqRegisterState).password,
                        verificationCode = verificationCode
                    )
                }
            } else if (loginState.value is LoginReqState.VerifyRegisterState) {
                _loginState.update { prev ->
                    LoginReqState.VerifyRegisterState(
                        email = (prev as LoginReqState.VerifyRegisterState).email,
                        password = (prev as LoginReqState.VerifyRegisterState).password,
                        verificationCode = verificationCode
                    )
                }
            }
            (loginState.value as? LoginReqState.VerifyRegisterState)?.let {
                memberRepository.postJoinVerify(
                    email = it.email,
                    verificationCode = it.verificationCode
                )
            }
            isSuccess()
        } catch (e: Exception) {
            Toast.makeText(UmcClothsOfTempApplication.context, e.toString(), Toast.LENGTH_SHORT)
                .show()
        }
    }

    fun postJoinSuccess(name: String, nickname: String, gender: Gender, isSuccess: () -> Unit) =
        viewModelScope.launch {
            try {
                if (loginState.value is LoginReqState.VerifyRegisterState) {
                    _loginState.update { prev ->
                        (prev as LoginReqState.VerifyRegisterState).let {
                            LoginReqState.PostRegisterState(
                                email = prev.email,
                                password = prev.password,
                                verificationCode = prev.verificationCode,
                                name = name,
                                nickName = nickname,
                                gender = gender
                            )
                        }
                    }
                } else if (loginState.value is LoginReqState.PostRegisterState) {
                    _loginState.update { prev ->
                        (prev as LoginReqState.PostRegisterState).let {
                            LoginReqState.PostRegisterState(
                                email = prev.email,
                                password = prev.password,
                                verificationCode = prev.verificationCode,
                                name = name,
                                nickName = nickname,
                                gender = gender
                            )
                        }
                    }
                }
                (loginState.value as? LoginReqState.PostRegisterState)?.let {
                    memberRepository.postJoinSuccess(
                        name = it.name,
                        email = it.email,
                        nickname = it.nickName,
                        gender = it.gender
                    )
                }
                isSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(UmcClothsOfTempApplication.context, e.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        }
}