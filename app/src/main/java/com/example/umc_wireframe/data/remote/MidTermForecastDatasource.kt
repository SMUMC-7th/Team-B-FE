package com.example.umc_wireframe.data.remote

import com.example.umc_wireframe.data.model.MidTermForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MidTermForecastDatasource {
    @GET("getMidFcst")
    suspend fun getWeatherForecast(
        @Query("pageNo") pageNo: Int,
        @Query("numOfRows") numOfRows: Int,
        @Query("dataType") dataType: String,
        @Query("stnId") stnId: String,
        @Query("tmFc") tmFc: String
    ): MidTermForecastResponse
}