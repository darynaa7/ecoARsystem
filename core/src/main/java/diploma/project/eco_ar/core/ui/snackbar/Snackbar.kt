package diploma.project.eco_ar.core.ui.snackbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import diploma.project.eco_ar.core.R
import diploma.project.eco_ar.core.ui.modifier.shadow
import diploma.project.eco_ar.core.ui.theme.LocalColorTheme
import diploma.project.eco_ar.core.ui.theme.interTextStyle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

val LocalSnackbarState = compositionLocalOf<SnackbarState?> { null }

const val SNACKBAR_ACTION_SUCCESS = "SNACKBAR_ACTION_SUCCESS"
const val SNACKBAR_ACTION_FAILURE = "SNACKBAR_ACTION_FAILURE"
const val SNACKBAR_ACTION_INFO = "SNACKBAR_ACTION_INFO"

enum class SnackbarType {
    SUCCESS,
    FAILURE,
    INFO
}

class SnackbarState {

    val hostState = SnackbarHostState()

    val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    fun show(text: String?, type: SnackbarType = SnackbarType.SUCCESS) {
        if (text == null) return

        coroutineScope.launch {
            hostState.showSnackbar(
                message = text,
                actionLabel = when (type) {
                    SnackbarType.SUCCESS -> SNACKBAR_ACTION_SUCCESS
                    SnackbarType.FAILURE -> SNACKBAR_ACTION_FAILURE
                    SnackbarType.INFO -> SNACKBAR_ACTION_INFO
                },
                duration = SnackbarDuration.Short
            )
        }
    }

    fun showSingle(text: String?, type: SnackbarType = SnackbarType.SUCCESS) {
        if (text == null) return

        dismissAll()

        show(text, type)
    }

    fun dismissAll() {
        coroutineScope.coroutineContext.cancelChildren()
    }
}

@Composable
fun Snackbar(
    modifier: Modifier = Modifier
) {
    val colorTheme = LocalColorTheme.current
    val snackbarState = LocalSnackbarState.current ?: return

    SnackbarHost(
        modifier = modifier
            .systemBarsPadding()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        hostState = snackbarState.hostState
    ) { snackbarData ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
                .background(colorTheme.colorTextDark)
                .clickable(
                    onClick = {
                        snackbarData.dismiss()
                    }
                )
                .padding(vertical = 8.dp, horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = snackbarData.visuals.message,
                style = interTextStyle(14.sp),
                color = colorTheme.colorWhite
            )
            when (snackbarData.visuals.actionLabel) {
                SNACKBAR_ACTION_SUCCESS -> {
                    Icon(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(colorTheme.colorGreenMain)
                            .padding(4.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.icon_check),
                        contentDescription = null,
                        tint = colorTheme.colorWhite
                    )
                }
                SNACKBAR_ACTION_FAILURE -> {
                    Icon(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(colorTheme.colorRedSecondary)
                            .padding(4.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.icon_close_small),
                        contentDescription = null,
                        tint = colorTheme.colorWhite
                    )
                }
                SNACKBAR_ACTION_INFO -> {
                    Icon(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(colorTheme.colorBlueMain)
                            .padding(4.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.icon_info),
                        contentDescription = null,
                        tint = colorTheme.colorWhite
                    )
                }
            }
        }
    }
}