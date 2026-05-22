package diploma.project.eco_ar.feature_home.ui.viewModel

sealed interface HomeUiAction {
    data object NavigateToAR : HomeUiAction
}