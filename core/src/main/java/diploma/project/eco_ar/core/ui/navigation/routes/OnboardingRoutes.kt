package diploma.project.eco_ar.core.ui.navigation.routes

import diploma.project.eco_ar.core.ui.navigation.nested.NestedRoute
import kotlinx.serialization.Serializable

@Serializable
sealed interface OnboardingRoutes {
    @Serializable
    data object Launch : NestedRoute()
    @Serializable
    data object Onboarding : NestedRoute()
}