package diploma.project.eco_ar.feature_profile.ui.viewModel.settings

sealed interface SettingsUiAction {
    data object NavigateBack : SettingsUiAction
    data object NavigateToPermissions : SettingsUiAction
}