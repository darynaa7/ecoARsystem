package diploma.project.eco_ar.core.ui.components.textField

import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import diploma.project.eco_ar.core.domain.validation.ValidationError
import diploma.project.eco_ar.core.ui.modifier.shake
import diploma.project.eco_ar.core.ui.theme.getTextFieldColors
import diploma.project.eco_ar.core.ui.theme.interTextStyle
import kotlinx.coroutines.launch

@Composable
fun TextField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChanged: ((String) -> Unit)? = null,
    error: ValidationError? = null,
    maxLines: Int,
    labelText: String? = null,
    leadingIconResId: Int? = null, // Vector icon
    trailingIconResId: Int? = null // Vector icon
) {
    val coroutineScope = rememberCoroutineScope()

    val bringIntoViewRequester = remember { BringIntoViewRequester() }

    var previousText by remember { mutableStateOf("") }

    var currentError by remember(error) { mutableStateOf(error) }

    LaunchedEffect(Unit) {
        currentError = null
    }

    LaunchedEffect(maxLines, text) {
        if (maxLines > 1) {
            val wasLines = previousText.lines().size
            val nowLines = text.lines().size

            if (nowLines != wasLines) {
                bringIntoViewRequester.bringIntoView()
            }

            previousText = text
        }
    }

    TextField(
        modifier = modifier
            .shake(currentError)
            .bringIntoViewRequester(bringIntoViewRequester)
            .onFocusEvent { event ->
                if (event.isFocused) {
                    coroutineScope.launch {
                        bringIntoViewRequester.bringIntoView()
                    }
                }
            },
        value = text,
        onValueChange = { onTextChanged?.invoke(it) },
        textStyle = interTextStyle(16.sp, FontWeight.SemiBold),
        enabled = onTextChanged != null,
        maxLines = maxLines,
        singleLine = maxLines == 1,
        isError = currentError != null,
        colors = getTextFieldColors(),
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
        trailingIcon = trailingIconResId?.let {
            {
                Icon(
                    imageVector = ImageVector.vectorResource(it),
                    contentDescription = null
                )
            }
        }
    )
}

@Composable
fun TextField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChanged: ((String) -> Unit)? = null,
    error: ValidationError? = null,
    labelText: String? = null,
    leadingIconResId: Int? = null, // Vector icon
    trailingIconResId: Int? = null // Vector icon
) {
    TextField(
        modifier = modifier,
        text = text,
        onTextChanged = onTextChanged,
        error = error,
        labelText = labelText,
        maxLines = 1,
        leadingIconResId = leadingIconResId,
        trailingIconResId = trailingIconResId
    )
}