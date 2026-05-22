package diploma.project.eco_ar.core.ui.miscellaneous

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import diploma.project.eco_ar.core.ui.components.miscellaneous.LocalCircularLoaderState

@Composable
fun LoaderController(key: Boolean) {
    val circularLoaderState = LocalCircularLoaderState.current

    LaunchedEffect(key) {
        if (key) {
            circularLoaderState.show()
        } else {
            circularLoaderState.hide()
        }
    }
}