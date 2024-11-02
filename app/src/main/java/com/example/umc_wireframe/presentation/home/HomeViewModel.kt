package com.example.umc_wireframe.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umc_wireframe.domain.repository.MidTermForecastRepository
import com.example.umc_wireframe.domain.repository.RepositoryFactory
import com.example.umc_wireframe.domain.repository.ShortTermForecastRepository
import com.example.umc_wireframe.presentation.MainItem
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class HomeViewModel : ViewModel() {
    private val shortTermForecastRepository: ShortTermForecastRepository =
        RepositoryFactory().createShortTermForecastRepository()

    private val midTermForecastDatasource: MidTermForecastRepository =
        RepositoryFactory().createMidTermForecastRepository()

    fun getShortTermForecast(x: Int, y: Int) = viewModelScope.launch {
        val now = LocalDateTime.now()
        val baseDate = now.toLocalDate().toString().replace("-", "") //YYYYMMDD
        val baseTime = when (now.hour) {
            in 0..2 -> "2300"
            in 3..5 -> "0200"
            in 6..8 -> "0500"
            in 9..11 -> "0800"
            in 12..14 -> "1100"
            in 15..17 -> "1400"
            in 18..20 -> "1700"
            in 21..23 -> "2000"
            else -> "1700" // 기본값으로 설정
        } //HHMM - 0200, 0500, 0800, 1100, 1400, 1700, 2000, 2300 만 가능


        shortTermForecastRepository.getWeatherForecast(
            pageNo = 3,
            baseDate = baseDate,
            baseTime = baseTime,
            nx = x,
            ny = y
        ).let { entity ->
            entity?.body?.items?.map {
                MainItem(
                    fcstDate = it.date,
                    fcstTime = it.time,
                    category = it.category.toString(),
                    fcstValue = it.value.toString()
                )
            }?.let { list ->

            }
        }
    }
}