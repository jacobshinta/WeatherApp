package com.jacobshinta.weatherapp

import android.app.ActivityOptions
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Pair
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var logo: View
    private lateinit var appName: View
    private lateinit var sharedPreferences: SharedPreferences
    private var hasUsedApp: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        initializeViews()
        checkFirstTimeUser()

        if (hasUsedApp) {
            navigateToMainActivity()
        } else {
            delayAndStartAnimations()
        }
    }

    private fun initializeViews() {
        logo = findViewById(R.id.splash_logo)
        appName = findViewById(R.id.splash_name)
    }

    private fun checkFirstTimeUser() {
        sharedPreferences = getSharedPreferences("WeatherApp", MODE_PRIVATE)
        hasUsedApp = sharedPreferences.getBoolean("HasUsedApp", false)
    }

    private fun delayAndStartAnimations() {
        Handler(Looper.getMainLooper()).postDelayed({
            startAnimations()
        }, 2000)
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun startAnimations() {
        val translateUp = AnimationUtils.loadAnimation(this, R.anim.translate_up)

        translateUp.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                navigateToLocationPromptActivity()
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })

        logo.startAnimation(translateUp)
        appName.startAnimation(translateUp)
    }

    private fun navigateToLocationPromptActivity() {
        val intent = Intent(this, LocationPromptActivity::class.java)
        val options = ActivityOptions.makeSceneTransitionAnimation(
            this,
            Pair.create(logo, "logoTransition"),
            Pair.create(appName, "appNameTransition")
        )
        startActivity(intent, options.toBundle())
    }
}
