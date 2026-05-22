package diploma.project.eco_ar.core.data.remote.dto.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationRequestDTO(
    @SerialName("lat") val latitude: Double,
    @SerialName("lon") val longitude: Double
)