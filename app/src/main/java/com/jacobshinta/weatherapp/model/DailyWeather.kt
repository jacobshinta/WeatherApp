package com.jacobshinta.weatherapp.model

import android.media.Image

data class DailyWeather(
    val day: String,
    val highTemp: Int,
    val lowTemp: Int,
    val weatherChance: Int,
    val weatherType: Int
)