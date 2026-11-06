package com.cornerweather.android.logic
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.cornerweather.android.SunnyWeatherApplication
import com.cornerweather.android.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers

object Repository {
    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                Log.d("Repository", "searchPlaces: $places")
                Result.success(places)
            } else {
                Log.d("Repository", "failure: ${placeResponse.status}")
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        } catch (e: Exception) {
            Log.d("Repository", "e: $e")
            Result.failure<List<com.cornerweather.android.logic.model.Place>>(e)
        }
        emit(result)
    }
    
}