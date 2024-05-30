package com.jacobshinta.weatherapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.media3.common.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.jacobshinta.weatherapp.model.WeatherResponse
import com.jacobshinta.weatherapp.viewmodel.WeatherViewModel
import java.text.SimpleDateFormat
import java.util.*

class WeatherFragment : Fragment() {

    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var hourlyAdapter: HourlyWeatherAdapter
    private lateinit var dailyAdapter: DailyWeatherAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var weatherLayout: LinearLayout
    private lateinit var locationEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews(view)
        setupRecyclerViews(view)
        setupObservers()
        setupLocationEditText()
        loadSavedLocation()
    }

    private fun initializeViews(view: View) {
        locationEditText = view.findViewById(R.id.et_location)
        progressBar = view.findViewById(R.id.progressBar)
        weatherLayout = view.findViewById(R.id.weatherLayout)
    }

    private fun setupRecyclerViews(view: View) {
        val hourlyRecyclerView = view.findViewById<RecyclerView>(R.id.rv_hourly)
        val dailyRecyclerView = view.findViewById<RecyclerView>(R.id.rv_daily)

        hourlyRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        dailyRecyclerView.layoutManager = LinearLayoutManager(context)

        val dailySnapHelper = LinearSnapHelper()
        dailySnapHelper.attachToRecyclerView(dailyRecyclerView)

        val hourlySnapHelper = LinearSnapHelper()
        hourlySnapHelper.attachToRecyclerView(hourlyRecyclerView)
    }

    private fun setupObservers() {
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            weatherLayout.visibility = if (isLoading) View.GONE else View.VISIBLE
        })

        viewModel.weatherData.observe(viewLifecycleOwner, Observer { weatherResponse ->
            weatherResponse?.let {
                setupRecyclerViewsWithData(it)
                displayWeatherData(it)
            }
        })
    }

    private fun setupLocationEditText() {
        locationEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val location = locationEditText.text.toString()
                if (location.isNotBlank()) {
                    viewModel.fetchWeatherData(query = location, apiKey = "b82bc4ef331546e7906224746241705")
                    hideKeyboard(locationEditText)
                    saveLocation(location)
                }
                true
            } else {
                false
            }
        }
    }

    private fun loadSavedLocation() {
        val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences("WeatherApp", Context.MODE_PRIVATE)
        val savedLocation = sharedPreferences.getString("UserLocation", null)

        if (savedLocation != null) {
            viewModel.fetchWeatherData(query = savedLocation, apiKey = "b82bc4ef331546e7906224746241705")
        } else {
            navigateToLocationPrompt()
        }
    }

    private fun saveLocation(location: String) {
        val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences("WeatherApp", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("UserLocation", location)
        editor.apply()
    }

    private fun navigateToLocationPrompt() {
        val intent = Intent(activity, LocationPromptActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun setupRecyclerViewsWithData(weatherResponse: WeatherResponse) {
        val currentTime = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

        val currentDayHours = weatherResponse.forecast.forecastday[0].hour.filter {
            val hourTime = dateFormat.parse(it.time)
            hourTime != null && hourTime.after(currentTime)
        }

        val nextDayHours = if (weatherResponse.forecast.forecastday.size > 1) {
            weatherResponse.forecast.forecastday[1].hour
        } else {
            emptyList()
        }

        val combinedHours = (currentDayHours + nextDayHours).take(24)

        hourlyAdapter = HourlyWeatherAdapter(combinedHours)

        val dailyForecasts = weatherResponse.forecast.forecastday.drop(1)
        dailyAdapter = DailyWeatherAdapter(dailyForecasts)

        view?.findViewById<RecyclerView>(R.id.rv_hourly)?.adapter = hourlyAdapter
        view?.findViewById<RecyclerView>(R.id.rv_daily)?.adapter = dailyAdapter
    }

    private fun displayWeatherData(weatherResponse: WeatherResponse) {
        val currentTemp = weatherResponse.current.temp_f
        val condition = weatherResponse.current.condition
        val location = weatherResponse.location

        view?.findViewById<TextView>(R.id.et_location)?.text = location.name
        view?.findViewById<TextView>(R.id.tv_current_temp)?.text = "${currentTemp.toInt()}°F"
        view?.findViewById<TextView>(R.id.tv_current_type)?.text = condition.text
    }

    private fun hideKeyboard(editText: EditText) {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }
}
