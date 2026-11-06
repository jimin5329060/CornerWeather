package com.cornerweather.android
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import androidx.core.view.WindowCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            R.layout.activity_main
        )
        WindowCompat.getInsetsController(window, window.decorView)
            .isAppearanceLightStatusBars = false

    }
}

