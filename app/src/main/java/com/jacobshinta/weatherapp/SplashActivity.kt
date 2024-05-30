package com.jacobshinta.weatherapp

import android.animation.ObjectAnimator
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.util.Log

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val sharedPreferences: SharedPreferences = getSharedPreferences("WeatherApp", MODE_PRIVATE)
        val hasUsedApp = sharedPreferences.getBoolean("HasUsedApp", false)

        navigateActivity(hasUsedApp)
    }

    private fun navigateActivity(hasUsedApp: Boolean) {
        Handler(Looper.getMainLooper()).postDelayed({
            val nextActivity = if (hasUsedApp) MainActivity::class.java else LocationPromptActivity::class.java
            startActivity(Intent(this, nextActivity))
            finish()
        }, 2000)
    }
}
