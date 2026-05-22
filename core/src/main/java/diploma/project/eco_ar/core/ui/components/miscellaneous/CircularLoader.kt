package diploma.project.eco_ar.core.ui.components.miscellaneous

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import diploma.project.eco_ar.core.ui.modifier.dummyClickable
import diploma.project.eco_ar.core.ui.modifier.shadow
import diploma.project.eco_ar.core.ui.theme.LocalColorTheme

class CircularLoaderState {

    val isShown = mutableStateOf(false)
    val navigationBackAllowed = mutableStateOf(false)

    fun show(navigationBackAllowed: Boolean = false) {
        isShown.value = true
        this.navigationBackAllowed.value = navigationBackAllowed
    }

    fun hide() {
        isShown.value = false
    }
}

val LocalCircularLoaderState = staticCompositionLocalOf { CircularLoaderState() }

@Composable
fun CircularLoader() {
    val colorTheme = LocalColorTheme.current
    val circularLoaderState = LocalCircularLoaderState.current

    if (circularLoaderState.isShown.value) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .dummyClickable(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .shadow(CircleShape)
                    .background(colorTheme.colorWhite, CircleShape)
                    .padding(5.dp),
                color = colorTheme.colorTextDark
            )
        }

        BackHandler(enabled = !circularLoaderState.navigationBackAllowed.value, onBack = {})
    }
}