package diploma.project.eco_ar.feature_profile.ui.viewModel.profile

sealed interface ProfileUiAction {
    data object NavigateToReports : ProfileUiAction
    data object NavigateToSettings : ProfileUiAction
    data object NavigateToProfileEdit : ProfileUiAction
}