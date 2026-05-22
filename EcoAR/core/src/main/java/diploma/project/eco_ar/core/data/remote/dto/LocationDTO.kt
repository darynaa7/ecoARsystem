package diploma.project.eco_ar.core.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LocationDTO(
    val city: String,
    val state: String,
    val country: String
)
