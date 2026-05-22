package diploma.project.eco_ar.feature_auth.ui.viewModel.signUp

sealed interface SignUpAction {
    data class OnNameTextChanged(val name: String) : SignUpAction

    data class OnEmailTextChanged(val email: String) : SignUpAction
    data class OnPasswordTextChanged(val password: String) : SignUpAction
    data class OnRepeatedPasswordTextChanged(val repeatedPassword: String) : SignUpAction

    data object SignUp : SignUpAction
}