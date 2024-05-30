package com.jacobshinta.weatherapp

import android.content.Context
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
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.jacobshinta.weatherapp.model.ForecastDay
import com.jacobshinta.weatherapp.viewmodel.WeatherViewModel

class MoonPhaseFragment : Fragment() {

    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var moonPhaseAdapter: MoonPhaseAdapter
    private lateinit var moonPhases: List<ForecastDay>
    private lateinit var locationEditText: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var moonLayout: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_moon_phase, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews(view)
        setupRecyclerView(view)
        setupObservers()
        loadSavedLocation()
        setupLocationEditText()
    }

    private fun initializeViews(view: View) {
        locationEditText = view.findViewById(R.id.et_location)
        progressBar = view.findViewById(R.id.progressBar)
        moonLayout = view.findViewById(R.id.moonLayout)
    }

    private fun setupRecyclerView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_moon_phase)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        moonPhaseAdapter = MoonPhaseAdapter(emptyList())
        recyclerView.adapter = moonPhaseAdapter
        val moonSnapHelper = LinearSnapHelper()
        moonSnapHelper.attachToRecyclerView(recyclerView)
    }

    private fun setupObservers() {
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            moonLayout.visibility = if (isLoading) View.GONE else View.VISIBLE
        })

        viewModel.weatherData.observe(viewLifecycleOwner) { weatherResponse ->
            weatherResponse?.let {
                locationEditText.setText(it.location.name)
                moonPhases = it.forecast.forecastday
                moonPhaseAdapter.updateData(moonPhases)
            }
        }
    }

    private fun loadSavedLocation() {
        val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences("WeatherApp", Context.MODE_PRIVATE)
        val savedLocation = sharedPreferences.getString("UserLocation", null)
        savedLocation?.let {
            viewModel.fetchWeatherData(it, "b82bc4ef331546e7906224746241705", 7)
        }
    }

    private fun setupLocationEditText() {
        locationEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val location = locationEditText.text.toString()
                if (location.isNotBlank()) {
                    viewModel.fetchWeatherData(location, "b82bc4ef331546e7906224746241705", 7)
                    hideKeyboard(locationEditText)
                    saveLocation(location)
                }
                true
            } else {
                false
            }
        }
    }

    private fun saveLocation(location: String) {
        val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences("WeatherApp", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("UserLocation", location)
        editor.apply()
    }

    private fun hideKeyboard(editText: EditText) {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }
}
