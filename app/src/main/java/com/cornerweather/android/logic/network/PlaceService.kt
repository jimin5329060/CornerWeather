package com.cornerweather.android.logic.network

import com.cornerweather.android.SunnyWeatherApplication
import retrofit2.http.GET

interface PlaceService {
    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@retrofit2.http.Query("query") query: String): retrofit2.Call<com.cornerweather.android.logic.model.PlaceResponse>

}