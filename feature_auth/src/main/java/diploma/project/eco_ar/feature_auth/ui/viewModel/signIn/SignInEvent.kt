package diploma.project.eco_ar.feature_auth.ui.viewModel.signIn

sealed interface SignInEvent {
    data object SignInSuccess : SignInEvent
    data class SignInFailure(val message: String? = null) : SignInEvent
}