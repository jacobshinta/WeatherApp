package com.jacobshinta.weatherapp.Interface

import com.google.firebase.database.core.Repo

import com.jacobshinta.weatherapp.model.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface WeatherInterface {
    @GET("forecast.json")
    fun getWeatherForecast(@Query("q") query: String, @Query("days") days: Int, @Query("key") apiKey: String): Call<WeatherResponse>
}