package com.jacobshinta.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacobshinta.weatherapp.Interface.WeatherInterface
import com.jacobshinta.weatherapp.model.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherViewModel : ViewModel() {

    private val _weatherData = MutableLiveData<WeatherResponse>()
    val weatherData: LiveData<WeatherResponse> = _weatherData

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.weatherapi.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service: WeatherInterface = retrofit.create(WeatherInterface::class.java)

    fun fetchWeatherData(query: String, apiKey: String, days: Int = 10) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    service.getWeatherForecast(query, days, apiKey).execute()
                }
                if (response.isSuccessful) {
                    _weatherData.postValue(response.body())
                } else {
                }
            } catch (e: Exception) {
            }
        }
    }
}
