package diploma.project.eco_ar.feature_profile.ui.viewModel.profileEdit

import android.content.Context
import android.net.Uri

sealed interface ProfileEditAction {
    data class OnNameTextChanged(val name: String) : ProfileEditAction
    data class OnEmailTextChanged(val email: String) : ProfileEditAction
    data class OnPasswordTextChanged(val password: String) : ProfileEditAction
    data class OnRepeatedPasswordTextChanged(val repeatedPassword: String) : ProfileEditAction

    data class OnNewProfilePictureSelected(val uri: Uri) : ProfileEditAction

    data class Save(val context: Context) : ProfileEditAction
}