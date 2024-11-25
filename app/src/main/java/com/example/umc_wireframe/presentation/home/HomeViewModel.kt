package com.example.umc_wireframe.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umc_wireframe.domain.model.MidTermRegion
import com.example.umc_wireframe.domain.model.ShortTermCategory
import com.example.umc_wireframe.domain.model.ShortTermRegionObject
import com.example.umc_wireframe.domain.repository.MemberRepository
import com.example.umc_wireframe.domain.repository.MidTermForecastRepository
import com.example.umc_wireframe.domain.repository.OotdRepository
import com.example.umc_wireframe.domain.repository.RepositoryFactory
import com.example.umc_wireframe.domain.repository.ShortTermForecastRepository
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

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Login("j", "j"))
    val loginState = _loginState.asStateFlow()

    private val shortTermForecastRepository: ShortTermForecastRepository =
        RepositoryFactory.createShortTermForecastRepository()

    private val ootdRepository: OotdRepository =
        RepositoryFactory.createOotdRepository()

    private val memberRepository: MemberRepository =
        RepositoryFactory.createMemberRepository()

    fun getDailyShortTermForecast(selectLocation: ShortTermRegionObject) = viewModelScope.launch {
        val now = LocalDate.now().minusDays(1)
        val baseDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"))

        val entity = shortTermForecastRepository.getWeatherForecast(
            pageNo = 1,
            baseDate = baseDate,
            baseTime = "2300", //"0200", "0500", "0800", "1100", "1400", "1700", "2000", "2300")
            nx = selectLocation.x,
            ny = selectLocation.y,
            numOfRows = 288
        )?.body?.items

        entity?.let { items ->
            val tempList = items.filter { it.category == ShortTermCategory.TMP }
                .map { "${it.fcstDate} ${it.fcstTime}" to it.value }
                .sortedByDescending { it.first }

            val maxTemp = tempList.maxBy { it.second }
            val minTime = tempList.minBy { it.second }

//            val pastOotd = ootdRepository.getOotdPastForTemp(
//                authorization = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWQiOjIsImlhdCI6MTczMTkyMDkzMywiZXhwIjoxNzMxOTI0NTMzfQ.W88HJXTFuaquN3eEuB-GeSnCQGFObl6ctdmU_BCsEFM",
//                maxTemperature = maxTemp.second.toInt(),
//                minTemperature = minTime.second.toInt()
//            )
//            val recommendedClothes = ootdRepository.getRecommendedHashtag(
//                authorization = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWQiOjIsImlhdCI6MTczMTkyMDkzMywiZXhwIjoxNzMxOTI0NTMzfQ.W88HJXTFuaquN3eEuB-GeSnCQGFObl6ctdmU_BCsEFM",
//                maxTemperature = maxTemp.second.toInt(),
//                minTemperature = minTime.second.toInt()
//            )

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
                    authorization = (loginState.value as LoginState.Login).jwtToken,
                    maxTemperature = uiState.value.maxTemp.second.toInt(),
                    minTemperature = uiState.value.minTemp.second.toInt()
                )
                val recommendedClothes = ootdRepository.getRecommendedHashtag(
                    authorization = (loginState.value as LoginState.Login).jwtToken,
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
                if (e is HttpException && e.code() == 401)
                    (loginState.value as? LoginState.Login)?.let {
                        _loginState.update { prev ->
                            LoginState.RefreshRequire(
                                jwtToken = it.jwtToken,
                                refreshToken = it.refreshToken
                            )
                        }
                    } else LoginState.LoginRequire
            }
        }
    }

    fun refreshToken() = viewModelScope.launch {
        try {
            (loginState.value as? LoginState.RefreshRequire)?.let {
                memberRepository.postRefreshToken(
                    authorization = it.jwtToken,
                    refreshToken = it.refreshToken
                )
            }
        }catch (e:Exception){
            e.printStackTrace()
            _loginState.update { prev->
                LoginState.LoginRequire
            }
        }
    }

}