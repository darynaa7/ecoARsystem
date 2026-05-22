package diploma.project.eco_ar.core.ui.components.textField

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import diploma.project.eco_ar.core.R
import diploma.project.eco_ar.core.domain.validation.ValidationError
import diploma.project.eco_ar.core.ui.modifier.shake
import diploma.project.eco_ar.core.ui.theme.getTextFieldColors
import diploma.project.eco_ar.core.ui.theme.interTextStyle

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChanged: (String) -> Unit,
    error: ValidationError? = null,
    labelText: String? = null,
    leadingIconResId: Int? = null // Vector icon
) {
    var isHidden by remember { mutableStateOf(true) }

    var currentError by remember(error) { mutableStateOf(error) }

    LaunchedEffect(Unit) {
        currentError = null
    }

    TextField(
        modifier = modifier.shake(currentError),
        value = text,
        onValueChange = onTextChanged,
        textStyle = interTextStyle(16.sp, FontWeight.SemiBold),
        singleLine = true,
        isError = currentError != null,
        colors = getTextFieldColors(),
        visualTransformation = if (isHidden) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        label = {
            Text(
                text = labelText ?: ""
            )
        },
        supportingText = {
            Text(
                text = currentError?.message ?: ""
            )
        },
        leadingIcon = leadingIconResId?.let {
            {
                Icon(
                    imageVector = ImageVector.vectorResource(it),
                    contentDescription = null
                )
            }
        },
        trailingIcon = {
            IconButton(
                onClick = {
                    isHidden = !isHidden
                }
            ) {
                Icon(
                    imageVector = if (isHidden) {
                        ImageVector.vectorResource(R.drawable.icon_visibility_off)
                    } else {
                        ImageVector.vectorResource(R.drawable.icon_visibility_on)
                    },
                    contentDescription = null
                )
            }
        }
    )
}