package diploma.project.eco_ar.feature_auth.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDataDTO(
    val id: String,
    val username: String,
    val email: String
)