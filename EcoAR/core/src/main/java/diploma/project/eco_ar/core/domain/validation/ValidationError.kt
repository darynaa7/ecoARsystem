package diploma.project.eco_ar.core.domain.validation

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
@Serializable
class ValidationError(
    val message: String
)