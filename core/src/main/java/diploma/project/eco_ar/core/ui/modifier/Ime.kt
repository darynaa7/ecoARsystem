package diploma.project.eco_ar.core.ui.modifier

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlin.math.min

fun Modifier.offsetWithIme(): Modifier = composed {
    val density = LocalDensity.current
    val windowInfo = LocalWindowInfo.current

    val screenHeight = windowInfo.containerSize.height
    val keyboardHeight = WindowInsets.ime.getBottom(density)

    val targetPaddingPx = remember { with(density) { 8.dp.toPx() } }
    var targetPosition by remember { mutableStateOf(Offset.Zero) }
    var targetSize by remember { mutableStateOf(IntSize.Zero) }

    this
        .onGloballyPositioned { coordinates ->
            targetPosition = coordinates.positionInWindow()
            targetSize = coordinates.size
        }
        .offset {
            val staticValue = screenHeight - targetPosition.y.toInt() - targetSize.height - targetPaddingPx.toInt()

            IntOffset(
                0,
                min(- keyboardHeight + staticValue, 0)
            )
        }
}