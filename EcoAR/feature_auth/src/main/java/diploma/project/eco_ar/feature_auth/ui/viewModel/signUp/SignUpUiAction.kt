package diploma.project.eco_ar.feature_auth.ui.viewModel.signUp

sealed interface SignUpUiAction {
    data object NavigateBack : SignUpUiAction
}