package diploma.project.eco_ar.feature_profile.ui.screens

import android.Manifest
import android.os.Build
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import diploma.project.eco_ar.core.ui.components.miscellaneous.ColumnScreen
import diploma.project.eco_ar.core.ui.components.miscellaneous.Spacer
import diploma.project.eco_ar.core.ui.components.topBar.TopBar
import diploma.project.eco_ar.feature_profile.R
import diploma.project.eco_ar.feature_profile.ui.components.PermissionCard

@Composable
fun PermissionsScreen(
    onNavigateBack: () -> Unit
) {
    ColumnScreen(
        padding = PaddingValues(horizontal = 20.dp)
    ) {
        TopBar(
            title = stringResource(R.string.permissions),
            onBackPressed = onNavigateBack
        )
        Spacer(32.dp)
        PermissionCard(
            modifier = Modifier.fillMaxWidth(),
            permissionName = Manifest.permission.CAMERA
        )
        Spacer(16.dp)
        PermissionCard(
            modifier = Modifier.fillMaxWidth(),
            permissionName = Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Spacer(16.dp)
            PermissionCard(
                modifier = Modifier.fillMaxWidth(),
                permissionName = Manifest.permission.POST_NOTIFICATIONS
            )
        }
    }
}