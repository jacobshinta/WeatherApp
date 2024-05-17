package com.jacobshinta.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jacobshinta.weatherapp.adpter.DailyWeatherAdapter
import com.jacobshinta.weatherapp.adpter.HourlyWeatherAdapter
import com.jacobshinta.weatherapp.model.MockData
import com.jacobshinta.weatherapp.ui.theme.WeatherAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val hourlyWeatherList = MockData.getMockHourlyWeather()
        val dailyWeatherList = MockData.getMockDailyWeather()

        val rvHourly: RecyclerView = findViewById(R.id.rv_hourly)
        rvHourly.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvHourly.adapter = HourlyWeatherAdapter(hourlyWeatherList)

        val rvDaily: RecyclerView = findViewById(R.id.rv_daily)
        rvDaily.layoutManager = LinearLayoutManager(this)
        rvDaily.adapter = DailyWeatherAdapter(dailyWeatherList)
    }
}
