package diploma.project.eco_ar.feature_auth.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import diploma.project.eco_ar.core.ui.components.buttons.TextButton
import diploma.project.eco_ar.core.ui.components.miscellaneous.ColumnScreen
import diploma.project.eco_ar.core.ui.components.miscellaneous.Spacer
import diploma.project.eco_ar.core.ui.components.miscellaneous.SpacerNavigationBar
import diploma.project.eco_ar.core.ui.components.textField.PasswordTextField
import diploma.project.eco_ar.core.ui.components.textField.TextField
import diploma.project.eco_ar.core.ui.components.topBar.TopBar
import diploma.project.eco_ar.core.ui.modifier.offsetWithIme
import diploma.project.eco_ar.core.ui.theme.LocalColorTheme
import diploma.project.eco_ar.core.ui.theme.robotoTextStyle
import diploma.project.eco_ar.feature_auth.R
import diploma.project.eco_ar.feature_auth.ui.viewModel.signIn.SignInAction
import diploma.project.eco_ar.feature_auth.ui.viewModel.signIn.SignInUiAction
import diploma.project.eco_ar.feature_auth.ui.viewModel.signIn.SignInUiState

@Composable
fun SignInScreen(
    uiState: SignInUiState,
    onAction: (SignInAction) -> Unit,
    onUiAction: (SignInUiAction) -> Unit
) {
    val colorTheme = LocalColorTheme.current

    ColumnScreen(
        clearFocusOnClick = true,
        padding = PaddingValues(horizontal = 20.dp)
    ) {
        TopBar(
            title = stringResource(R.string.login)
        )
        Spacer(64.dp)
        TextField(
            modifier = Modifier.fillMaxWidth(),
            text = uiState.username,
            error = uiState.usernameValidationError,
            onTextChanged = { username ->
                onAction(SignInAction.OnNameTextChanged(username))
            },
            labelText = stringResource(R.string.name),
            leadingIconResId = diploma.project.eco_ar.core.R.drawable.icon_mail
        )
        PasswordTextField(
            modifier = Modifier.fillMaxWidth(),
            text = uiState.password,
            error = uiState.passwordValidationError,
            onTextChanged = { password ->
                onAction(SignInAction.OnPasswordTextChanged(password))
            },
            labelText = stringResource(R.string.password),
            leadingIconResId = diploma.project.eco_ar.core.R.drawable.icon_lock
        )
        Spacer(1f)
        TextButton(
            modifier = Modifier
                .fillMaxWidth()
                .offsetWithIme(),
            text = stringResource(R.string.sign_in),
            onClick = {
                onAction(SignInAction.SignIn)
            }
        )
        Spacer(24.dp)
        Text(
            text = buildAnnotatedString {
                val boldStyle = SpanStyle(
                    fontWeight = FontWeight.ExtraBold
                )

                append(stringResource(R.string.dont_have_an_account))
                append(" ")

                withLink(
                    LinkAnnotation.Clickable(
                        tag = "ToSignUpClick",
                        styles = TextLinkStyles(style = boldStyle),
                        linkInteractionListener = {
                            onUiAction(SignInUiAction.NavigateToSignUp)
                        }
                    )
                ) {
                    append(stringResource(R.string.registration))
                }
            },
            style = robotoTextStyle(20.sp),
            color = colorTheme.colorTextDark
        )
        Spacer(40.dp)
        SpacerNavigationBar()
    }
}