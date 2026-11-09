package com.cornerweather.android.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.cornerweather.android.SunnyWeatherApplication
import com.cornerweather.android.logic.model.Place
import com.google.gson.Gson

object PlaceDao {

    private const val SP_NAME = "corner_weather"
    private const val KEY_PLACE = "place"

    fun savePlace(place: Place) {
        sharedPreferences().edit {
            putString(KEY_PLACE, Gson().toJson(place))
        }
    }

    fun getSavedPlace(): Place? {
        val json = sharedPreferences().getString(KEY_PLACE, "")
        if (json.isNullOrEmpty()) return null
        return runCatching {
            Gson().fromJson(json, Place::class.java)
        }.getOrNull()
    }

    fun isPlaceSaved() = sharedPreferences().contains(KEY_PLACE)

    private fun sharedPreferences() = SunnyWeatherApplication.context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
}
