package com.example.umc_wireframe.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umc_wireframe.domain.model.MidTermRegion
import com.example.umc_wireframe.domain.model.ShortTermCategory
import com.example.umc_wireframe.domain.model.ShortTermRegionObject
import com.example.umc_wireframe.domain.repository.MidTermForecastRepository
import com.example.umc_wireframe.domain.repository.RepositoryFactory
import com.example.umc_wireframe.domain.repository.ShortTermForecastRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.init())
    val uiState = _uiState.asStateFlow()

    private val shortTermForecastRepository: ShortTermForecastRepository =
        RepositoryFactory().createShortTermForecastRepository()

    private val midTermForecastDatasource: MidTermForecastRepository =
        RepositoryFactory().createMidTermForecastRepository()


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
            _uiState.update { prev ->
                prev.copy(
                    selectLocation = selectLocation,
                    temp = items.filter { it.category == ShortTermCategory.TMP }
                        .map { "${it.fcstDate} ${it.fcstTime}" to it.value }
                        .sortedByDescending { it.first },
                    pop = items.filter { it.category == ShortTermCategory.POP }
                        .map { "${it.fcstDate} ${it.fcstTime}" to it.value }
                        .sortedByDescending { it.first },
                    pcp = items.filter { it.category == ShortTermCategory.PCP }
                        .map { "${it.fcstDate} ${it.fcstTime}" to it.value }
                        .sortedByDescending { it.first }
                )
            }
        }

    }

    fun getMidTermForecast(midTermRegion: MidTermRegion) = viewModelScope.launch {
        val tmFc = when {
            LocalDateTime.now().hour < 6 -> LocalDateTime.now().minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "1800"

            LocalDateTime.now().hour < 18 -> LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "0600"

            else -> LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "1800"
        }

        midTermForecastDatasource.getWeatherForecast(
            tmFc = tmFc,
            regId = midTermRegion.regId
        ).let { entity ->
            entity?.response?.body?.items?.map {

            }

        }
    }
}