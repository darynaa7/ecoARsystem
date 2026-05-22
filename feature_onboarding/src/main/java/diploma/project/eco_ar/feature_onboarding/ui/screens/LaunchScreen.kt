package diploma.project.eco_ar.feature_onboarding.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import diploma.project.eco_ar.core.ui.components.miscellaneous.ColumnScreen
import diploma.project.eco_ar.core.ui.theme.LocalColorTheme
import diploma.project.eco_ar.core.ui.theme.robotoTextStyle
import diploma.project.eco_ar.feature_onboarding.R

@Composable
fun LaunchScreen() {
    val colorTheme = LocalColorTheme.current

    ColumnScreen(
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
    ) {
        Text(
            text = stringResource(diploma.project.eco_ar.core.R.string.app_name),
            style = robotoTextStyle(80.sp, FontWeight.Bold),
            color = colorTheme.colorTextDark
        )
        Text(
            text = stringResource(R.string.monitor),
            style = robotoTextStyle(48.sp),
            color = colorTheme.colorTextDark
        )
    }
}