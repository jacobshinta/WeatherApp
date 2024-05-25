package com.jacobshinta.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jacobshinta.weatherapp.model.Hour

class HourlyWeatherAdapter(private val hourlyForecasts: List<Hour>) :
    RecyclerView.Adapter<HourlyWeatherAdapter.HourlyViewHolder>() {

    class HourlyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val time: TextView = itemView.findViewById(R.id.tv_forecast_time)
        val temp: TextView = itemView.findViewById(R.id.tv_forecast_temp)
        val icon: ImageView = itemView.findViewById(R.id.img_forecast_type)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_hourly_forecast, parent, false)
        return HourlyViewHolder(view)
    }

    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        val forecast = hourlyForecasts[position]
        holder.time.text = forecast.time.substring(11, 16)
        holder.temp.text = "${forecast.temp_f.toInt()}°F"
        Glide.with(holder.itemView.context)
            .load(forecast.condition.fixIcon)
            .into(holder.icon)
    }

    override fun getItemCount() = hourlyForecasts.size
}
