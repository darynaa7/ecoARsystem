package diploma.project.eco_ar.core.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class AirQualityReport(
    val location: String,
    val coordinates: Pair<Double, Double>,
    val temperature: Float,
    val humidity: Int,
    val windSpeed: Float,
    val aqi: Int
) {
    fun getAirQuality(): AirQuality {
        return when (aqi) {
            in 0..50 -> AirQuality.CLEAN
            in 51..100 -> AirQuality.MEDIUM
            in 101..150 -> AirQuality.BAD
            else -> AirQuality.POLLUTED
        }
    }
}