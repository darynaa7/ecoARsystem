package diploma.project.eco_ar.feature_onboarding.ui.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import diploma.project.eco_ar.core.ui.navigation.nested.NestedRoutesBackStack
import diploma.project.eco_ar.core.ui.navigation.routes.AuthRoutes
import diploma.project.eco_ar.core.ui.navigation.routes.OnboardingRoutes
import diploma.project.eco_ar.feature_onboarding.data.OnboardingManager
import diploma.project.eco_ar.feature_onboarding.ui.screens.LaunchScreen
import diploma.project.eco_ar.feature_onboarding.ui.screens.OnboardingScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

fun EntryProviderScope<NavKey>.addOnboardingRoutes(backStack: NestedRoutesBackStack) {
    entry<OnboardingRoutes.Launch> {
        val onboardingManager = koinInject<OnboardingManager>()

        LaunchedEffect(Unit) {
            onboardingManager.navigateAfterLaunch(backStack)
        }

        LaunchScreen()
    }
    entry<OnboardingRoutes.Onboarding> {
        val coroutineScope = rememberCoroutineScope()
        val onboardingManager = koinInject<OnboardingManager>()

        OnboardingScreen(
            onNavigateToSignInScreen = {
                coroutineScope.launch(Dispatchers.IO) {
                    onboardingManager.isFirstTimeInApp.set(false)
                }
                backStack.clearAndPush(AuthRoutes.SignIn)
            }
        )
    }
}