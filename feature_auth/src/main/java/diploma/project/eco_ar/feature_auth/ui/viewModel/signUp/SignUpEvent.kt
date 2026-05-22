package diploma.project.eco_ar.feature_auth.ui.viewModel.signUp

sealed interface SignUpEvent {
    data object SignUpSuccess : SignUpEvent
    data class SignUpFailure(val message: String? = null) : SignUpEvent
}