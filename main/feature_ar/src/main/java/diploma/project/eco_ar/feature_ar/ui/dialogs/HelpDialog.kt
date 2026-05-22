package diploma.project.eco_ar.feature_ar.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import diploma.project.eco_ar.core.domain.model.AirQuality
import diploma.project.eco_ar.core.ui.theme.LocalColorTheme
import diploma.project.eco_ar.core.ui.theme.robotoTextStyle
import diploma.project.eco_ar.feature_ar.R

@Composable
fun HelpDialog(
    isShown: Boolean,
    onDismiss: () -> Unit
) {
    if (!isShown) return

    val context = LocalContext.current
    val colorTheme = LocalColorTheme.current

    Dialog(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorTheme.colorBackground, RoundedCornerShape(16.dp))
                .padding(20.dp)
                .clickable(
                    interactionSource = null,
                    indication = null,
                    onClick = onDismiss
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(R.string.reference),
                style = robotoTextStyle(28.sp),
                color = colorTheme.colorTextDark
            )
            Text(
                text = stringResource(R.string.reference_desc_1),
                style = robotoTextStyle(16.sp),
                color = colorTheme.colorTextDark
            )
            AirQuality.entries.forEach {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(it.color, CircleShape)
                    )
                    Text(
                        text = it.toString(context),
                        style = robotoTextStyle(20.sp),
                        color = colorTheme.colorTextDark
                    )
                }
            }
            Text(
                text = stringResource(R.string.reference_desc_2),
                style = robotoTextStyle(16.sp),
                color = colorTheme.colorTextDark
            )
        }
    }
}