package diploma.project.eco_ar.feature_auth.ui.viewModel.signIn

import androidx.compose.runtime.Immutable
import diploma.project.eco_ar.core.domain.validation.ValidationError

@Immutable
data class SignInUiState(
    // Field data
    val username: String = "",
    val usernameValidationError: ValidationError? = null,
    val password: String = "",
    val passwordValidationError: ValidationError? = null,
    // Other
    val isLoading: Boolean = false
) {
    fun hasErrors(): Boolean {
        return usernameValidationError != null || passwordValidationError != null
    }
}
