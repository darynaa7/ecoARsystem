package diploma.project.eco_ar.feature_auth.ui.viewModel.signIn

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

class SignInViewModel(
    private val authRepository: AuthRepository,
    private val validationChecker: ValidationChecker
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState = _uiState.asStateFlow()

    private val _events = Channel<SignInEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: SignInAction) {
        when (action) {
            is SignInAction.OnNameTextChanged -> onUsernameTextChanged(action.username)
            is SignInAction.OnPasswordTextChanged -> onPasswordTextChanged(action.password)

            is SignInAction.SignIn -> signIn()
        }
    }

    private fun onUsernameTextChanged(username: String) {
        _uiState.update {
            it.copy(
                username = username
            )
        }
    }

    private fun onPasswordTextChanged(password: String) {
        _uiState.update {
            it.copy(
                password = password
            )
        }
    }

    private fun signIn() {
        _uiState.update {
            it.copy(
                usernameValidationError = validationChecker.checkName(uiState.value.username),
                passwordValidationError = validationChecker.checkPassword(uiState.value.password)
            )
        }

        if (uiState.value.hasErrors()) return

        _uiState.update {
            it.copy(
                isLoading = true
            )
        }

        viewModelScope.launch {
            authRepository
                .login(
                    username = uiState.value.username.trim(),
                    password = uiState.value.password.trim()
                )
                .onSuccess {
                    _events.send(SignInEvent.SignInSuccess)
                }
                .onFailure {
                    _events.send(SignInEvent.SignInFailure(it.message))
                }

            _uiState.update {
                it.copy(
                    isLoading = false
                )
            }
        }
    }
}