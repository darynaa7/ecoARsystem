package diploma.project.eco_ar.core.ui.components.buttons

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import diploma.project.eco_ar.core.ui.theme.getRadioButtonColors

// Copied from Material3 code because I wanted to retain functionality, but needed to change border from 2.dp to 1.dp
@Composable
fun RadioButton(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    onClick: (() -> Unit)?
) {
    val interactionSource: MutableInteractionSource? = remember { null }
    val enabled = remember { true }
    val colors = getRadioButtonColors()

    val dotRadius = animateDpAsState(
        targetValue = if (isSelected) 7.5.dp else 0.dp,
        animationSpec = spring(dampingRatio = 1f, stiffness = 3800.0f)
    )
    val radioColor = colors.radioColor(enabled, isSelected)

    Canvas(
        modifier = modifier
            .then(
                if (onClick != null) {
                    Modifier.selectable(
                        selected = isSelected,
                        onClick = onClick,
                        enabled = enabled,
                        role = Role.RadioButton,
                        interactionSource = interactionSource,
                        indication = ripple(bounded = false, radius = 20.dp),)
                } else {
                    Modifier
                }
            )
            .wrapContentSize(Alignment.Center)
            .requiredSize(24.dp)
            .padding(2.dp)
    ) {
        val strokeWidth = 1.5.dp.toPx()

        drawCircle(
            color = radioColor.value,
            radius = 11.dp.toPx() - strokeWidth / 2,
            style = Stroke(strokeWidth)
        )
        if (dotRadius.value > 0.dp) {
            drawCircle(
                color = radioColor.value,
                radius = dotRadius.value.toPx() - strokeWidth / 2,
                style = Fill
            )
        }
    }
}

@Composable
private fun RadioButtonColors.radioColor(enabled: Boolean, selected: Boolean): State<Color> {
    val target = when {
        enabled && selected -> selectedColor
        enabled && !selected -> unselectedColor
        !enabled && selected -> disabledSelectedColor
        else -> disabledUnselectedColor
    }

    return if (enabled) {
        animateColorAsState(target, spring(dampingRatio = 1f, stiffness = 1600.0f))
    } else {
        rememberUpdatedState(target)
    }
}