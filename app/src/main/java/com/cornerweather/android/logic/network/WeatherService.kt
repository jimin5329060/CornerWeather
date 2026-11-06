package com.cornerweather.android.logic.network

import retrofit2.http.GET

interface WeatherService {
    @GET("v2.5/${com.cornerweather.android.SunnyWeatherApplication.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(@retrofit2.http.Path("lng") lng: String, @retrofit2.http.Path("lat") lat: String): retrofit2.Call<com.cornerweather.android.logic.model.RealtimeResponse>
    @GET("v2.5/${com.cornerweather.android.SunnyWeatherApplication.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@retrofit2.http.Path("lng") lng: String, @retrofit2.http.Path("lat") lat: String): retrofit2.Call<com.cornerweather.android.logic.model.DailyResponse>
}