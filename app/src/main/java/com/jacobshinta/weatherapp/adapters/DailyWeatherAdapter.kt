package com.jacobshinta.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jacobshinta.weatherapp.model.Current
import com.jacobshinta.weatherapp.model.ForecastDay
import java.text.SimpleDateFormat
import java.util.*

class DailyWeatherAdapter(private val dailyForecasts: List<ForecastDay>) :
    RecyclerView.Adapter<DailyWeatherAdapter.DailyViewHolder>() {

    class DailyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date     : TextView = itemView.findViewById(R.id.tv_day)
        val lowTemp  : TextView = itemView.findViewById(R.id.tv_daily_low_temp)
        val highTemp : TextView = itemView.findViewById(R.id.tv_daily_high_temp)
        val icon     : ImageView = itemView.findViewById(R.id.img_daily_weather_type)
        val chance   : TextView = itemView.findViewById(R.id.tv_daily_chance)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_daily_forecast, parent, false)
        return DailyViewHolder(view)
    }

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
        val forecast = dailyForecasts[position]
        holder.date.text =
            SimpleDateFormat("EEE", Locale.getDefault()).format(Date(forecast.date_epoch * 1000))
        holder.lowTemp.text = "${forecast.day.mintemp_f.toInt()}°F"
        holder.highTemp.text = "${forecast.day.maxtemp_f.toInt()}°F"
        holder.chance.text = "${forecast.day.daily_chance_of_rain}%"
        Glide.with(holder.itemView.context)
            .load(forecast.day.condition.fixIcon)
            .into(holder.icon)
    }

    override fun getItemCount() = dailyForecasts.size
}
