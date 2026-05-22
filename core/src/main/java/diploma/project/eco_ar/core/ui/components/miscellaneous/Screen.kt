package diploma.project.eco_ar.core.ui.components.miscellaneous

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import diploma.project.eco_ar.core.ui.modifier.noIndicationClickable
import diploma.project.eco_ar.core.ui.theme.LocalColorTheme

@Composable
inline fun ColumnScreen(
    modifier: Modifier = Modifier,
    clearFocusOnClick: Boolean = false,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    padding: PaddingValues = PaddingValues(),
    backgroundColor: Color = LocalColorTheme.current.colorBackground,
    content: @Composable ColumnScope.() -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
            .then(
                if (clearFocusOnClick) {
                    Modifier.noIndicationClickable(
                        onClick = {
                            focusManager.clearFocus()
                        }
                    )
                } else {
                    Modifier
                }
            )
            .padding(padding),
        horizontalAlignment = horizontalAlignment,
        verticalArrangement = verticalArrangement,
        content = content
    )
}

@Composable
inline fun ScrollableColumnScreen(
    modifier: Modifier = Modifier,
    clearFocusOnClick: Boolean = false,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    padding: PaddingValues = PaddingValues(),
    backgroundColor: Color = LocalColorTheme.current.colorBackground,
    content: @Composable ColumnScope.(ScrollState) -> Unit
) {
    val focusManager = LocalFocusManager.current

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
            .then(
                if (clearFocusOnClick) {
                    Modifier.noIndicationClickable(
                        onClick = {
                            focusManager.clearFocus()
                        }
                    )
                } else {
                    Modifier
                }
            )
            .padding(padding)
            .verticalScroll(scrollState),
        horizontalAlignment = horizontalAlignment,
        verticalArrangement = verticalArrangement,
        content = {
            content(scrollState)
        }
    )
}

@Composable
inline fun BoxScreen(
    modifier: Modifier = Modifier,
    clearFocusOnClick: Boolean = false,
    padding: PaddingValues = PaddingValues(),
    backgroundColor: Color = LocalColorTheme.current.colorBackground,
    content: @Composable BoxScope.() -> Unit
) {
    val focusManager = LocalFocusManager.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
            .then(
                if (clearFocusOnClick) {
                    Modifier.noIndicationClickable(
                        onClick = {
                            focusManager.clearFocus()
                        }
                    )
                } else {
                    Modifier
                }
            )
            .padding(padding),
        content = content
    )
}