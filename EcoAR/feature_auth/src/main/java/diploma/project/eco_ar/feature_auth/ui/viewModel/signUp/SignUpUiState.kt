package diploma.project.eco_ar.feature_auth.ui.viewModel.signUp

import androidx.compose.runtime.Immutable
import diploma.project.eco_ar.core.domain.validation.ValidationError

@Immutable
data class SignUpUiState(
    // Field data
    val name: String = "",
    val email: String = "",
    val emailValidationError: ValidationError? = null,
    val password: String = "",
    val passwordValidationError: ValidationError? = null,
    val repeatedPassword: String = "",
    val repeatedPasswordValidationError: ValidationError? = null,
    val isSignUpButtonEnabled: Boolean = false,
    // Other
    val isLoading: Boolean = false
) {
    fun hasErrors(): Boolean {
        return emailValidationError != null || passwordValidationError != null || repeatedPasswordValidationError != null
    }
}