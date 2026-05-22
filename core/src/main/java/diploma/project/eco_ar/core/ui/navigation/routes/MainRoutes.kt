package diploma.project.eco_ar.core.ui.navigation.routes

import androidx.compose.runtime.mutableStateListOf
import diploma.project.eco_ar.core.ui.navigation.nested.NestedRoute
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.Serializable

@Serializable
sealed interface MainRoutes {
    @Serializable
    data object RouteOne : NestedRoute(mutableStateListOf(Home)) {
        @Serializable
        data object Home : NestedRoute()
        @Serializable
        data object ARCamera : NestedRoute()
    }

    @Serializable
    data object RouteTwo : NestedRoute(mutableStateListOf(Map)) {
        @Serializable
        data object Map : NestedRoute()
    }

    @Serializable
    data object RouteThree : NestedRoute(mutableStateListOf(Statistics)) {
        @Serializable
        data object Statistics : NestedRoute()
    }

    @Serializable
    data object RouteFour : NestedRoute(mutableStateListOf(Profile)) {
        @Serializable
        data object Profile : NestedRoute()
        @Serializable
        data object ProfileEdit : NestedRoute()
        @Serializable
        data object Reports : NestedRoute()
        @Serializable
        data object Settings : NestedRoute()
        @Serializable
        data object Permissions : NestedRoute()
    }

    companion object {
        fun getMainRoutes(): ImmutableList<NestedRoute> {
            return persistentListOf(
                RouteOne,
                RouteTwo,
                RouteThree,
                RouteFour
            )
        }
    }
}