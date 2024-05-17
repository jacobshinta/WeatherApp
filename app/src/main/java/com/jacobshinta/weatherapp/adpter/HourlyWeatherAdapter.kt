package com.jacobshinta.weatherapp.adpter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jacobshinta.weatherapp.R
import com.jacobshinta.weatherapp.model.HourlyWeather

class HourlyWeatherAdapter(private val hourlyWeatherList: List<HourlyWeather>) :
    RecyclerView.Adapter<HourlyWeatherAdapter.HourlyWeatherViewHolder>() {

    inner class HourlyWeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val time: TextView = itemView.findViewById(R.id.tv_forecast_time)
        val temp: TextView = itemView.findViewById(R.id.tv_forecast_temp)
        val image: ImageView = itemView.findViewById(R.id.img_forecast_type)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyWeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_hourly_forecast, parent, false)
        return HourlyWeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: HourlyWeatherViewHolder, position: Int) {
        val hourlyWeather = hourlyWeatherList[position]
        holder.time.text = hourlyWeather.time
        holder.temp.text = "${hourlyWeather.temp}°F"
        holder.image.setImageResource(hourlyWeather.weatherType)
    }

    override fun getItemCount() = hourlyWeatherList.size
}
