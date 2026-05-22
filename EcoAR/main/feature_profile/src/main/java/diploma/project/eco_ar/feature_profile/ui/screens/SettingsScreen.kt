package diploma.project.eco_ar.feature_profile.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import diploma.project.eco_ar.core.ui.components.buttons.VectorButton
import diploma.project.eco_ar.core.ui.components.card.TextCard
import diploma.project.eco_ar.core.ui.components.miscellaneous.ColumnScreen
import diploma.project.eco_ar.core.ui.components.miscellaneous.Spacer
import diploma.project.eco_ar.core.ui.components.topBar.TopBar
import diploma.project.eco_ar.feature_profile.R
import diploma.project.eco_ar.feature_profile.ui.viewModel.settings.SettingsAction
import diploma.project.eco_ar.feature_profile.ui.viewModel.settings.SettingsUiAction

@Composable
fun SettingsScreen(
    onAction: (SettingsAction) -> Unit,
    onUiAction: (SettingsUiAction) -> Unit
) {
    ColumnScreen(
        padding = PaddingValues(horizontal = 20.dp)
    ) {
        TopBar(
            title = stringResource(R.string.profile),
            endContent = {
                VectorButton(
                    vectorResId = diploma.project.eco_ar.core.R.drawable.icon_log_out,
                    onClick = {
                        onAction(SettingsAction.LogOut)
                    }
                )
            },
            onBackPressed = {
                onUiAction(SettingsUiAction.NavigateBack)
            }
        )
        Spacer(32.dp)
        TextCard(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.permissions),
            iconRes = diploma.project.eco_ar.core.R.drawable.icon_permissions,
            onClick = {
                onUiAction(SettingsUiAction.NavigateToPermissions)
            }
        )
    }
}