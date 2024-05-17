package com.jacobshinta.weatherapp.model

import com.jacobshinta.weatherapp.R

object MockData {

    fun getMockHourlyWeather(): List<HourlyWeather> {
        return listOf(
            HourlyWeather("8 AM", 60 , R.drawable.baseline_wb_sunny_24),
            HourlyWeather("9 AM", 62, R.drawable.baseline_wb_cloudy_24),
            HourlyWeather("10 AM", 65, R.drawable.baseline_wb_sunny_24),
            HourlyWeather("11 AM", 68, R.drawable.baseline_wb_cloudy_24),
            HourlyWeather("12 PM", 70, R.drawable.baseline_wb_sunny_24),
            HourlyWeather("1 PM", 72, R.drawable.baseline_wb_cloudy_24),
            HourlyWeather("2 PM", 73, R.drawable.baseline_wb_sunny_24),
            HourlyWeather("3 PM", 73, R.drawable.baseline_wb_cloudy_24),
            HourlyWeather("4 PM", 75, R.drawable.baseline_wb_sunny_24)
        )
    }

    fun getMockDailyWeather(): List<DailyWeather> {
        return listOf(
            DailyWeather("Mon", 75, 55, 50, R.drawable.baseline_wb_sunny_24),
            DailyWeather("Tue", 70, 52, 75, R.drawable.baseline_wb_cloudy_24),
            DailyWeather("Wed", 68, 50, 0, R.drawable.baseline_wb_sunny_24),
            DailyWeather("Thu", 72, 53, 0, R.drawable.baseline_wb_cloudy_24),
            DailyWeather("Fri", 74, 54, 10, R.drawable.baseline_wb_sunny_24),
            DailyWeather("Sat", 76, 55, 25, R.drawable.baseline_wb_cloudy_24),
            DailyWeather("Sun", 78, 57, 50, R.drawable.baseline_wb_sunny_24),
            DailyWeather("Mon", 71, 51, 75, R.drawable.baseline_wb_cloudy_24),
            DailyWeather("Tue", 72, 50, 75, R.drawable.baseline_wb_sunny_24),
            DailyWeather("Wed", 79, 53, 50, R.drawable.baseline_wb_cloudy_24)
        )
    }
}