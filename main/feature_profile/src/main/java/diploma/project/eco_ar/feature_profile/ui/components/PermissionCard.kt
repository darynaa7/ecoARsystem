package diploma.project.eco_ar.feature_profile.ui.components

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import diploma.project.eco_ar.core.ui.components.buttons.ToggleButton
import diploma.project.eco_ar.core.ui.components.card.Card
import diploma.project.eco_ar.core.ui.permissions.AndroidPermission
import diploma.project.eco_ar.core.ui.permissions.rememberAndroidPermission
import diploma.project.eco_ar.core.ui.permissions.rememberAndroidPermissions
import diploma.project.eco_ar.core.ui.theme.LocalColorTheme
import diploma.project.eco_ar.core.ui.theme.robotoTextStyle
import diploma.project.eco_ar.feature_profile.R
import kotlinx.collections.immutable.persistentListOf

@Composable
fun PermissionCard(
    modifier: Modifier = Modifier,
    permissionName: String
) {
    val activity = LocalActivity.current
    val context = LocalContext.current
    val colorTheme = LocalColorTheme.current

    val permission = when (permissionName) {
        Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION -> rememberAndroidPermissions(
            names = persistentListOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
        else -> rememberAndroidPermission(permissionName)
    }

    val icon = when (permissionName) {
        Manifest.permission.CAMERA -> diploma.project.eco_ar.core.R.drawable.icon_camera
        Manifest.permission.ACCESS_COARSE_LOCATION -> diploma.project.eco_ar.core.R.drawable.icon_my_location
        Manifest.permission.ACCESS_FINE_LOCATION -> diploma.project.eco_ar.core.R.drawable.icon_my_location
        Manifest.permission.POST_NOTIFICATIONS -> diploma.project.eco_ar.core.R.drawable.icon_notifications
        else -> null
    }

    val name = when (permissionName) {
        Manifest.permission.CAMERA -> stringResource(R.string.camera)
        Manifest.permission.ACCESS_COARSE_LOCATION -> stringResource(R.string.geolocation)
        Manifest.permission.ACCESS_FINE_LOCATION -> stringResource(R.string.geolocation)
        Manifest.permission.POST_NOTIFICATIONS -> stringResource(R.string.Notifications)
        else -> null
    }
    val allowInSettingsText = name?.let {
        stringResource(R.string.please_allow_permission_in_settings, name)
    }
    val permissionAlreadyGrantedText = stringResource(R.string.permission_already_granted)

    var shouldShowRationale by remember {
        mutableStateOf(
            activity?.let { ActivityCompat.shouldShowRequestPermissionRationale(it, permissionName) } ?: false
        )
    }

    val isPermanentlyDenied = remember(context, permissionName, shouldShowRationale) {
        !shouldShowRationale && ContextCompat.checkSelfPermission(context, permissionName) != PackageManager.PERMISSION_GRANTED
    }

    LaunchedEffect(Unit) {
        permission.updateIsGranted(ContextCompat.checkSelfPermission(context, permissionName) == PackageManager.PERMISSION_GRANTED)
    }

    Card(
        modifier = modifier,
        onClick = {
            askForPermission(
                context = context,
                permission = permission,
                isPermanentlyDenied = isPermanentlyDenied,
                allowInSettingsText = allowInSettingsText,
                permissionAlreadyGrantedText = permissionAlreadyGrantedText
            )
        }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon?.let {
                Image(
                    modifier = Modifier.size(32.dp),
                    imageVector = ImageVector.vectorResource(icon),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    colorFilter = ColorFilter.tint(colorTheme.colorGreenMain, BlendMode.SrcIn)
                )
            }
            name?.let {
                Text(
                    modifier = Modifier.weight(1f),
                    text = name,
                    style = robotoTextStyle(20.sp),
                    color = colorTheme.colorTextDark
                )
                ToggleButton(
                    isToggled = permission.isGranted,
                    onToggleChange = {
                        askForPermission(
                            context = context,
                            permission = permission,
                            isPermanentlyDenied = isPermanentlyDenied,
                            allowInSettingsText = allowInSettingsText,
                            permissionAlreadyGrantedText = permissionAlreadyGrantedText
                        )
                    }
                )
            }
        }
    }
}

private fun askForPermission(
    context: Context,
    permission: AndroidPermission,
    isPermanentlyDenied: Boolean,
    allowInSettingsText: String?,
    permissionAlreadyGrantedText: String
) {
    if (!permission.isGranted) {
        if (isPermanentlyDenied) {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.fromParts("package", context.packageName, null)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

            context.startActivity(intent)

            allowInSettingsText?.let {
                Toast.makeText(context, allowInSettingsText, Toast.LENGTH_SHORT).show()
            }
        } else {
            permission.askForPermission()
        }
    } else {
        Toast.makeText(context, permissionAlreadyGrantedText, Toast.LENGTH_SHORT).show()
    }
}