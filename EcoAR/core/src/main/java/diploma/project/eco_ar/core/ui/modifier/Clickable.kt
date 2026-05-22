package diploma.project.eco_ar.core.ui.modifier

import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier

fun Modifier.dummyClickable(): Modifier {
    return this.clickable(
        interactionSource = null,
        indication = null,
        onClick = { }
    )
}

fun Modifier.noIndicationClickable(onClick: () -> Unit): Modifier {
    return this.clickable(
        interactionSource = null,
        indication = null,
        onClick = onClick
    )
}