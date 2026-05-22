package diploma.project.eco_ar.feature_auth.data.remote.dto.requests

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationRequestDTO(
    val username: String,
    val email: String,
    val password: String
)