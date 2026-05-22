package diploma.project.eco_ar.feature_onboarding.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import diploma.project.eco_ar.core.data.common.dataStore.BooleanDataStoreValue
import diploma.project.eco_ar.core.ui.navigation.nested.NestedRoutesBackStack
import diploma.project.eco_ar.core.ui.navigation.routes.AuthRoutes
import diploma.project.eco_ar.core.ui.navigation.routes.MainRoutes
import diploma.project.eco_ar.core.ui.navigation.routes.OnboardingRoutes
import diploma.project.eco_ar.feature_onboarding.domain.UserAuthChecker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OnboardingManager(
    dataStore: DataStore<Preferences>,
    private val userAuthChecker: UserAuthChecker
) {
    val isFirstTimeInApp = BooleanDataStoreValue(dataStore, true, "isFirstTimeInAppKey")

    suspend fun navigateAfterLaunch(backStack: NestedRoutesBackStack) = withContext(Dispatchers.Main) {
        when {
            isFirstTimeInApp.now() -> backStack.clearAndPush(OnboardingRoutes.Onboarding)
            else -> {
                when (userAuthChecker.isUserAuthorized()) {
                    false -> backStack.clearAndPush(AuthRoutes.SignIn)
                    true -> backStack.clearAndPush(MainRoutes.RouteOne)
                }
            }
        }
    }
}