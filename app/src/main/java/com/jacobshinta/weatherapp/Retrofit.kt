package com.jacobshinta.weatherapp

import com.jacobshinta.weatherapp.Interface.WeatherInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

var retrofit = Retrofit.Builder()
    .baseUrl("https://api.weatherapi.com/v1/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

var service: WeatherInterface = retrofit.create(WeatherInterface::class.java)




