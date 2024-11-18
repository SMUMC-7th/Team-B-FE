package com.example.umc_wireframe.data.remote

import android.graphics.Bitmap
import com.example.umc_wireframe.data.model.Hashtag
import com.example.umc_wireframe.data.model.OotdResultResponse
import com.example.umc_wireframe.data.model.RecommendedHashtagResultResponse
import com.example.umc_wireframe.data.model.OOtdResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ServerDatasource {

    //ootd
    @GET("api/recommendation")
    suspend fun getRecommendedHashtag(
        @Header("Authorization") authorization: String,
        @Query("maxTemperature") maxTemperature: Int,
        @Query("minTemperature") minTemperature: Int
    ): OOtdResponse<RecommendedHashtagResultResponse>


    @GET("api/ootds/past")
    suspend fun getOotdPastForTemp(
        @Header("Authorization") authorization: String,
        @Query("maxTemperature") maxTemperature: Int,
        @Query("minTemperature") minTemperature: Int
    ): OOtdResponse<OotdResultResponse>

    @POST("api/ootds")
    suspend fun postOotd(
        @Header("Authorization") authorization: String,
        @Query("image") image: Bitmap,
        @Query("maxTemperature") maxTemperature: Int,
        @Query("minTemperature") minTemperature: Int,
        @Query("hashtags") hashtags: List<Hashtag> // 최대 3개
    ): OOtdResponse<OotdResultResponse>

    @GET("api/ootds")
    suspend fun getOotdPastForYearMonth(
        @Header("Authorization") authorization: String,
        @Query("year") year: Int,
        @Query("month") month: Int
    ): OOtdResponse<OotdResultResponse>
}