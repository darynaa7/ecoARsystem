package diploma.project.eco_ar.feature_profile.ui.viewModel.profileEdit

sealed interface ProfileEditUiAction {
    data object NavigateBack : ProfileEditUiAction
}