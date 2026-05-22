package diploma.project.eco_ar.feature_profile.ui.viewModel.profileEdit

sealed interface ProfileEditEvent {
    data object SaveSuccess : ProfileEditEvent
    data class SaveDataFailure(val message: String? = null) : ProfileEditEvent
    data class SaveProfilePictureFailure(val message: String? = null) : ProfileEditEvent
}