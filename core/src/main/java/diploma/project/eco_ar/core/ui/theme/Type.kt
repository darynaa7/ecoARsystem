package diploma.project.eco_ar.core.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import diploma.project.eco_ar.core.R

val robotoFontFamily = FontFamily(
    Font(R.font.font_roboto_extra_light, FontWeight.ExtraLight),
    Font(R.font.font_roboto_light, FontWeight.Light),
    Font(R.font.font_roboto_regular, FontWeight.Normal),
    Font(R.font.font_roboto_medium, FontWeight.Medium),
    Font(R.font.font_roboto_semi_bold, FontWeight.SemiBold),
    Font(R.font.font_roboto_bold, FontWeight.Bold),
    Font(R.font.font_roboto_extra_bold, FontWeight.ExtraBold),
    Font(R.font.font_roboto_black, FontWeight.Black),
)

val interFontFamily = FontFamily(
    Font(R.font.font_inter_light, FontWeight.Light),
    Font(R.font.font_inter_regular, FontWeight.Normal),
    Font(R.font.font_inter_semi_bold, FontWeight.SemiBold),
    Font(R.font.font_inter_bold, FontWeight.Bold)
)

@Composable
fun robotoTextStyle(
    fontSize: TextUnit,
    fontWeight: FontWeight = FontWeight.Normal,
    lineHeight: TextUnit = (fontSize.value + 4).sp,
    letterSpacing: Float = 0f,
    shadow: Shadow? = null
): TextStyle {
    return remember(fontSize, lineHeight, fontWeight, letterSpacing) {
        TextStyle(
            fontSize = fontSize,
            lineHeight = lineHeight,
            letterSpacing = fontSize * letterSpacing,
            fontFamily = robotoFontFamily,
            fontWeight = fontWeight,
            shadow = shadow
        )
    }
}

@Composable
fun interTextStyle(
    fontSize: TextUnit,
    fontWeight: FontWeight = FontWeight.Normal,
    lineHeight: TextUnit = (fontSize.value + 4).sp,
    letterSpacing: Float = 0f,
    shadow: Shadow? = null
): TextStyle {
    return remember(fontSize, lineHeight, fontWeight, letterSpacing) {
        TextStyle(
            fontSize = fontSize,
            lineHeight = lineHeight,
            letterSpacing = fontSize * letterSpacing,
            fontFamily = interFontFamily,
            fontWeight = fontWeight,
            shadow = shadow
        )
    }
}