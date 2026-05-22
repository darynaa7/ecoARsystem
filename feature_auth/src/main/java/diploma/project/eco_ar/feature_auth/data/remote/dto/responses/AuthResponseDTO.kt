package diploma.project.eco_ar.feature_auth.data.remote.dto.responses

import diploma.project.eco_ar.feature_auth.data.remote.dto.UserDataDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponseDTO(
    @SerialName("user") val userData: UserDataDTO
)