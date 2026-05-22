package diploma.project.eco_ar.core.ui.theme

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

class ColorTheme {

    // Common

    val colorBlack = Color(0xFF000000)
    val colorWhite = Color(0xFFFFFFFF)
    val colorLightGray = Color(0xFFBBBBBB)

    // Main

    val colorGreenMain = Color(0xFF839581)

    val colorBlueMain = Color(0xFF258C8F)

    val colorRedMain = Color(0xFFCA1515)
    val colorRedSecondary = Color(0xFFE63C3C)

    val colorTextDark = Color(0xFF39554B)

    val colorBackground = Color(0xFFF9F8F8)

    val colorButtonVector = Color(0xFF1D1B20)
    val colorButtonDisabled = Color(0xFFC8C8C8)

    val colorDivider = Color(0xFFB8BEC9)

    val colorTemperatures = Color(0xFFFF5722)
    val colorHumidities = Color(0xFF2196F3)
    val colorPressures = Color(0xFF9C27B0)
    val colorWindSpeeds = Color(0xFF4CAF50)
    val colorWindDegrees = Color(0xFF009688)
    val colorAQIs = Color(0xFFFFC107)
    val colorPM2point5s = Color(0xFF795548)
    val colorPM10s = Color(0xFF607D8B)
    val colorNO2s = Color(0xFF8BC34A)
    val colorO3s = Color(0xFF03A9F4)
    val colorCOs = Color(0xFF424242)
}

val LocalColorTheme = compositionLocalOf { ColorTheme() }

// For Material3 components

@Composable
fun getButtonColors(): ButtonColors {
    val colorTheme = ColorTheme()

    return ButtonDefaults.buttonColors(
        containerColor = colorTheme.colorGreenMain,
        contentColor = colorTheme.colorWhite,
        disabledContainerColor = colorTheme.colorButtonDisabled,
        disabledContentColor = colorTheme.colorWhite
    )
}

@Composable
fun getTextFieldColors(): TextFieldColors {
    val colorTheme = LocalColorTheme.current

    return TextFieldDefaults.colors(
        focusedTextColor = colorTheme.colorTextDark,
        unfocusedTextColor = colorTheme.colorTextDark,
        disabledTextColor = colorTheme.colorTextDark,
        errorTextColor = colorTheme.colorTextDark,
        errorSupportingTextColor = colorTheme.colorRedMain,
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
        errorContainerColor = Color.Transparent,
        cursorColor = colorTheme.colorTextDark,
        errorCursorColor = colorTheme.colorRedMain,
        focusedIndicatorColor = colorTheme.colorBlueMain,
        unfocusedIndicatorColor = colorTheme.colorDivider,
        disabledIndicatorColor = colorTheme.colorDivider,
        errorIndicatorColor = colorTheme.colorRedMain,
        focusedLeadingIconColor = colorTheme.colorBlueMain,
        unfocusedLeadingIconColor = colorTheme.colorTextDark,
        errorLeadingIconColor = colorTheme.colorRedMain,
        disabledLeadingIconColor = colorTheme.colorTextDark,
        focusedTrailingIconColor = colorTheme.colorBlueMain,
        unfocusedTrailingIconColor = colorTheme.colorTextDark,
        errorTrailingIconColor = colorTheme.colorRedMain,
        disabledTrailingIconColor = colorTheme.colorTextDark,
        focusedLabelColor = colorTheme.colorTextDark.copy(0.5f),
        unfocusedLabelColor = colorTheme.colorTextDark.copy(0.5f),
        disabledLabelColor = colorTheme.colorTextDark.copy(0.5f),
        errorLabelColor = colorTheme.colorTextDark.copy(0.5f),
        focusedPlaceholderColor = colorTheme.colorTextDark.copy(0.5f),
        unfocusedPlaceholderColor = colorTheme.colorTextDark.copy(0.5f),
        disabledPlaceholderColor = colorTheme.colorTextDark.copy(0.5f),
        errorPlaceholderColor = colorTheme.colorTextDark.copy(0.5f),
    )
}

@Composable
fun getRadioButtonColors(): RadioButtonColors {
    val colorTheme = ColorTheme()

    return RadioButtonDefaults.colors(
        selectedColor = colorTheme.colorBlueMain,
        unselectedColor = colorTheme.colorTextDark
    )
}