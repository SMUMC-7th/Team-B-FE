package com.example.umc_wireframe.presentation.home

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umc_wireframe.domain.model.ShortTermCategory
import com.example.umc_wireframe.domain.model.ShortTermRegionObject
import com.example.umc_wireframe.domain.repository.MemberRepository
import com.example.umc_wireframe.domain.repository.OotdRepository
import com.example.umc_wireframe.domain.repository.RepositoryFactory
import com.example.umc_wireframe.domain.repository.ShortTermForecastRepository
import com.example.umc_wireframe.presentation.UmcClothsOfTempApplication
import com.example.umc_wireframe.util.SharedPreferencesManager
import com.example.umc_wireframe.util.cancelAlarmWorker
import com.example.umc_wireframe.util.scheduleDailyAlarmWorker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.init())
    val uiState = _uiState.asStateFlow()

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Init)
    val loginState = _loginState.asStateFlow()

    private val shortTermForecastRepository: ShortTermForecastRepository =
        RepositoryFactory.createShortTermForecastRepository()

    private val ootdRepository: OotdRepository =
        RepositoryFactory.createOotdRepository()

    private val memberRepository: MemberRepository =
        RepositoryFactory.createMemberRepository()


    fun logout() {
        _loginState.value = LoginState.LoginRequire
        SharedPreferencesManager(UmcClothsOfTempApplication.context).clearAll()
    }

    fun withdraw() = viewModelScope.launch {
        try {
            memberRepository.postUserWithdraw()
            SharedPreferencesManager(UmcClothsOfTempApplication.context).clearAll()
            _loginState.value = LoginState.LoginRequire
        } catch (e: Exception) {
            Toast.makeText(UmcClothsOfTempApplication.context, e.toString(), Toast.LENGTH_SHORT)
                .show()
            failedToken()
        }
    }

    fun getDailyShortTermForecast(selectLocation: ShortTermRegionObject) = viewModelScope.launch {
        val now = LocalDate.now().minusDays(1)
        val baseDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"))

        val entity = shortTermForecastRepository.getMaxMinTemp(
            pageNo = 1,
            baseDate = baseDate,
            baseTime = "2300", //"0200", "0500", "0800", "1100", "1400", "1700", "2000", "2300")
            nx = selectLocation.x,
            ny = selectLocation.y,
            numOfRows = 288
        )?.body?.items

        entity?.let { items ->
            val tempList = items
                .map { "${it.fcstDate} ${it.fcstTime}" to it.value }

            val maxTemp = tempList.maxBy { it.second }
            val minTime = tempList.minBy { it.second }

            _uiState.update { prev ->
                prev.copy(
                    selectLocation = selectLocation,
                    maxTemp = maxTemp,
                    minTemp = minTime,
                    pop = items.filter { it.category == ShortTermCategory.POP }
                        .map { "${it.fcstDate} ${it.fcstTime}" to it.value }
                        .sortedByDescending { it.first },
                    pcp = items.filter { it.category == ShortTermCategory.PCP }
                        .map { "${it.fcstDate} ${it.fcstTime}" to it.value }
                        .sortedByDescending { it.first }
                )
            }

            getOotd()
        }

    }


    fun getOotd() = viewModelScope.launch {
        if (loginState.value is LoginState.Login) {
            try {
                val pastOotd = ootdRepository.getOotdPastForTemp(
                    maxTemperature = uiState.value.maxTemp.second.toInt(),
                    minTemperature = uiState.value.minTemp.second.toInt()
                )
                val recommendedClothes = ootdRepository.getRecommendedHashtag(
                    maxTemperature = uiState.value.maxTemp.second.toInt(),
                    minTemperature = uiState.value.minTemp.second.toInt()
                )

                _uiState.update { prev ->
                    prev.copy(
                        recommendedClothes = recommendedClothes.result?.recommendations
                            ?: emptyList(),
                        historyList = pastOotd.result?.ootds ?: emptyList()
                    )
                }
            } catch (e: Exception) {
                Toast.makeText(UmcClothsOfTempApplication.context, e.toString(), Toast.LENGTH_SHORT)
                    .show()
                e.printStackTrace()
                failedToken()
            }
        }
    }

    fun login(accessToken: String, refreshToken: String) {
        _loginState.update {
            LoginState.Login(
                accessToken = accessToken,
                refreshToken = refreshToken
            )
        }
    }

    fun failedToken() {
        _loginState.update {
            LoginState.LoginRequire
        }
    }

    fun setAlarm() = viewModelScope.launch {
        try {
            memberRepository.getMyProfile().result?.let {
                if (it.alarmStatus) {
                    it.alarmTime.let {
                        scheduleDailyAlarmWorker(
                            UmcClothsOfTempApplication.context,
                            LocalDateTime.now().withHour(it.hour).withMonth(it.min)
                        )
                    }
                } else cancelAlarmWorker(UmcClothsOfTempApplication.context)
            }
        } catch (e: Exception) {
            Toast.makeText(UmcClothsOfTempApplication.context, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}