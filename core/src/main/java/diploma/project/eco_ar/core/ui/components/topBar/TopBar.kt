package diploma.project.eco_ar.core.ui.components.topBar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import diploma.project.eco_ar.core.R
import diploma.project.eco_ar.core.ui.components.buttons.VectorButton
import diploma.project.eco_ar.core.ui.components.miscellaneous.Spacer
import diploma.project.eco_ar.core.ui.components.miscellaneous.SpacerStatusBar
import diploma.project.eco_ar.core.ui.theme.LocalColorTheme
import diploma.project.eco_ar.core.ui.theme.robotoTextStyle

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    title: AnnotatedString? = null,
    textColor: Color = LocalColorTheme.current.colorTextDark,
    backPressedButtonColor: Color = LocalColorTheme.current.colorButtonVector,
    endContent: (@Composable BoxScope.() -> Unit)? = null,
    onBackPressed: (() -> Unit)? = null
) {
    Column(
        modifier = modifier
    ) {
        SpacerStatusBar()
        Spacer(16.dp)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            onBackPressed?.let {
                VectorButton(
                    modifier = Modifier.align(Alignment.CenterStart),
                    vectorResId = R.drawable.icon_arrow_left,
                    tint = backPressedButtonColor,
                    onClick = onBackPressed
                )
            }
            title?.let {
                Text(
                    modifier = Modifier.align(
                        alignment = when (onBackPressed) {
                            null if endContent == null -> Alignment.Center
                            null -> Alignment.CenterStart
                            else -> Alignment.Center
                        }
                    ),
                    text = title,
                    style = robotoTextStyle(28.sp, FontWeight.Bold),
                    color = textColor
                )
            }
            endContent?.invoke(this)
        }
    }
}

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    textColor: Color = LocalColorTheme.current.colorTextDark,
    backPressedButtonColor: Color = LocalColorTheme.current.colorButtonVector,
    endContent: (@Composable BoxScope.() -> Unit)? = null,
    onBackPressed: (() -> Unit)? = null
) {
    TopBar(
        modifier = modifier,
        title = title?.let { AnnotatedString(it) },
        textColor = textColor,
        backPressedButtonColor = backPressedButtonColor,
        endContent = endContent,
        onBackPressed = onBackPressed
    )
}