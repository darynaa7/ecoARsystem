package diploma.project.eco_ar.core.ui.components.buttons

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import diploma.project.eco_ar.core.ui.theme.getButtonColors
import diploma.project.eco_ar.core.ui.theme.interTextStyle

@Composable
fun TextButton(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = interTextStyle(20.sp, FontWeight.Bold),
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        colors = getButtonColors(),
        shape = RoundedCornerShape(16.dp),
        enabled = enabled,
        onClick = onClick
    ) {
        Text(
            modifier = Modifier.padding(vertical = 8.dp),
            text = text,
            style = textStyle
        )
    }
}