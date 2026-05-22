package diploma.project.eco_ar.core.ui.navigation

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import diploma.project.eco_ar.core.ui.navigation.nested.NestedRoute
import diploma.project.eco_ar.core.ui.navigation.nested.NestedRoutesBackStack
import diploma.project.eco_ar.core.ui.navigation.routes.MainRoutes

private val defaultContentTransform = ContentTransform(
    targetContentEnter = fadeIn(),
    initialContentExit = fadeOut()
)

private val predictiveContentTransform = ContentTransform(
    targetContentEnter = fadeIn(),
    initialContentExit = fadeOut() + scaleOut(targetScale = 0.75f)
)

@Composable
fun TopRoutesGraphsNavDisplay(
    modifier: Modifier = Modifier,
    backStack: NestedRoutesBackStack,
    entryProvider: (key: NavKey) -> NavEntry<NavKey>,
) {
    val mainRoutes = remember { MainRoutes.getMainRoutes() }
    val currentTopRoute = backStack.topRoutes.last()

    val onBack = remember(backStack.topRoutes.size, currentTopRoute) {
        if (backStack.topRoutes.size > 1 && currentTopRoute in mainRoutes) {
            { backStack.push(currentTopRoute, index = 0) }
        } else {
            { backStack.pop() }
        }
    }

    NavDisplay(
        modifier = modifier.fillMaxSize(),
        backStack = backStack.topRoutes,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        transitionSpec = { defaultContentTransform },
        popTransitionSpec = { defaultContentTransform },
        predictivePopTransitionSpec = { predictiveContentTransform },
        onBack = onBack,
        entryProvider = entryProvider
    )
}

@Composable
fun ChildrenNavDisplay(
    modifier: Modifier = Modifier,
    backStack: NestedRoutesBackStack,
    parent: NestedRoute,
    entryProvider: (key: NavKey) -> NavEntry<NavKey>,
) {
    if (!parent.children.isNullOrEmpty()) {
        NavDisplay(
            modifier = modifier.fillMaxSize(),
            backStack = parent.children,
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            transitionSpec = { defaultContentTransform },
            popTransitionSpec = { defaultContentTransform },
            predictivePopTransitionSpec = { predictiveContentTransform },
            onBack = { backStack.pop(parent) },
            entryProvider = entryProvider
        )
    }
}