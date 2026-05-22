package diploma.project.eco_ar.feature_auth.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import diploma.project.eco_ar.core.ui.components.buttons.TextButton
import diploma.project.eco_ar.core.ui.components.miscellaneous.ColumnScreen
import diploma.project.eco_ar.core.ui.components.miscellaneous.Spacer
import diploma.project.eco_ar.core.ui.components.miscellaneous.SpacerNavigationBar
import diploma.project.eco_ar.core.ui.components.textField.TextField
import diploma.project.eco_ar.core.ui.components.topBar.TopBar
import diploma.project.eco_ar.feature_auth.R
import diploma.project.eco_ar.feature_auth.ui.viewModel.signUp.SignUpAction
import diploma.project.eco_ar.feature_auth.ui.viewModel.signUp.SignUpUiAction
import diploma.project.eco_ar.feature_auth.ui.viewModel.signUp.SignUpUiState

@Composable
fun SignUpScreen(
    uiState: SignUpUiState,
    onAction: (SignUpAction) -> Unit,
    onUiAction: (SignUpUiAction) -> Unit
) {
    ColumnScreen(
        clearFocusOnClick = true,
        padding = PaddingValues(horizontal = 20.dp)
    ) {
        TopBar(
            title = stringResource(R.string.registration),
            onBackPressed = {
                onUiAction(SignUpUiAction.NavigateBack)
            }
        )
        Spacer(64.dp)
        TextField(
            modifier = Modifier.fillMaxWidth(),
            text = uiState.name,
            onTextChanged = { firstName ->
                onAction(SignUpAction.OnNameTextChanged(firstName))
            },
            labelText = stringResource(R.string.name),
            leadingIconResId = diploma.project.eco_ar.core.R.drawable.icon_person
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            text = uiState.email,
            error = uiState.emailValidationError,
            onTextChanged = { email ->
                onAction(SignUpAction.OnEmailTextChanged(email))
            },
            labelText = stringResource(R.string.email),
            leadingIconResId = diploma.project.eco_ar.core.R.drawable.icon_mail
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            text = uiState.password,
            error = uiState.passwordValidationError,
            onTextChanged = { password ->
                onAction(SignUpAction.OnPasswordTextChanged(password))
            },
            labelText = stringResource(R.string.password),
            leadingIconResId = diploma.project.eco_ar.core.R.drawable.icon_lock
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            text = uiState.repeatedPassword,
            error = uiState.repeatedPasswordValidationError,
            onTextChanged = { repeatedPassword ->
                onAction(SignUpAction.OnRepeatedPasswordTextChanged(repeatedPassword))
            },
            labelText = stringResource(R.string.approve_password),
            leadingIconResId = diploma.project.eco_ar.core.R.drawable.icon_lock
        )
        Spacer(1f)
        TextButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.create_profile),
            enabled = uiState.isSignUpButtonEnabled,
            onClick = {
                onAction(SignUpAction.SignUp)
            }
        )
        Spacer(40.dp)
        SpacerNavigationBar()
    }
}