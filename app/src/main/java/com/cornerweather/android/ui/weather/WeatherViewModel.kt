package com.cornerweather.android.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.cornerweather.android.logic.Repository
import com.cornerweather.android.logic.model.Location
import com.cornerweather.android.logic.model.Place

class WeatherViewModel: ViewModel() {
   private val locationLiveData = androidx.lifecycle.MutableLiveData<Location>()
   var locationLng = ""
   var locationLat = ""
   var placeName = ""
   val weatherLiveData: LiveData<Result<com.cornerweather.android.logic.model.Weather>> = locationLiveData.switchMap { location ->
      Repository.refreshWeather(location.lng,location.lat)
   }
   fun refreshWeather(lng: String, lat: String) {
       locationLiveData.value = Location(lng,lat)
   }
}