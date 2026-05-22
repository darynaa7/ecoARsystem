package diploma.project.eco_ar.feature_auth.ui.viewModel.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import diploma.project.eco_ar.core.domain.validation.ValidationChecker
import diploma.project.eco_ar.feature_auth.data.repository.AuthRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val authRepository: AuthRepository,
    private val validationChecker: ValidationChecker
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState = _uiState.asStateFlow()

    private val _events = Channel<SignUpEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: SignUpAction) {
        when (action) {
            is SignUpAction.OnNameTextChanged -> onNameTextChanged(action.name)

            is SignUpAction.OnEmailTextChanged -> onEmailTextChanged(action.email)
            is SignUpAction.OnPasswordTextChanged -> onPasswordTextChanged(action.password)
            is SignUpAction.OnRepeatedPasswordTextChanged -> onRepeatedPasswordTextChanged(action.repeatedPassword)

            is SignUpAction.SignUp -> signUp()
        }
    }

    private fun onNameTextChanged(name: String) {
        _uiState.update {
            it.copy(
                name = name
            )
        }

        enableOrDisableSignUpButton()
    }

    private fun onEmailTextChanged(email: String) {
        _uiState.update {
            it.copy(
                email = email
            )
        }

        enableOrDisableSignUpButton()
    }

    private fun onPasswordTextChanged(password: String) {
        _uiState.update {
            it.copy(
                password = password
            )
        }

        enableOrDisableSignUpButton()
    }

    private fun onRepeatedPasswordTextChanged(repeatedPassword: String) {
        _uiState.update {
            it.copy(
                repeatedPassword = repeatedPassword
            )
        }

        enableOrDisableSignUpButton()
    }

    private fun signUp() {
        _uiState.update {
            it.copy(
                emailValidationError = validationChecker.checkEmail(uiState.value.email),
                passwordValidationError = validationChecker.checkPassword(uiState.value.password),
                repeatedPasswordValidationError = validationChecker.checkRepeatedPassword(
                    uiState.value.password,
                    uiState.value.repeatedPassword,
                )
            )
        }

        if (uiState.value.hasErrors()) return

        viewModelScope.launch {
            if (uiState.value.password != uiState.value.repeatedPassword) return@launch

            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }

            authRepository
                .register(
                    username = uiState.value.name.trim(),
                    email = uiState.value.email.trim(),
                    password = uiState.value.password.trim()
                )
                .onSuccess {
                    _events.send(SignUpEvent.SignUpSuccess)
                }
                .onFailure {
                    _events.send(SignUpEvent.SignUpFailure(it.message))
                }

            _uiState.update {
                it.copy(
                    isLoading = false
                )
            }
        }
    }

    private fun enableOrDisableSignUpButton() {
        val willButtonBeEnabled = run {
            if (uiState.value.name.isBlank()) return@run false
            if (uiState.value.email.isBlank()) return@run false
            if (uiState.value.password.isBlank()) return@run false
            if (uiState.value.repeatedPassword.isBlank()) return@run false

            true
        }

        _uiState.update {
            it.copy(
                isSignUpButtonEnabled = willButtonBeEnabled
            )
        }
    }
}