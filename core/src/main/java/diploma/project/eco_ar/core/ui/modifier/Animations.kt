package diploma.project.eco_ar.core.ui.modifier

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.delay
import kotlin.math.roundToInt
import kotlin.random.Random

fun Modifier.shake(key: Any?) = composed {
    val density = LocalDensity.current

    var targetX by remember { mutableFloatStateOf(0f) }
    var targetY by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(key) {
        if (key == null) return@LaunchedEffect

        var counter = 0

        while (counter++ < 7) {
            Random.run {
                targetX = nextFloat() * (nextInt() % 5) * density.density
                targetY = nextFloat() * (nextInt() % 5) * density.density
            }

            delay(20)
        }

        targetX = 0f
        targetY = 0f
    }

    val x by animateFloatAsState(
        targetValue = targetX,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessHigh
        )
    )

    val y by animateFloatAsState(
        targetValue = targetY,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessHigh
        )
    )

    this.offset {
        IntOffset(x.roundToInt(), y.roundToInt())
    }
}