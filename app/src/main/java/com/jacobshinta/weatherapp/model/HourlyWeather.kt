package com.jacobshinta.weatherapp.model

import android.media.Image

data class HourlyWeather(
    val time: String,
    val temp: Int,
    val weatherType: Int
)