package com.jacobshinta.weatherapp

object MoonPhaseUtils {
    fun getMoonPhaseImage(phase: String): Int {
        return when (phase.lowercase()) {
            "full moon" -> R.drawable.full_moon
            "new moon" -> R.drawable.new_moon
            "first quarter" -> R.drawable.first_quarter
            "last quarter" -> R.drawable.last_quarter
            "waxing crescent" -> R.drawable.waxing_crescent
            "waning crescent" -> R.drawable.waning_crescent
            "waxing gibbous" -> R.drawable.waxing_gibbous
            "waning gibbous" -> R.drawable.waning_gibbous
            else -> R.drawable.full_moon
        }
    }
}
