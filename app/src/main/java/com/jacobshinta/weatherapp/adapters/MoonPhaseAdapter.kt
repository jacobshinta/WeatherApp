package com.jacobshinta.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jacobshinta.weatherapp.model.ForecastDay

class MoonPhaseAdapter(private var moonPhases: List<ForecastDay>) : RecyclerView.Adapter<MoonPhaseAdapter.MoonPhaseViewHolder>() {

    class MoonPhaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val phaseImage: ImageView = itemView.findViewById(R.id.phase_image)
        val phaseName: TextView = itemView.findViewById(R.id.phase_name)
        val moonrise: TextView = itemView.findViewById(R.id.moonrise)
        val date: TextView = itemView.findViewById(R.id.date)
        val moonset: TextView = itemView.findViewById(R.id.moonset)
        val illumination: TextView = itemView.findViewById(R.id.illumination)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoonPhaseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_moon_phase, parent, false)
        return MoonPhaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoonPhaseViewHolder, position: Int) {
        val moonPhase = moonPhases[position]
        holder.phaseImage.setImageResource(MoonPhaseUtils.getMoonPhaseImage(moonPhase.astro.moon_phase))
        holder.phaseName.text = moonPhase.astro.moon_phase
        holder.date.text = moonPhase.date
        holder.moonrise.text = moonPhase.astro.moonrise
        holder.moonset.text = moonPhase.astro.moonset
        holder.illumination.text = "${moonPhase.astro.moon_illumination}%"
    }

    override fun getItemCount(): Int = moonPhases.size

    fun updateData(newMoonPhases: List<ForecastDay>) {
        moonPhases = newMoonPhases
        notifyDataSetChanged()
    }
}
