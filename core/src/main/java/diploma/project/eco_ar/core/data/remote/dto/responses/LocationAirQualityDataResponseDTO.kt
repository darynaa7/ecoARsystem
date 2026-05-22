package diploma.project.eco_ar.core.data.remote.dto.responses

import diploma.project.eco_ar.core.data.remote.dto.LocationDTO
import diploma.project.eco_ar.core.data.remote.dto.PollutionDTO
import diploma.project.eco_ar.core.data.remote.dto.WeatherDTO
import kotlinx.serialization.Serializable

@Serializable
data class LocationAirQualityDataResponseDTO(
    val location: LocationDTO,
    val pollution: PollutionDTO,
    val weather: WeatherDTO
)