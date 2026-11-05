package com.cornerweather.android
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            R.layout.activity_main
        )
        WindowCompat.getInsetsController(window, window.decorView)
            .isAppearanceLightStatusBars = false
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.root)) { v, insets ->
//            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
//            insets
//        }
    }
}

