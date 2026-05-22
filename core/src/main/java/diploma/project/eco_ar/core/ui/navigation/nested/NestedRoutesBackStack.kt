package diploma.project.eco_ar.core.ui.navigation.nested

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.Snapshot

@Stable
class NestedRoutesBackStack(startRoute: NestedRoute) {

    private val _rootRoute = object : NestedRoute(mutableStateListOf(startRoute)) {}
    val topRoutes: List<NestedRoute> get() = _rootRoute.children ?: emptyList()

    fun push(route: NestedRoute, parent: NestedRoute? = null, index: Int? = null) {
        instantly {
            parent(parent).pushChild(route, index)
        }
    }

    fun clearAndPush(route: NestedRoute, parent: NestedRoute? = null) {
        instantly {
            parent(parent).clearStack(true)
            parent(parent).pushChild(route)
        }
    }

    fun pop(parent: NestedRoute? = null) {
        instantly {
            parent(parent).popChild()
        }
    }

    fun clearStack(parent: NestedRoute? = null) {
        instantly {
            parent(parent).clearStack()
        }
    }

    fun parent(parent: NestedRoute? = null): NestedRoute {
        return parent ?: _rootRoute
    }

    fun instantly(block: NestedRoutesBackStack.() -> Unit) {
        Snapshot.withMutableSnapshot {
            this.block()
        }
    }

    private fun NestedRoute.track(): NestedRoute {
        return children?.lastOrNull()?.track() ?: this
    }

    // index == null -> moves to end
    private fun NestedRoute.pushChild(child: NestedRoute, index: Int? = null) {
        children?.let { children ->
            val childIndex = children.indexOf(child)

            if (childIndex != -1) {
                if (index == null) {
                    children.add(children.removeAt(childIndex))
                } else if (childIndex != index) {
                    children.add(index, children.removeAt(childIndex))
                }
            } else {
                if (index == null) {
                    children.add(child)
                } else {
                    children.add(index, child)
                }
            }
        }
    }

    private fun NestedRoute.popChild() {
        children?.removeLastOrNull()?.clearStack()
    }

    private fun NestedRoute.clearStack(clearFirst: Boolean = false) {
        children?.let { children ->
            val range = if (clearFirst) {
                children.size - 1 downTo 0
            } else {
                children.size - 1 downTo 1
            }
            for (index in range) {
                val removedChild = children.removeAt(index)
                removedChild.clearStack()
            }
        }
    }
}

@Composable
fun rememberNestedRoutesBackStack(startRoute: NestedRoute): NestedRoutesBackStack {
    return remember {
        NestedRoutesBackStack(startRoute)
    }
}