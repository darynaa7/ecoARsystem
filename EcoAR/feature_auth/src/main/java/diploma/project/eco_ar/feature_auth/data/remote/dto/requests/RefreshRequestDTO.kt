package diploma.project.eco_ar.feature_auth.data.remote.dto.requests

import kotlinx.serialization.Serializable

@Serializable
data class RefreshRequestDTO(
    val refreshToken: String
)