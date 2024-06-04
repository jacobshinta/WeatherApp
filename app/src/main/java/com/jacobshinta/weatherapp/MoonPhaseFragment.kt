package com.jacobshinta.weatherapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
    private lateinit var progressBar: ProgressBar
    private lateinit var moonLayout: LinearLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var leftArrow: ImageView
    private lateinit var rightArrow: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_moon_phase, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews(view)
        setupRecyclerView()
        setupObservers()
        loadSavedLocation()
    }

    private fun initializeViews(view: View) {
        progressBar = view.findViewById(R.id.progress_bar)
        moonLayout = view.findViewById(R.id.moon_layout)
        recyclerView = view.findViewById(R.id.rv_moon_phase)
        leftArrow = view.findViewById(R.id.left_arrow)
        rightArrow = view.findViewById(R.id.right_arrow)

        leftArrow.setOnClickListener {
            val currentPosition = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            if (currentPosition > 0) {
                recyclerView.smoothScrollToPosition(currentPosition - 1)
            }
        }

        rightArrow.setOnClickListener {
            val currentPosition = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            if (currentPosition < moonPhaseAdapter.itemCount - 1) {
                recyclerView.smoothScrollToPosition(currentPosition + 1)
            }
        }
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        moonPhaseAdapter = MoonPhaseAdapter(emptyList())
        recyclerView.adapter = moonPhaseAdapter
        val moonSnapHelper = LinearSnapHelper()
        moonSnapHelper.attachToRecyclerView(recyclerView)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                updateArrows()
            }
        })
    }

    private fun setupObservers() {
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            moonLayout.visibility = if (isLoading) View.GONE else View.VISIBLE
        })

        viewModel.weatherData.observe(viewLifecycleOwner) { weatherResponse ->
            weatherResponse?.let {
                moonPhases = it.forecast.forecastday
                moonPhaseAdapter.updateData(moonPhases)
                updateArrows()
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

    private fun updateArrows() {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()
        val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()

        leftArrow.visibility = if (firstVisiblePosition == 0) View.GONE else View.VISIBLE
        rightArrow.visibility = if (lastVisiblePosition == moonPhaseAdapter.itemCount - 1) View.GONE else View.VISIBLE
    }
}
