package diploma.project.eco_ar.feature_profile.ui.viewModel.profile

import android.net.Uri
import androidx.compose.runtime.Immutable

@Immutable
data class ProfileUiState(
    val photoUri: Uri? = null,
    val name: String = "",
    val email: String = ""
)
