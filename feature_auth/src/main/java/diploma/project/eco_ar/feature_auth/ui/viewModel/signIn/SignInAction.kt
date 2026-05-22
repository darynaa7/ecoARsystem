package diploma.project.eco_ar.feature_auth.ui.viewModel.signIn

sealed interface SignInAction {
    data class OnNameTextChanged(val username: String) : SignInAction
    data class OnPasswordTextChanged(val password: String) : SignInAction

    data object SignIn : SignInAction
}