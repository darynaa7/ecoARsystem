package diploma.project.eco_ar.core.ui.navigation.routes

import diploma.project.eco_ar.core.ui.navigation.nested.NestedRoute
import kotlinx.serialization.Serializable

@Serializable
sealed interface AuthRoutes {
    @Serializable
    data object SignIn : NestedRoute()
    @Serializable
    data object SignUp : NestedRoute()
}