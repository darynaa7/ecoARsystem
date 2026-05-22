package diploma.project.eco_ar.feature_onboarding.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import diploma.project.eco_ar.core.ui.components.buttons.TextButton
import diploma.project.eco_ar.core.ui.components.miscellaneous.ColumnScreen
import diploma.project.eco_ar.core.ui.components.miscellaneous.Spacer
import diploma.project.eco_ar.core.ui.components.miscellaneous.SpacerNavigationBar
import diploma.project.eco_ar.core.ui.theme.LocalColorTheme
import diploma.project.eco_ar.core.ui.theme.robotoTextStyle
import diploma.project.eco_ar.feature_onboarding.R

@Composable
fun OnboardingScreen(
    onNavigateToSignInScreen: () -> Unit
) {
    val colorTheme = LocalColorTheme.current

    ColumnScreen(
        padding = PaddingValues(horizontal = 20.dp)
    ) {
        Spacer(200.dp)
        Text(
            text = stringResource(diploma.project.eco_ar.core.R.string.app_name),
            style = robotoTextStyle(80.sp, FontWeight.Bold),
            color = colorTheme.colorTextDark
        )
        Spacer(16.dp)
        Text(
            text = stringResource(R.string.monitor),
            style = robotoTextStyle(48.sp),
            color = colorTheme.colorTextDark
        )
        Spacer(90.dp)
        Text(
            text = stringResource(R.string.ecological_monitoring_in_real_time),
            style = robotoTextStyle(32.sp),
            color = colorTheme.colorTextDark
        )
        Spacer(1f)
        TextButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.start),
            onClick = onNavigateToSignInScreen
        )
        Spacer(40.dp)
        SpacerNavigationBar()
    }
}