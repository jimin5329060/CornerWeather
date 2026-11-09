package com.cornerweather.android.logic.network
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

object SunnyWeatherNetwork {
    private val weatherService = ServiceCreator.create<WeatherService>()
    suspend fun getRealtimeWeather(lng: String, lat: String) = weatherService.getRealtimeWeather(lng, lat).await()
    suspend fun getDailyWeather(lng: String, lat: String) = weatherService.getDailyWeather(lng, lat).await()
    private val placeService = ServiceCreator.create<PlaceService>()
    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()
    private suspend fun <T> retrofit2.Call<T>.await(): T {
        return suspendCancellableCoroutine { continuation ->
            enqueue(object : retrofit2.Callback<T> {
                override fun onResponse(call: retrofit2.Call<T>, response: retrofit2.Response<T>) {
                    if (!response.isSuccessful) {
                        val error = response.errorBody()?.string()
                        continuation.resumeWithException(
                            RuntimeException("HTTP ${response.code()} ${response.message()} body=$error")
                        )
                        return
                    }
                    val body = response.body()
                    if (body != null) {
                        continuation.resume(body) {}
                    } else {
                        continuation.resumeWithException(RuntimeException("response body is null"))
                    }
                }

                override fun onFailure(call: retrofit2.Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }

}