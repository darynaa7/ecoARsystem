package diploma.project.eco_ar.feature_ar.ui.viewModel

sealed interface ARUiAction {
    data object NavigateBack : ARUiAction
}