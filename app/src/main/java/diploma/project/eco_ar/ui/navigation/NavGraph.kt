package diploma.project.eco_ar.ui.navigation

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation3.runtime.entryProvider
import diploma.project.eco_ar.core.ui.components.bottomNavBar.BottomNavBar
import diploma.project.eco_ar.core.ui.components.miscellaneous.CircularLoader
import diploma.project.eco_ar.core.ui.components.miscellaneous.LocalCircularLoaderState
import diploma.project.eco_ar.core.ui.navigation.TopRoutesGraphsNavDisplay
import diploma.project.eco_ar.core.ui.navigation.nested.rememberNestedRoutesBackStack
import diploma.project.eco_ar.core.ui.navigation.routes.OnboardingRoutes
import diploma.project.eco_ar.core.ui.snackbar.Snackbar
import diploma.project.eco_ar.core.ui.theme.LocalColorTheme
import diploma.project.eco_ar.feature_auth.ui.navigation.addAuthRoutes
import diploma.project.eco_ar.feature_onboarding.ui.navigation.addOnboardingRoutes
import diploma.project.eco_ar.main.ui.navigation.addMainRoutes

@SuppressLint("LocalContextGetResourceValueCall")
@Composable
fun NavGraph() {
    val colorTheme = LocalColorTheme.current
    val focusManager = LocalFocusManager.current
    val circularLoaderState = LocalCircularLoaderState.current

    val backStack = rememberNestedRoutesBackStack(OnboardingRoutes.Launch)
    val currentTopRoute = backStack.topRoutes.lastOrNull()
    val currentRoute = currentTopRoute?.children?.lastOrNull()

    val isKeyboardVisible = WindowInsets.ime.getBottom(density = LocalDensity.current) > 0

    LaunchedEffect(currentTopRoute, currentRoute) {
        if (currentTopRoute != null) circularLoaderState.hide()
    }

    LaunchedEffect(isKeyboardVisible) {
        if (!isKeyboardVisible) focusManager.clearFocus()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        TopRoutesGraphsNavDisplay(
            modifier = Modifier
                .fillMaxSize()
                .background(colorTheme.colorBackground),
            backStack = backStack,
            entryProvider = entryProvider {
                addAuthRoutes(backStack)
                addMainRoutes(backStack)
                addOnboardingRoutes(backStack)
            }
        )

        BottomNavBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            backStack = backStack
        )

        Snackbar(
            modifier = Modifier.align(Alignment.TopCenter)
        )

        CircularLoader()
    }

    BackHandler(isKeyboardVisible) {
        focusManager.clearFocus()
    }
}