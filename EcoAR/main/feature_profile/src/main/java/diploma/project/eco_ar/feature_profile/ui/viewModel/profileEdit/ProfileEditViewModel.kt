package diploma.project.eco_ar.feature_profile.ui.viewModel.profileEdit

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import androidx.compose.runtime.snapshots.Snapshot
import androidx.core.net.toFile
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import diploma.project.eco_ar.core.data.repository.UserDataRepository
import diploma.project.eco_ar.core.domain.validation.ValidationChecker
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

@OptIn(FlowPreview::class)
class ProfileEditViewModel(
    private val validationChecker: ValidationChecker,
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileEditUiState())
    val uiState = _uiState.asStateFlow()

    private val _events = Channel<ProfileEditEvent>()
    val events = _events.receiveAsFlow()

    init {
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

    fun onAction(action: ProfileEditAction) {
        when (action) {
            is ProfileEditAction.OnNameTextChanged -> onNameTextChanged(action.name)
            is ProfileEditAction.OnEmailTextChanged -> onEmailTextChanged(action.email)
            is ProfileEditAction.OnPasswordTextChanged -> onPasswordTextChanged(action.password)
            is ProfileEditAction.OnRepeatedPasswordTextChanged -> onRepeatedPasswordTextChanged(action.repeatedPassword)

            is ProfileEditAction.OnNewProfilePictureSelected -> onNewProfilePictureSelected(action.uri)

            is ProfileEditAction.Save -> save(action.context)
        }
    }

    private fun onNameTextChanged(name: String) {
        _uiState.update {
            it.copy(
                name = name
            )
        }
    }

    private fun onEmailTextChanged(email: String) {
        _uiState.update {
            it.copy(
                email = email
            )
        }
    }

    private fun onPasswordTextChanged(password: String) {
        _uiState.update {
            it.copy(
                password = password
            )
        }
    }

    private fun onRepeatedPasswordTextChanged(repeatedPassword: String) {
        _uiState.update {
            it.copy(
                repeatedPassword = repeatedPassword
            )
        }
    }

    private fun onNewProfilePictureSelected(uri: Uri) {
        _uiState.update {
            it.copy(
                photoUri = uri
            )
        }
    }

    private fun save(context: Context) {
        Snapshot.withMutableSnapshot {
            _uiState.update {
                it.copy(
                    nameValidationError = validationChecker.checkName(uiState.value.name),
                    emailValidationError = validationChecker.checkEmail(uiState.value.email),
                    passwordValidationError = null,
                    repeatedPasswordValidationError = null
                )
            }

            if (uiState.value.password.isNotBlank()) {
                _uiState.update {
                    it.copy(
                        passwordValidationError = validationChecker.checkPassword(uiState.value.password),
                        repeatedPasswordValidationError = validationChecker.checkRepeatedPassword(uiState.value.password, uiState.value.repeatedPassword)
                    )
                }
            }
        }

        if (uiState.value.hasErrors()) return

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }

            viewModelScope.launch {
                userDataRepository
                    .setCurrentSessionUserData(
                        username = uiState.value.name.trim(),
                        email = uiState.value.email.trim(),
                        password = uiState.value.password.trim()
                    )
                    .onSuccess {
                        _uiState.update {
                            it.copy(
                                password = "",
                                repeatedPassword = ""
                            )
                        }

                        val file = uriToFile(context, uiState.value.photoUri)

                        if (file == null) {
                            _events.send(ProfileEditEvent.SaveSuccess)
                            return@onSuccess
                        }

                        userDataRepository
                            .updateProfilePicture(
                                file = file
                            )
                            .onSuccess {
                                _events.send(ProfileEditEvent.SaveSuccess)
                            }
                            .onFailure {
                                _events.send(ProfileEditEvent.SaveProfilePictureFailure(it.message))
                            }
                    }
                    .onFailure {
                        _events.send(ProfileEditEvent.SaveDataFailure(it.message))
                    }

                _uiState.update {
                    it.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    fun uriToFile(context: Context, uri: Uri?): File? {
        if (uri == null) return null

        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val tempFile = File(context.cacheDir, "temp_file_${System.currentTimeMillis()}.jpg")

            inputStream?.use { input ->
                FileOutputStream(tempFile).use { output ->
                    input.copyTo(output)
                }
            }

            tempFile
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}