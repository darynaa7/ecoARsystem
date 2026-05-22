package diploma.project.eco_ar.feature_profile.ui.viewModel.profile

import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import diploma.project.eco_ar.core.data.repository.UserDataRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class ProfileViewModel(
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    init {
        reloadUserData()
    }

    fun reloadUserData() {
        viewModelScope.launch {
            userDataRepository.getCurrentSessionUserData().collect { userData ->
                if (userData != null) {
                    _uiState.update {
                        it.copy(
                            name = userData.username,
                            email = userData.email
                        )
                    }
                }
            }
        }

        viewModelScope.launch {
            userDataRepository.getProfilePicture().onSuccess { file ->
                _uiState.update {
                    it.copy(
                        photoUri = file.toUri()
                    )
                }
            }
        }
    }
}