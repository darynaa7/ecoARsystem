package diploma.project.eco_ar.core.ui.modifier

import android.annotation.SuppressLint
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import diploma.project.eco_ar.core.ui.theme.LocalColorTheme

fun Modifier.shadow(
    shape: Shape,
    color: Color,
    offset: DpOffset = DpOffset(0.dp, 2.dp),
    radius: Dp = 4.dp,
    spread: Dp = 0.dp
): Modifier {
    return this.dropShadow(
        shape = shape,
        shadow = Shadow(
            color = color,
            offset = offset,
            radius = radius,
            spread = spread
        )
    )
}

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.shadow(
    shape: Shape,
    offset: DpOffset = DpOffset(0.dp, 2.dp),
    radius: Dp = 4.dp,
    spread: Dp = 0.dp
): Modifier = composed {
    val colorTheme = LocalColorTheme.current

    this.dropShadow(
        shape = shape,
        shadow = Shadow(
            color = colorTheme.colorBlack.copy(0.25f),
            offset = offset,
            radius = radius,
            spread = spread
        )
    )
}