package diploma.project.eco_ar.feature_auth.ui.viewModel.signIn

sealed interface SignInUiAction {
    data object NavigateToSignUp : SignInUiAction
}