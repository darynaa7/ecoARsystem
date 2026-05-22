package diploma.project.eco_ar.feature_map.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import diploma.project.eco_ar.core.ui.theme.LocalColorTheme

@Composable
fun GoogleMapButton(
    modifier: Modifier = Modifier,
    iconResId: Int,
    onClick: () -> Unit
) {
    val colorTheme = LocalColorTheme.current

    Box(
        modifier = modifier
            .background(colorTheme.colorBackground.copy(0.9f))
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = ImageVector.vectorResource(iconResId),
            contentDescription = null,
            tint = colorTheme.colorTextDark
        )
    }
}
