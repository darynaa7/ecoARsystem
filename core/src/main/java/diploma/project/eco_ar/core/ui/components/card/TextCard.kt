package diploma.project.eco_ar.core.ui.components.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import diploma.project.eco_ar.core.ui.theme.LocalColorTheme
import diploma.project.eco_ar.core.ui.theme.robotoTextStyle

@Composable
fun TextCard(
    modifier: Modifier = Modifier,
    text: String,
    iconRes: Int? = null,
    onClick: () -> Unit
) {
    val colorTheme = LocalColorTheme.current

    Card(
        modifier = modifier,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            iconRes?.let {
                Image(
                    modifier = Modifier.size(32.dp),
                    imageVector = ImageVector.vectorResource(iconRes),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    colorFilter = ColorFilter.tint(colorTheme.colorGreenMain, BlendMode.SrcIn)
                )
            }
            Text(
                text = text,
                style = robotoTextStyle(20.sp),
                color = colorTheme.colorTextDark
            )
        }
    }
}