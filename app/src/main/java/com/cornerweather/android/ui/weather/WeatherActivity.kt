package com.cornerweather.android.ui.weather
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.cornerweather.android.R
class WeatherActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            R.layout.activity_weather
        )
        val placeName:TextView = findViewById(R.id.placeName)
        placeName.text = intent.getStringExtra("place_name")
        val currentTemp:TextView = findViewById(R.id.currentTemp)
        currentTemp.text = "29°C"
        val  currentSky:TextView = findViewById(R.id.currentSky)
        currentSky.text = "晴"
        val currentAQI:TextView = findViewById(R.id.currentAQI)
        currentAQI.text = "空气指数 100"
        WindowCompat.getInsetsController(window, window.decorView)
            .isAppearanceLightStatusBars = false

    }
}

