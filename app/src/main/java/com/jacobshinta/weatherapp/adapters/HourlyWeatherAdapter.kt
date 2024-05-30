package com.jacobshinta.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jacobshinta.weatherapp.model.Hour
import java.text.SimpleDateFormat
import java.util.Locale

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
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val displayFormat = SimpleDateFormat("h a", Locale.getDefault())
        val hourTime = dateFormat.parse(forecast.time)
        holder.time.text = hourTime?.let { displayFormat.format(it) } ?: ""
        holder.temp.text = "${forecast.temp_f.toInt()}°F"
        Glide.with(holder.itemView.context)
            .load(forecast.condition.fixIcon)
            .into(holder.icon)
    }

    override fun getItemCount() = hourlyForecasts.size
}
