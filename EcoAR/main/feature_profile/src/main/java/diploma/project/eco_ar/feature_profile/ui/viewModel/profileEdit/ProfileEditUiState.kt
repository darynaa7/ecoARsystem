package diploma.project.eco_ar.feature_profile.ui.viewModel.profileEdit

import android.net.Uri
import androidx.compose.runtime.Immutable
import diploma.project.eco_ar.core.domain.validation.ValidationError

@Immutable
data class ProfileEditUiState(
    val isLoading: Boolean = false,
    val photoUri: Uri? = null,
    val name: String = "",
    val nameValidationError: ValidationError? = null,
    val email: String = "",
    val emailValidationError: ValidationError? = null,
    val password: String = "",
    val passwordValidationError: ValidationError? = null,
    val repeatedPassword: String = "",
    val repeatedPasswordValidationError: ValidationError? = null
) {
    fun hasErrors(): Boolean {
        return nameValidationError != null || emailValidationError != null || passwordValidationError != null || repeatedPasswordValidationError != null
    }
}
