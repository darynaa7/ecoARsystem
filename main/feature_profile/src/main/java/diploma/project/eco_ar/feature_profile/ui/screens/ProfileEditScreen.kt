package diploma.project.eco_ar.feature_profile.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import diploma.project.eco_ar.core.ui.components.buttons.VectorButton
import diploma.project.eco_ar.core.ui.components.miscellaneous.ColumnScreen
import diploma.project.eco_ar.core.ui.components.miscellaneous.Spacer
import diploma.project.eco_ar.core.ui.components.textField.TextField
import diploma.project.eco_ar.core.ui.components.topBar.TopBar
import diploma.project.eco_ar.core.ui.dialog.ImageSourceBottomDialog
import diploma.project.eco_ar.core.ui.theme.LocalColorTheme
import diploma.project.eco_ar.feature_profile.R
import diploma.project.eco_ar.feature_profile.ui.viewModel.profileEdit.ProfileEditAction
import diploma.project.eco_ar.feature_profile.ui.viewModel.profileEdit.ProfileEditUiAction
import diploma.project.eco_ar.feature_profile.ui.viewModel.profileEdit.ProfileEditUiState

@Composable
fun ProfileEditScreen(
    uiState: ProfileEditUiState,
    onAction: (ProfileEditAction) -> Unit,
    onUiAction: (ProfileEditUiAction) -> Unit
) {
    val context = LocalContext.current
    val colorTheme = LocalColorTheme.current

    var isImageSourceBottomDialogShown by remember { mutableStateOf(false) }

    ColumnScreen(
        padding = PaddingValues(horizontal = 20.dp),
        clearFocusOnClick = true
    ) {
        TopBar(
            title = stringResource(R.string.profile_editing),
            endContent = {
                VectorButton(
                    vectorResId = diploma.project.eco_ar.core.R.drawable.icon_check,
                    onClick = {
                        onAction(ProfileEditAction.Save(context))
                    }
                )
            },
            onBackPressed = {
                onUiAction(ProfileEditUiAction.NavigateBack)
            }
        )
        Spacer(32.dp)
        if (uiState.photoUri == null) {
            Image(
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .background(colorTheme.colorLightGray)
                    .clickable(
                        onClick = {
                            isImageSourceBottomDialogShown = true
                        }
                    )
                    .padding(52.dp),
                imageVector = ImageVector.vectorResource(diploma.project.eco_ar.core.R.drawable.icon_add_photo_gallery),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                colorFilter = ColorFilter.tint(colorTheme.colorGreenMain, BlendMode.SrcIn)
            )
        } else {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(
                        onClick = {
                            isImageSourceBottomDialogShown = true
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    modifier = Modifier.size(200.dp),
                    model = uiState.photoUri,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Image(
                    modifier = Modifier.size(96.dp),
                    imageVector = ImageVector.vectorResource(diploma.project.eco_ar.core.R.drawable.icon_add_photo_gallery),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    colorFilter = ColorFilter.tint(colorTheme.colorGreenMain, BlendMode.SrcIn)
                )
            }
        }
        Spacer(12.dp)
        TextField(
            modifier = Modifier.fillMaxWidth(),
            text = uiState.name,
            onTextChanged = { newName ->
                onAction(ProfileEditAction.OnNameTextChanged(newName))
            },
            error = uiState.nameValidationError,
            labelText = stringResource(R.string.name),
            leadingIconResId = diploma.project.eco_ar.core.R.drawable.icon_person
        )
        Spacer(12.dp)
        TextField(
            modifier = Modifier.fillMaxWidth(),
            text = uiState.email,
            onTextChanged = { newEmail ->
                onAction(ProfileEditAction.OnEmailTextChanged(newEmail))
            },
            error = uiState.emailValidationError,
            labelText = stringResource(R.string.electronic_mail),
            leadingIconResId = diploma.project.eco_ar.core.R.drawable.icon_mail
        )
        Spacer(12.dp)
        TextField(
            modifier = Modifier.fillMaxWidth(),
            text = uiState.password,
            onTextChanged = { newPassword ->
                onAction(ProfileEditAction.OnPasswordTextChanged(newPassword))
            },
            error = uiState.passwordValidationError,
            labelText = stringResource(R.string.password),
            leadingIconResId = diploma.project.eco_ar.core.R.drawable.icon_lock
        )
        Spacer(12.dp)
        TextField(
            modifier = Modifier.fillMaxWidth(),
            text = uiState.repeatedPassword,
            onTextChanged = { newRepeatedPassword ->
                onAction(ProfileEditAction.OnRepeatedPasswordTextChanged(newRepeatedPassword))
            },
            error = uiState.repeatedPasswordValidationError,
            labelText = stringResource(R.string.repeat_password),
            leadingIconResId = diploma.project.eco_ar.core.R.drawable.icon_lock
        )
    }

    ImageSourceBottomDialog(
        isShown = isImageSourceBottomDialogShown,
        onDismiss = {
            isImageSourceBottomDialogShown = false
        },
        onPictureSelected = { uri ->
            onAction(ProfileEditAction.OnNewProfilePictureSelected(uri))
        }
    )
}