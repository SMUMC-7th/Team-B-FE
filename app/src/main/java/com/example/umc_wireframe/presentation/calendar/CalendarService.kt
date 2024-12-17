package com.example.umc_wireframe.presentation.calendar

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CalendarService {
    @GET("api/ootds")
    fun getOOTD(
        @Query("year") year: Int,
        @Query("month") month: Int
    ): Call<OOTDResponse>
}