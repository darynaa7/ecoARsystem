package diploma.project.eco_ar.core.ui.animations

import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay

@Composable
fun smartAnimateFloatAsState(
    isActive: Boolean,
    restValue: Float,
    activeValue: Float,
    restAnimationSpec: FiniteAnimationSpec<Float>,
    activeAnimationSpec: FiniteAnimationSpec<Float>
) : State<Float> {
    var delayedIsActive by remember { mutableStateOf(isActive) }
    val transition = updateTransition(delayedIsActive)

    val value = transition.animateFloat(
        transitionSpec = {
            if (false isTransitioningTo true) {
                activeAnimationSpec
            } else {
                restAnimationSpec
            }
        },
        targetValueByState = { state ->
            if (state) activeValue else restValue
        }
    )

    LaunchedEffect(isActive) {
        if (isActive) {
            delayedIsActive = true
        } else {
            while (delayedIsActive && value.value > activeValue) {
                delay(20)
            }

            delayedIsActive = false
        }
    }

    return value
}