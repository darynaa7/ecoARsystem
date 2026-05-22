package diploma.project.eco_ar.core.ui.navigation

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation3.ui.NavDisplay
import diploma.project.eco_ar.core.ui.navigation.routes.MainRoutes
import kotlin.reflect.KClass

fun getMainRouteTransitionSpecForMainRouteOne(): Map<String, Any> {
    return getMainRouteTransitionSpec(
        prevs = emptyList(),
        nexts = listOf(MainRoutes.RouteTwo::class, MainRoutes.RouteThree::class, MainRoutes.RouteFour::class)
    )
}

fun getMainRouteTransitionSpecForMainRouteTwo(): Map<String, Any> {
    return getMainRouteTransitionSpec(
        prevs = listOf(MainRoutes.RouteOne::class),
        nexts = listOf(MainRoutes.RouteThree::class, MainRoutes.RouteFour::class)
    )
}

fun getMainRouteTransitionSpecForMainRouteThree(): Map<String, Any> {
    return getMainRouteTransitionSpec(
        prevs = listOf(MainRoutes.RouteOne::class, MainRoutes.RouteTwo::class),
        nexts = listOf(MainRoutes.RouteFour::class)
    )
}

fun getMainRouteTransitionSpecForMainRouteFour(): Map<String, Any> {
    return getMainRouteTransitionSpec(
        prevs = listOf(MainRoutes.RouteOne::class, MainRoutes.RouteTwo::class, MainRoutes.RouteThree::class),
        nexts = emptyList()
    )
}

fun getMainRouteTransitionSpec(prevs: List<KClass<*>>, nexts: List<KClass<*>>): Map<String, Any> {
    val prevsNames = prevs.map { it.simpleName }
    val nextsNames = nexts.map { it.simpleName }

    return NavDisplay.transitionSpec {
        val key = initialState.key.toString().substringBefore("(")

        when {
            prevsNames.contains(key) -> {
                ContentTransform(
                    targetContentEnter = fadeIn() + slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth }
                    ),
                    initialContentExit = slideOutHorizontally(
                        targetOffsetX = { fullWidth -> -fullWidth }
                    )
                )
            }
            nextsNames.contains(key) -> {
                ContentTransform(
                    targetContentEnter = fadeIn() + slideInHorizontally(
                        initialOffsetX = { fullWidth -> -fullWidth }
                    ),
                    initialContentExit = slideOutHorizontally(
                        targetOffsetX = { fullWidth -> fullWidth }
                    )
                )
            }
            else -> null
        }
    } + NavDisplay.popTransitionSpec {
        val key = targetState.key.toString().substringBefore("(")

        when {
            prevsNames.contains(key) -> {
                ContentTransform(
                    targetContentEnter = fadeIn() + slideInHorizontally(
                        initialOffsetX = { fullWidth -> -fullWidth }
                    ),
                    initialContentExit = fadeOut() + slideOutHorizontally(
                        targetOffsetX = { fullWidth -> fullWidth }
                    )
                )
            }
            nextsNames.contains(key) -> {
                ContentTransform(
                    targetContentEnter = fadeIn() + slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth }
                    ),
                    initialContentExit = fadeOut() + slideOutHorizontally(
                        targetOffsetX = { fullWidth -> -fullWidth }
                    )
                )
            }
            else -> null
        }
    } + NavDisplay.predictivePopTransitionSpec {
        val key = targetState.key.toString().substringBefore("(")

        when {
            prevsNames.contains(key) -> {
                ContentTransform(
                    targetContentEnter = fadeIn() + slideInHorizontally(
                        initialOffsetX = { fullWidth -> -fullWidth }
                    ),
                    initialContentExit = fadeOut() + slideOutHorizontally(
                        targetOffsetX = { fullWidth -> fullWidth }
                    )
                )
            }
            nextsNames.contains(key) -> {
                ContentTransform(
                    targetContentEnter = fadeIn() + slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth }
                    ),
                    initialContentExit = fadeOut() + slideOutHorizontally(
                        targetOffsetX = { fullWidth -> -fullWidth }
                    )
                )
            }
            else -> null
        }
    }
}