package diploma.project.eco_ar.core.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReportDTO(
    val id: String,
    val userId: String,
    @SerialName("lat") val latitude: Double,
    @SerialName("lon") val longitude: Double,
    val temperature: Float,
    val humidity: Int,
    val pressure: Int,
    val windSpeed: Float,
    @SerialName("windDeg") val windDegrees: Int,
    val aqi: Int,
    @SerialName("pm2_5") val pm2point5: Float,
    val pm10: Float,
    val no2: Float,
    val o3: Float,
    val co: Float,
    val createdAt: String
)