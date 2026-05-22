package diploma.project.eco_ar.feature_profile.ui.viewModel.settings

sealed interface SettingsEvent {
    data object LogOutSuccess : SettingsEvent
    data class LogOutFailure(val message: String? = null) : SettingsEvent
}