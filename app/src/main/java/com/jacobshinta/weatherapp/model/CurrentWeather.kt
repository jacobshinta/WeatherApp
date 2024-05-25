package com.jacobshinta.weatherapp.model

data class WeatherResponse(
    val current: Current,
    val location: Location,
    val forecast: Forecast
)

data class Current(
    val temp_c: Double,
    val temp_f: Double,
    val condition: Condition,
    val feelslike_c: Double,
    val feelslike_f: Double,
    val humidity: Int,
    val wind_kph: Double,
    val wind_mph: Double,
    val gust_kph: Double,
    val gust_mph: Double,
    val uv: Double,
    val vis_km: Double,
    val vis_miles: Double,
    val pressure_mb: Double,
    val pressure_in: Double,
    val precip_mm: Double,
    val precip_in: Double,
    val cloud: Int,
    val is_day: Int,
    val last_updated: String,
    val last_updated_epoch: Long
)

data class Location(
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val tz_id: String,
    val localtime: String,
    val localtime_epoch: Long
)

data class Forecast(
    val forecastday: List<ForecastDay>
)

data class ForecastDay(
    val date: String,
    val date_epoch: Long,
    val day: Day,
    val hour: List<Hour>
)

data class Day(
    val maxtemp_c: Double,
    val maxtemp_f: Double,
    val mintemp_c: Double,
    val mintemp_f: Double,
    val avgtemp_c: Double,
    val avgtemp_f: Double,
    val condition: Condition,
    val daily_chance_of_rain: Int
)

data class Hour(
    val time: String,
    val temp_c: Double,
    val temp_f: Double,
    val condition: Condition
)

data class Condition(
    val code: Int,
    val icon: String,
    val text: String
){
    val fixIcon: String
        get() = if (icon.startsWith("//")) "https:$icon" else icon
}
