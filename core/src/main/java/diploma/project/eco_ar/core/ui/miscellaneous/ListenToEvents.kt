package diploma.project.eco_ar.core.ui.miscellaneous

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import diploma.project.eco_ar.core.ui.snackbar.LocalSnackbarState
import diploma.project.eco_ar.core.ui.snackbar.SnackbarType
import kotlinx.coroutines.flow.Flow

@Composable
fun <T> ListenToEvents(
    events: Flow<T>,
    collector: suspend (
        event: T,
        onSuccess: (String?) -> Unit,
        onError: (String?) -> Unit
    ) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val snackbarHostState = LocalSnackbarState.current

    val coroutineScope = rememberCoroutineScope()

    val onSuccess: (String?) -> Unit = remember(coroutineScope) {
        { message: String? ->
            message?.let {
                snackbarHostState?.show(message)
            }
        }
    }

    val onError: (String?) -> Unit = remember(coroutineScope) {
        { message: String? ->
            message?.let {
                snackbarHostState?.show(message, SnackbarType.FAILURE)
            }
        }
    }

    LaunchedEffect(events, lifecycleOwner) {
        events
            .flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .collect {
                collector(it, onSuccess, onError)
            }
    }
}