package com.example.umc_wireframe.presentation.calendar

import retrofit2.Call

class RecordRepository(private val recordService: CalendarService) {

    fun getMyRecord(year: Int, month: Int): Call<OOTDResponse> {
        return recordService.getOOTD(year, month)
    }
}