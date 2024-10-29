package com.example.umc_wireframe.data.remote

import com.example.umc_wireframe.data.model.MidTermForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MidTermForecastDatasource {
    @GET("getMidTa")
    suspend fun getWeatherForecast(
        @Query("pageNo") pageNo: Int,
        @Query("numOfRows") numOfRows: Int,
        @Query("dataType") dataType: String,
        @Query("regId") regId: String,
        @Query("tmFc") tmFc: String
    ): MidTermForecastResponse
}