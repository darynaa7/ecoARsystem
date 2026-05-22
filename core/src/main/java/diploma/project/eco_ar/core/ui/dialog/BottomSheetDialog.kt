package diploma.project.eco_ar.core.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import diploma.project.eco_ar.core.ui.theme.LocalColorTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetDialog(
    isShown: Boolean,
    skipPartiallyExpanded: Boolean = false,
    topPadding: Dp = 0.dp,
    onDismissRequest: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    val colorTheme = LocalColorTheme.current

    val sheetDialogState = rememberModalBottomSheetState(skipPartiallyExpanded)

    LaunchedEffect(isShown) {
        if (isShown) {
            sheetDialogState.show()
        } else {
            sheetDialogState.hide()
        }
    }

    if (isShown) {
        ModalBottomSheet(
            modifier = Modifier.padding(top = topPadding),
            sheetState = sheetDialogState,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            containerColor = colorTheme.colorWhite,
            scrimColor = colorTheme.colorBlack.copy(0.5f),
            dragHandle = {
                Box(
                    modifier = Modifier
                        .padding(top = 12.dp, bottom = 16.dp)
                        .size(60.dp, 4.dp)
                        .background(colorTheme.colorDivider, RoundedCornerShape(50))
                )
            },
            contentWindowInsets = { WindowInsets() },
            onDismissRequest = onDismissRequest
        ) {
            content()
        }
    }
}