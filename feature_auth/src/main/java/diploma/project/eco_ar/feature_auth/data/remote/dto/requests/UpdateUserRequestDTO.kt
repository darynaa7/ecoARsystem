package diploma.project.eco_ar.feature_auth.data.remote.dto.requests

import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserRequestDTO(
    val username: String,
    val email: String,
    val password: String?
)