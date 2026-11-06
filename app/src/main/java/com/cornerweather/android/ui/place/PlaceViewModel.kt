package com.cornerweather.android.ui.place

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.cornerweather.android.logic.Repository
import com.cornerweather.android.logic.model.Place

class PlaceViewModel: ViewModel() {
    private val searchLiveData = androidx.lifecycle.MutableLiveData<String>()
    val placeList = ArrayList<Place>()
    val placeLiveData: LiveData<Result<List<Place>>> = searchLiveData.switchMap { query ->
        Repository.searchPlaces(query)
    }
    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }
}