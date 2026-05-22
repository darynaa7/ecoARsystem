package diploma.project.eco_ar.core.ui.permissions

import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import kotlinx.collections.immutable.ImmutableList

class AndroidPermission(
    isGranted: Boolean,
    var onGrantedCallback: (() -> Unit)? = null,
    var onNotGrantedCallback: (() -> Unit)? = null
) {
    var askForPermission: () -> Unit = {}

    var isGranted by mutableStateOf(isGranted)
        private set

    fun updateIsGranted(isGranted: Boolean) {
        if (isGranted) onGrantedCallback?.invoke()
        if (!isGranted) onNotGrantedCallback?.invoke()

        this.isGranted = isGranted
    }
}

@Composable
fun rememberAndroidPermission(
    name: String,
    onGrantedCallback: (() -> Unit)? = null,
    onNotGrantedCallback: (() -> Unit)? = null
): AndroidPermission {
    val context = LocalContext.current

    val permission = remember {
        AndroidPermission(
            isGranted = ContextCompat.checkSelfPermission(context, name) == PackageManager.PERMISSION_GRANTED,
            onGrantedCallback = onGrantedCallback,
            onNotGrantedCallback = onNotGrantedCallback
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        permission.updateIsGranted(isGranted)
    }

    SideEffect {
        permission.askForPermission = {
            launcher.launch(name)
        }
    }

    return permission
}

@Composable
fun rememberAndroidPermissions(
    names: ImmutableList<String>,
    onGrantedCallback: (() -> Unit)? = null,
    onNotGrantedCallback: (() -> Unit)? = null
): AndroidPermission {
    val context = LocalContext.current

    val permissions = remember {
        AndroidPermission(
            isGranted = names.all { name ->
                ContextCompat.checkSelfPermission(context, name) == PackageManager.PERMISSION_GRANTED
            },
            onGrantedCallback = onGrantedCallback,
            onNotGrantedCallback = onNotGrantedCallback
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { isGrantedMap: Map<String, Boolean> ->
        permissions.updateIsGranted(isGrantedMap.values.all { it })
    }

    SideEffect {
        permissions.askForPermission = {
            launcher.launch(names.toTypedArray())
        }
    }

    return permissions
}