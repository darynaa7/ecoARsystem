package diploma.project.eco_ar.feature_profile.ui.viewModel.reports

sealed interface ReportsUiAction {
    data object NavigateBack : ReportsUiAction
}