package com.cornerweather.android.logic
import android.util.Log
import androidx.lifecycle.liveData
import com.cornerweather.android.logic.model.Weather
import com.cornerweather.android.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlin.coroutines.CoroutineContext

object Repository {
   fun searchPlaces(query: String) = fire(Dispatchers.IO) {
       val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
       if (placeResponse.status == "ok") {
           val places = placeResponse.places
           Log.d("Repository", "searchPlaces: $places")
           Result.success(places)
       } else {
           Log.d("Repository", "failure: ${placeResponse.status}")
           Result.failure(RuntimeException("response status is ${placeResponse.status}"))
       }
    }
    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
        val realtimeResponse = SunnyWeatherNetwork.getRealtimeWeather(lng, lat)
        Log.d("Repository", "===== realtime status=${realtimeResponse.status} =====")
        logLong("Repository", "realtime: $realtimeResponse")

        delay(1100)                                            // 避开 1 QPS 限制

        val dailyResponse = SunnyWeatherNetwork.getDailyWeather(lng, lat)
        Log.d("Repository", "===== daily status=${dailyResponse.status} =====")
        logLong("Repository", "daily: $dailyResponse")

        if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
            val weather = Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
            logLong("Repository", "weather: $weather")
            Result.success(weather)
        } else {
            Result.failure(RuntimeException(
                "realtime=${realtimeResponse.status}, daily=${dailyResponse.status}"
            ))
        }
    }

    /**
     * Logcat 单条日志有长度上限（约 4KB），超长内容会被截断。
     * 这里按 3000 字符切段输出，保证完整数据都能看到。
     */
    private fun logLong(tag: String, message: String) {
        val maxLength = 3000
        if (message.length <= maxLength) {
            Log.d(tag, message)
            return
        }
        var start = 0
        while (start < message.length) {
            val end = minOf(start + maxLength, message.length)
            Log.d(tag, message.substring(start, end))
            start = end
        }
    }

    private fun <T>fire(context: CoroutineContext, block: suspend () -> Result<T>) = liveData<Result<T>>(context) {
        val result = try {
            block()
        } catch (e: Exception) {
            Log.e("Repository", "request failed", e)
            Result.failure<T>(e)
        }
        emit(result)
    }
}