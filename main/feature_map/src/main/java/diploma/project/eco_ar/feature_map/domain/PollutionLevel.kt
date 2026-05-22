package diploma.project.eco_ar.feature_map.domain

import android.content.Context
import diploma.project.eco_ar.feature_map.R

enum class PollutionLevel {
    LOW,
    MODERATE,
    HIGH;

    fun toString(context: Context): String {
        return when (this) {
            LOW -> context.getString(R.string.low)
            MODERATE -> context.getString(R.string.moderate)
            HIGH -> context.getString(R.string.high)
        }
    }
}