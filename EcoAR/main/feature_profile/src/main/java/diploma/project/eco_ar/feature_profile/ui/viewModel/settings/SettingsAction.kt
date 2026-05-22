package diploma.project.eco_ar.feature_profile.ui.viewModel.settings

sealed interface SettingsAction {
    data object LogOut : SettingsAction
}