package diploma.project.eco_ar.core.ui.components.buttons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import diploma.project.eco_ar.core.ui.theme.LocalColorTheme

@Composable
fun VectorButton(
    modifier: Modifier = Modifier,
    vectorResId: Int,
    tint: Color = LocalColorTheme.current.colorButtonVector,
    background: Color = Color.Transparent,
    innerPadding: Dp = 0.dp,
    onClick: () -> Unit
) {
    val colorTheme = LocalColorTheme.current

    Image(
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(background)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(
                    color = colorTheme.colorButtonVector.copy(0.5f)
                ),
                onClick = onClick
            )
            .padding(4.dp)
            .padding(innerPadding),
        imageVector = ImageVector.vectorResource(vectorResId),
        contentDescription = null,
        colorFilter = ColorFilter.tint(tint)
    )
}