@file:Suppress("UnusedReceiverParameter", "unused")

package diploma.project.eco_ar.core.ui.components.miscellaneous

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun ColumnScope.Spacer(height: Dp) {
    Spacer(modifier = Modifier.height(height))
}

@Composable
fun ColumnScope.Spacer(weight: Float) {
    Spacer(modifier = Modifier.weight(weight))
}

@Composable
fun ColumnScope.SpacerStatusBar() {
    Spacer(modifier = Modifier.statusBarsPadding())
}

@Composable
fun ColumnScope.SpacerNavigationBar() {
    Spacer(modifier = Modifier.navigationBarsPadding())
}

@Composable
fun RowScope.Spacer(width: Dp) {
    Spacer(modifier = Modifier.width(width))
}

@Composable
fun RowScope.Spacer(weight: Float) {
    Spacer(modifier = Modifier.weight(weight))
}

@Composable
fun SpacerVertical(height: Dp) {
    Spacer(modifier = Modifier.height(height))
}

@Composable
fun SpacerHorizontal(width: Dp) {
    Spacer(modifier = Modifier.width(width))
}