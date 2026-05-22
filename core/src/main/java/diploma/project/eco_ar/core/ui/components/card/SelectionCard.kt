package diploma.project.eco_ar.core.ui.components.card

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import diploma.project.eco_ar.core.R
import diploma.project.eco_ar.core.ui.theme.LocalColorTheme
import diploma.project.eco_ar.core.ui.theme.robotoTextStyle
import kotlinx.collections.immutable.ImmutableList

@Composable
fun SelectionCard(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    values: ImmutableList<Pair<String, String>>,
    onSelected: (Int) -> Unit
) {
    val colorTheme = LocalColorTheme.current

    var isExpanded by remember { mutableStateOf(false) }

    BoxWithConstraints(
        modifier = modifier
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                isExpanded = true
            }
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(32.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.icon_radio_button_checked),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    colorFilter = ColorFilter.tint(colorTheme.colorGreenMain, BlendMode.SrcIn)
                )
                Crossfade(
                    modifier = Modifier.weight(1f),
                    targetState = values[selectedIndex].first
                ) { text ->
                    Text(
                        text = text,
                        style = robotoTextStyle(20.sp),
                        color = colorTheme.colorTextDark,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Image(
                    modifier = Modifier.size(32.dp),
                    imageVector = ImageVector.vectorResource(if (isExpanded) R.drawable.icon_arrow_drop_up else R.drawable.icon_arrow_drop_down),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    colorFilter = ColorFilter.tint(colorTheme.colorGreenMain, BlendMode.SrcIn)
                )
            }
        }
        DropdownMenu(
            modifier = Modifier.width(maxWidth),
            properties = PopupProperties(usePlatformDefaultWidth = false),
            expanded = isExpanded,
            containerColor = colorTheme.colorBackground,
            shape = RoundedCornerShape(16.dp),
            shadowElevation = 4.dp,
            onDismissRequest = {
                isExpanded = false
            }
        ) {
            values.forEachIndexed { index, item ->
                DropdownMenuItem(
                    modifier = Modifier.fillMaxWidth(),
                    text = {
                        Crossfade(
                            targetState = selectedIndex == index
                        ) { isSelected ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    modifier = Modifier.size(32.dp),
                                    imageVector = ImageVector.vectorResource(if (isSelected) R.drawable.icon_radio_button_checked else R.drawable.icon_radio_button_unchecked),
                                    contentDescription = null,
                                    contentScale = ContentScale.FillBounds,
                                    colorFilter = ColorFilter.tint(colorTheme.colorGreenMain, BlendMode.SrcIn)
                                )
                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = item.first,
                                        style = robotoTextStyle(20.sp),
                                        color = colorTheme.colorTextDark
                                    )
                                    if (item.second.isNotEmpty()) {
                                        Text(
                                            text = item.second,
                                            style = robotoTextStyle(16.sp),
                                            color = colorTheme.colorTextDark
                                        )
                                    }
                                }
                            }
                        }
                    },
                    onClick = {
                        onSelected(index)
                        isExpanded = false
                    }
                )
            }
        }
    }
}