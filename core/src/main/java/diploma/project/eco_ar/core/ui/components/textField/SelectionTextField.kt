package diploma.project.eco_ar.core.ui.components.textField

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import diploma.project.eco_ar.core.R
import diploma.project.eco_ar.core.domain.validation.ValidationError
import diploma.project.eco_ar.core.ui.components.buttons.RadioButton
import diploma.project.eco_ar.core.ui.components.miscellaneous.Spacer
import diploma.project.eco_ar.core.ui.dialog.BottomSheetDialog
import diploma.project.eco_ar.core.ui.modifier.noIndicationClickable
import diploma.project.eco_ar.core.ui.modifier.shake
import diploma.project.eco_ar.core.ui.theme.LocalColorTheme
import diploma.project.eco_ar.core.ui.theme.getTextFieldColors
import diploma.project.eco_ar.core.ui.theme.interTextStyle
import diploma.project.eco_ar.core.ui.theme.robotoTextStyle
import kotlinx.collections.immutable.ImmutableList

@Composable
fun <T> SelectionTextField(
    modifier: Modifier = Modifier,
    items: ImmutableList<T>,
    selectedItemIndex: Int,
    placeholderValue: String? = null,
    error: ValidationError? = null,
    labelText: String? = null,
    leadingIconResId: Int? = null, // Vector icon
    sheetDialogTitle: String,
    sheetDialogDescription: String,
    onItemSelected: (Int) -> Unit
) {
    val colorTheme = LocalColorTheme.current
    val focusManager = LocalFocusManager.current

    val interactionSource = remember { MutableInteractionSource() }

    var isSheetDialogShown by remember { mutableStateOf(false) }

    var currentError by remember(error) { mutableStateOf(error) }

    LaunchedEffect(Unit) {
        currentError = null
    }

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            if (interaction is PressInteraction.Release) {
                isSheetDialogShown = true
            }
        }
    }

    TextField(
        modifier = modifier
            .shake(currentError)
            .noIndicationClickable {
                isSheetDialogShown = true
            },
        value = if (placeholderValue == null) {
            items.getOrNull(selectedItemIndex)?.toString() ?: ""
        } else {
            (items.getOrNull(selectedItemIndex) ?: items.firstOrNull())?.toString()
                ?: placeholderValue
        },
        onValueChange = { },
        textStyle = interTextStyle(16.sp, FontWeight.SemiBold),
        singleLine = true,
        readOnly = true,
        interactionSource = interactionSource,
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
                    modifier = Modifier
                        .size(20.dp)
                        .noIndicationClickable(
                            onClick = {
                                isSheetDialogShown = true
                            }
                        ),
                    imageVector = ImageVector.vectorResource(it),
                    contentDescription = null
                )
            }
        },
        trailingIcon = {
            Icon(
                modifier = Modifier
                    .size(20.dp)
                    .noIndicationClickable(
                        onClick = {
                            isSheetDialogShown = true
                        }
                    ),
                imageVector = ImageVector.vectorResource(R.drawable.icon_arrow_drop_down),
                contentDescription = null
            )
        }
    )

    BottomSheetDialog(
        isShown = isSheetDialogShown,
        onDismissRequest = {
            isSheetDialogShown = false
            focusManager.clearFocus()
        }
    ) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = sheetDialogTitle,
                    style = interTextStyle(20.sp, FontWeight.Bold),
                    color = colorTheme.colorTextDark
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .noIndicationClickable(
                            onClick = {
                                isSheetDialogShown = false
                                focusManager.clearFocus()
                            }
                        ),
                    text = stringResource(R.string.cancel),
                    style = robotoTextStyle(14.sp, FontWeight.Bold),
                    color = colorTheme.colorBlueMain
                )
            }
            Spacer(32.dp)
            Text(
                modifier = Modifier.padding(horizontal = 20.dp),
                text = sheetDialogDescription,
                style = interTextStyle(14.sp),
                color = colorTheme.colorTextDark.copy(0.5f)
            )
            if (items.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(horizontal = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .background(colorTheme.colorWhite, CircleShape)
                            .padding(5.dp),
                        color = colorTheme.colorTextDark
                    )
                }
            } else {
                items.forEachIndexed { index, item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = ripple(color = colorTheme.colorTextDark.copy(0.5f)),
                                onClick = {
                                    onItemSelected(index)
                                    isSheetDialogShown = false
                                    focusManager.clearFocus()
                                }
                            )
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            isSelected = index == selectedItemIndex,
                            onClick = {
                                onItemSelected(index)
                                isSheetDialogShown = false
                                focusManager.clearFocus()
                            }
                        )
                        Text(
                            text = item.toString(),
                            style = interTextStyle(14.sp, FontWeight.Bold),
                            color = colorTheme.colorTextDark
                        )
                    }
                    if (index != items.size - 1) {
                        HorizontalDivider(color = colorTheme.colorDivider)
                    }
                }
            }
            Spacer(128.dp)
        }
    }
}