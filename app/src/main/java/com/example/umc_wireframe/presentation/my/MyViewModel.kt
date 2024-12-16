package com.example.umc_wireframe.presentation.my

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umc_wireframe.data.remote.AuthResponse
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

    fun updateKakaoUserInfo(nickName: String, profileImageUrl: String) {
        _uiState.update { prev ->
            prev.copy(
                nickName = nickName,
                profileImageUrl = profileImageUrl
            )
        }
    }

    fun getMyProfile(isFailed: () -> Unit) = viewModelScope.launch {
        try {
            memberRepository.getMyProfile().result?.let {
                _uiState.update { prev ->
                    prev.copy(
                        name = it.name,
                        nickName = it.nickname,
                        alarmState = MyUiState.AlarmState(
                            isSet = it.alarmStatus,
                            hour = it.alarmTime.hour,
                            min = it.alarmTime.min
                        )
                    )
                }
            }
        } catch (e: Exception) {
            Toast.makeText(UmcClothsOfTempApplication.context, e.toString(), Toast.LENGTH_SHORT).show()
            e.printStackTrace()
            isFailed()
        }
    }

    fun getMyAlarmState():MyUiState.AlarmState = uiState.value.alarmState

    fun sendKakaoAccessToken(accessToken: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = memberRepository.sendKakaoAccessToken(accessToken)
                Log.d("MyViewModel", "Response: $response") // 서버 응답 로그 추가
                if (response.success) {
                    Log.i("MyViewModel", "서버 인증 성공: ${response.message}")
                    onSuccess()
                } else {
                    Log.e("MyViewModel", "서버 인증 실패: ${response.message}")
                    onFailure("서버 인증 실패: ${response.message}")
                }
            } catch (e: Exception) {
                Log.e("MyViewModel", "서버 통신 오류: ${e.message}")
                onFailure("서버 통신 오류: ${e.message}")
            }
        }
    }
}