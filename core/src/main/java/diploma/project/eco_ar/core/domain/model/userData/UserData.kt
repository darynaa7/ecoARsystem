package diploma.project.eco_ar.core.domain.model.userData

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class UserData(
    val uuid: String,
    val accessToken: String,
    val refreshToken: String,
    val username: String,
    val email: String
)