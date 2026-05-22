package diploma.project.eco_ar.core.ui.components.textField

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import diploma.project.eco_ar.core.R
import diploma.project.eco_ar.core.ui.modifier.shadow
import diploma.project.eco_ar.core.ui.theme.LocalColorTheme
import diploma.project.eco_ar.core.ui.theme.interTextStyle

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    text: String,
    shape: Shape = RoundedCornerShape(12.dp),
    onTextChanged: (String) -> Unit
) {
    val colorTheme = LocalColorTheme.current

    BasicTextField(
        modifier = modifier,
        value = text,
        onValueChange = onTextChanged,
        textStyle = interTextStyle(20.sp, FontWeight.SemiBold).copy(
            color = colorTheme.colorTextDark
        ),
        cursorBrush = SolidColor(colorTheme.colorTextDark),
        singleLine = true
    ) { innerTextField ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(shape)
                .clip(shape)
                .background(colorTheme.colorWhite)
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(20.dp),
                imageVector = ImageVector.vectorResource(R.drawable.icon_search),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                colorFilter = ColorFilter.tint(colorTheme.colorTextDark.copy(0.5f))
            )
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterStart
            ) {
                if (text.isBlank()) {
                    Text(
                        text = stringResource(R.string.search),
                        style = interTextStyle(20.sp),
                        color = colorTheme.colorTextDark.copy(0.5f)
                    )
                }
                innerTextField()
            }
        }
    }
}