package com.jacobshinta.weatherapp

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jacobshinta.weatherapp.model.WeatherResponse
import com.jacobshinta.weatherapp.viewmodel.WeatherViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var hourlyAdapter: HourlyWeatherAdapter
    private lateinit var dailyAdapter: DailyWeatherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val hourlyRecyclerView = findViewById<RecyclerView>(R.id.rv_hourly)
        val dailyRecyclerView = findViewById<RecyclerView>(R.id.rv_daily)
        val locationEditText = findViewById<EditText>(R.id.et_location)

        hourlyRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        dailyRecyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.weatherData.observe(this, Observer { weatherResponse ->
            weatherResponse?.let {
                setupRecyclerViews(it)
                displayWeatherData(it)
            }
        })

        locationEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val location = locationEditText.text.toString()
                if (location.isNotBlank()) {
                    viewModel.fetchWeatherData(query = location, apiKey = "b82bc4ef331546e7906224746241705")
                    hideKeyboard(locationEditText)
                }
                true
            } else {
                false
            }
        }

        viewModel.fetchWeatherData(query = "Jacksonville", apiKey = "b82bc4ef331546e7906224746241705")


    }

    private fun setupRecyclerViews(weatherResponse: WeatherResponse) {
        hourlyAdapter = HourlyWeatherAdapter(weatherResponse.forecast.forecastday[0].hour)
        dailyAdapter = DailyWeatherAdapter(weatherResponse.forecast.forecastday)

        findViewById<RecyclerView>(R.id.rv_hourly).adapter = hourlyAdapter
        findViewById<RecyclerView>(R.id.rv_daily).adapter = dailyAdapter
    }

    private fun displayWeatherData(weatherResponse: WeatherResponse) {
        val currentTemp = weatherResponse.current.temp_f
        val condition = weatherResponse.current.condition
        val location = weatherResponse.location


        findViewById<TextView>(R.id.et_location).text = location.name
        findViewById<TextView>(R.id.tv_current_temp).text = "${currentTemp.toInt()}°F"
        findViewById<TextView>(R.id.tv_current_type).text = condition.text
    }
    private fun hideKeyboard(editText: EditText) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }
}
