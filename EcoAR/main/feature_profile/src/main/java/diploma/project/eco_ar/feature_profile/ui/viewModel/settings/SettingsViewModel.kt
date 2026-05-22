package diploma.project.eco_ar.feature_profile.ui.viewModel.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import diploma.project.eco_ar.core.data.UserOutLogger
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class SettingsViewModel(
    private val userOutLogger: UserOutLogger
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState = _uiState.asStateFlow()

    private val _events = Channel<SettingsEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: SettingsAction) {
        when (action) {
            SettingsAction.LogOut -> logout()
        }
    }

    private fun logout() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }

            userOutLogger.logout()
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            isLoading = false
                        )
                    }

                    _events.send(SettingsEvent.LogOutSuccess)
                }
                .onFailure {
                    _events.send(SettingsEvent.LogOutFailure(it.message))
                }
        }
    }
}