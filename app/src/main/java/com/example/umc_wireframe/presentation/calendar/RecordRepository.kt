package com.example.umc_wireframe.presentation.calendar

import retrofit2.Call
import timber.log.Timber

class RecordRepository(private val recordService: CalendarService) {

    fun getMyRecord(year: Int, month: Int): Call<OOTDResponse> {
        Timber.d("Getting my record for year: $year, month: $month")
        return recordService.getOOTD(year, month)
    }
}