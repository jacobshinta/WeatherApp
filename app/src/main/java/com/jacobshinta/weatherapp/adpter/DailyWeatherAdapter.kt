package com.jacobshinta.weatherapp.adpter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jacobshinta.weatherapp.R
import com.jacobshinta.weatherapp.model.DailyWeather

class DailyWeatherAdapter(private val dailyWeatherList: List<DailyWeather>) :
    RecyclerView.Adapter<DailyWeatherAdapter.DailyWeatherViewHolder>() {

    inner class DailyWeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val day: TextView = itemView.findViewById(R.id.tv_day)
        val highTemp: TextView = itemView.findViewById(R.id.tv_daily_high_temp)
        val lowTemp: TextView = itemView.findViewById(R.id.tv_daily_low_temp)
        val weatherChance: TextView = itemView.findViewById(R.id.tv_daily_chance)
        val weatherType: ImageView = itemView.findViewById(R.id.img_daily_weather_type)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyWeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_daily_forecast, parent, false)
        return DailyWeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: DailyWeatherViewHolder, position: Int) {
        val dailyWeather = dailyWeatherList[position]
        holder.day.text = dailyWeather.day
        holder.highTemp.text = "High: ${dailyWeather.highTemp}°F"
        holder.lowTemp.text = "Low: ${dailyWeather.lowTemp}°F"
        holder.weatherChance.text = "${dailyWeather.weatherChance}%"
        holder.weatherType.setImageResource(dailyWeather.weatherType)
    }

    override fun getItemCount() = dailyWeatherList.size
}
