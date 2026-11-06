package com.cornerweather.android.ui.place

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cornerweather.android.R
import com.cornerweather.android.logic.model.Place

class PlaceAdapter(private val placeList: List<Place>): RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {

    inner  class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val placeName:TextView = view.findViewById(R.id.placeName)
        val placeAddress:TextView = view.findViewById(R.id.placeAddress)
        val location:TextView = view.findViewById(R.id.location)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item,parent,false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
       val  place = placeList[position]
       holder.placeName.text = place.name
       holder.placeAddress.text = place.address
       holder.location.text = "Lat: ${place.location.lat}, Lng: ${place.location.lng}"
    }

    override fun getItemCount() = placeList.size



}