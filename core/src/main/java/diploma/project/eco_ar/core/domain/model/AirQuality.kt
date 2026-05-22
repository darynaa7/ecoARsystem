package diploma.project.eco_ar.core.domain.model

import android.content.Context
import androidx.compose.ui.graphics.Color
import diploma.project.eco_ar.core.R

enum class AirQuality(val color: Color, val contentColor: Color) {
    CLEAN(Color(0xFF1EB31E), Color(0xFFFFFFFF)),
    MEDIUM(Color(0xFFE1BB20), Color(0xFF000000)),
    BAD(Color(0xFFDB891F), Color(0xFFFFFFFF)),
    POLLUTED(Color(0xFFB3261E), Color(0xFFFFFFFF));

    fun toString(context: Context): String {
        return when (this) {
            CLEAN -> context.getString(R.string.aq_clean)
            MEDIUM -> context.getString(R.string.aq_medium)
            BAD -> context.getString(R.string.aq_bad)
            POLLUTED -> context.getString(R.string.aq_polluted)
        }
    }
}