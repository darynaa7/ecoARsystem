package diploma.project.eco_ar.core.ui.components.buttons

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import diploma.project.eco_ar.core.ui.modifier.noIndicationClickable
import diploma.project.eco_ar.core.ui.theme.LocalColorTheme

@Composable
fun ToggleButton(
    modifier: Modifier = Modifier,
    isToggled: Boolean,
    onToggleChange: (Boolean) -> Unit
) {
    val density = LocalDensity.current
    val colorTheme = LocalColorTheme.current

    val toggleButtonSize = DpSize(44.dp, 24.dp)
    val circleSize = DpSize(24.dp, 24.dp)

    val backgroundColor by animateColorAsState(
        targetValue = if (isToggled) colorTheme.colorGreenMain else colorTheme.colorRedSecondary
    )

    val circleOffsetXTarget = rememberSaveable(isToggled) {
        if (isToggled) {
            with(density) { (toggleButtonSize.width - circleSize.width).toPx() }
        } else {
            0f
        }
    }
    val circleOffsetX by animateFloatAsState(
        targetValue = circleOffsetXTarget
    )

    Box(
        modifier = modifier
            .size(toggleButtonSize)
            .clip(RoundedCornerShape(50))
            .background(backgroundColor)
            .noIndicationClickable(
                onClick = {
                    onToggleChange(!isToggled)
                }
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .size(circleSize)
                .offset {
                    IntOffset(circleOffsetX.toInt(), 0)
                }
                .background(colorTheme.colorWhite, CircleShape)
                .border(2.dp, backgroundColor, CircleShape)
        )
    }
}