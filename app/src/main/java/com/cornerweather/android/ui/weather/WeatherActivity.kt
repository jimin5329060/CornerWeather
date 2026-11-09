package com.cornerweather.android.ui.weather
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cornerweather.android.R
import com.cornerweather.android.logic.model.Weather
import com.cornerweather.android.logic.model.getSky

class WeatherActivity : AppCompatActivity() {

    val viewModel by lazy { ViewModelProvider(this)[WeatherViewModel::class.java] }

    @SuppressLint("SetTextI18n", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val decorView = window.decorView
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = android.graphics.Color.TRANSPARENT
        setContentView(
            R.layout.activity_weather
        )
        if (viewModel.locationLng.isEmpty()){
            viewModel.locationLng = intent.getStringExtra("location_lng") ?: ""
        }
        if (viewModel.locationLat.isEmpty()){
            viewModel.locationLat = intent.getStringExtra("location_lat") ?: ""
        }
        if(viewModel.placeName.isEmpty()){
           viewModel.placeName = intent.getStringExtra("place_name") ?: ""
        }

        Log.d("WeatherActivity", "Weather data: ${viewModel.locationLng}, ${viewModel.locationLat}, ${viewModel.placeName}")

        viewModel.weatherLiveData.observe(this, Observer{
            result ->
            val  weather = result.getOrNull()
            Log.d("WeatherActivity", "Weather data: $weather")
            if (weather != null){
                showWeatherInfo(weather)
            }else{
                Toast.makeText(this,"无法成功获取天气信息",Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }

        })

        viewModel.refreshWeather(viewModel.locationLng,viewModel.locationLat)

        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = false

    }

    @SuppressLint("SetTextI18n")
    private fun showWeatherInfo(weather:Weather){
        val placeName:TextView = findViewById(R.id.placeName)
        val realtime = weather.realtime
        val daily = weather.daily
        placeName.text = viewModel.placeName
        val currentTemp:TextView = findViewById(R.id.currentTemp)
        currentTemp.text = realtime.temperature.toInt().toString() + "°C"
        val  currentSky:TextView = findViewById(R.id.currentSky)
        currentSky.text = getSky(realtime.skycon).info
        val currentAQI:TextView = findViewById(R.id.currentAQI)
        currentAQI.text = "空气指数 ${realtime.airQuality.aqi.chn.toInt()}"

        val nowLayout:RelativeLayout = findViewById(R.id.nowLayout)
        nowLayout.setBackgroundResource(getSky(realtime.skycon).bg)

        val forecastLayout:LinearLayout = findViewById(R.id.forecastLayout)
        forecastLayout.removeAllViews()
        val days = daily.skycon.size
        for (i in 0 until days){
            val skycon = daily.skycon[i]
            val temperature = daily.temperature[i]
            val view = layoutInflater.inflate(R.layout.forecast_item,forecastLayout,false)
            val dateInfo:TextView = view.findViewById(R.id.dateInfo)
            val skyIcon:ImageView = view.findViewById(R.id.skyIcon)
            val skyInfo:TextView = view.findViewById(R.id.skyInfo)
            val temperatureInfo:TextView = view.findViewById(R.id.temperatureInfo)
            val simpleDateFormat = java.text.SimpleDateFormat("yyyy-MM-dd",java.util.Locale.getDefault())
            dateInfo.text = simpleDateFormat.format(skycon.date)
            val sky = getSky(skycon.value)
            skyIcon.setImageResource(sky.icon)
            skyInfo.text = sky.info
            temperatureInfo.text = "${temperature.min.toInt()}°C ~ ${temperature.max.toInt()}°C"
            forecastLayout.addView(view)
        }
       val lifeIndex = daily.lifeIndex
       val coldRiskText:TextView = findViewById(R.id.coldRiskText)
       coldRiskText.text = lifeIndex.coldRisk[0].desc
       val dressingText:TextView = findViewById(R.id.dressingText)
       dressingText.text =  lifeIndex.dressing[0].desc
       val ultravioletText:TextView = findViewById(R.id.ultravioletText)
       ultravioletText.text =  lifeIndex.ultraviolet[0].desc
       val carWashingText:TextView = findViewById(R.id.carWashingText)
       carWashingText.text = lifeIndex.carWashing[0].desc
       val weatherLayout:ScrollView =  findViewById(R.id.weatherLayout)
       weatherLayout.visibility = View.VISIBLE
    }
}

