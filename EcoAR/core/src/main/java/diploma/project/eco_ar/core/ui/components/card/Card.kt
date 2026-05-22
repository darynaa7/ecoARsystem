package diploma.project.eco_ar.core.ui.components.card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import diploma.project.eco_ar.core.ui.modifier.shadow
import diploma.project.eco_ar.core.ui.theme.LocalColorTheme

@Composable
fun Card(
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    backgroundColor: Color = LocalColorTheme.current.colorBackground,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .shadow(RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .clickable(
                enabled = onClick != null,
                onClick = {
                    onClick?.invoke()
                }
            )
            .padding(16.dp),
        horizontalAlignment = horizontalAlignment,
        content = content
    )
}