package diploma.project.eco_ar.ui.activities

import android.Manifest
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import diploma.project.eco_ar.core.ui.permissions.rememberAndroidPermission
import diploma.project.eco_ar.core.ui.snackbar.LocalSnackbarState
import diploma.project.eco_ar.core.ui.snackbar.SnackbarState
import diploma.project.eco_ar.core.ui.theme.ColorTheme
import diploma.project.eco_ar.core.ui.theme.LocalColorTheme
import diploma.project.eco_ar.ui.navigation.NavGraph

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                scrim = Color.TRANSPARENT,
                darkScrim = Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(
                scrim = Color.TRANSPARENT,
                darkScrim = Color.TRANSPARENT
            )
        )

        setContent {
            val colorTheme = remember { ColorTheme() }
            val snackbarState = remember { SnackbarState() }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val permission = rememberAndroidPermission(Manifest.permission.POST_NOTIFICATIONS)

                LaunchedEffect(permission) {
                    if (!permission.isGranted) {
                        permission.askForPermission()
                    }
                }
            }

            CompositionLocalProvider(
                LocalColorTheme provides colorTheme,
                LocalSnackbarState provides snackbarState
            ) {
                NavGraph()
            }
        }
    }
}