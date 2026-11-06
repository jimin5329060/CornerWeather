package com.cornerweather.android.ui.weather
import android.os.Bundle
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.cornerweather.android.R
class WeatherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            R.layout.activity_weather
        )
       val placeName:TextView = findViewById(R.id.placeName)
        placeName.text = intent.getStringExtra("place_name")
        WindowCompat.getInsetsController(window, window.decorView)
            .isAppearanceLightStatusBars = false

    }
}

