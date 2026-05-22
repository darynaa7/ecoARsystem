package diploma.project.eco_ar.core.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ErrorDTO(
    val message: String? = null
)
