package com.cornerweather.android.ui.place

import android.annotation.SuppressLint
import android.content.Intent
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.core.widget.addTextChangedListener
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cornerweather.android.MainActivity
import com.cornerweather.android.R
import com.cornerweather.android.ui.weather.WeatherActivity


class PlaceFragment: Fragment() {
    private lateinit var placeAdapter: PlaceAdapter
    val viewModel by lazy { ViewModelProvider(this)[PlaceViewModel::class.java] }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_place, container, false)
    }

    @SuppressLint("NotifyDataSetChanged", "ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val actionBarLayout = view.findViewById<FrameLayout>(R.id.actionBarLayout)
        ViewCompat.setOnApplyWindowInsetsListener(actionBarLayout) { v, insets ->
            val top = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            v.updatePadding(top = top)
            insets
        }
        val bgImageView = view.findViewById<ImageView>(R.id.bgImageView)
        ViewCompat.setOnApplyWindowInsetsListener(bgImageView) { v, insets ->
            val bottom = insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom
            v.updatePadding(bottom = bottom)
            insets
        }


        if (activity is MainActivity && viewModel.isPlaceSaved()){
            val  place = viewModel.getSavedPlace()
            val  intent = Intent(requireContext(), com.cornerweather.android.ui.weather.WeatherActivity::class.java).apply {
                putExtra("location_lng", place!!.location.lng)
                putExtra("location_lat", place.location.lat)
                putExtra("place_name", place.name)
            }
            startActivity(intent)
            activity?.finish()
        }


        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        placeAdapter = PlaceAdapter(viewModel.placeList){
                place -> Log.d("PlaceFragment", "Clicked place: ${place.name}, ${place.address}, Lat: ${place.location.lat}, Lng: ${place.location.lng}")
            // 同一个 PlaceFragment 被两处复用：
            // 1) MainActivity 首页 -> 新开一个天气页
            // 2) WeatherActivity 抽屉里 -> 关抽屉 + 让宿主就地换地点刷新，别重复 startActivity
            val weatherActivity = activity as? WeatherActivity
            if (weatherActivity != null) {
                Log.d("PlaceFragment", "WeatherActivity detected, updating location and refreshing weather.")
                // DrawerLayout 在 Activity 的布局里，Fragment.findViewById 拿到的是 null
                weatherActivity.findViewById<DrawerLayout>(R.id.drawerLayout)
                    ?.closeDrawer(GravityCompat.START)
                weatherActivity.updateWeather(place.location.lng, place.location.lat, place.name)
            } else {
                val  intent = Intent(requireContext(), WeatherActivity::class.java).apply {
                    putExtra("location_lng", place.location.lng)
                    putExtra("location_lat", place.location.lat)
                    putExtra("place_name", place.name)
                }
                startActivity(intent)
                activity?.finish()
            }
            viewModel.savePlace(place)
            hideKeyboard()
        }
        recyclerView.adapter = placeAdapter
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    hideKeyboard()
                }
            }
        })
        recyclerView.setOnTouchListener { _, event ->
            if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                hideKeyboard()
            }
            false
        }
        val searchPlaceEdit:EditText = view.findViewById(R.id.searchPlaceEdit)
        searchPlaceEdit.addTextChangedListener{
                editable -> val content = editable.toString()
            Log.d("PlaceFragment", "Search content: $content")
            if (content.isNotEmpty()) {
                viewModel.searchPlaces(content)
            } else {
                recyclerView.visibility = View.VISIBLE
                bgImageView.visibility = View.GONE
                viewModel.placeList.clear()
                placeAdapter.notifyDataSetChanged()
            }
        }
        viewModel.placeLiveData.observe(viewLifecycleOwner) { result ->
            val places = result.getOrNull()
            if (places != null) {
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                placeAdapter.notifyDataSetChanged()
                recyclerView.visibility = View.VISIBLE
                bgImageView.visibility = View.GONE
            } else {
                Toast.makeText(requireContext(), "未能查询到任何地点", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
                recyclerView.visibility = View.GONE
                bgImageView.visibility = View.VISIBLE
            }
        }


    }
    private fun hideKeyboard() {
        ViewCompat.getWindowInsetsController(requireActivity().window.decorView)
            ?.hide(WindowInsetsCompat.Type.ime())
    }
}