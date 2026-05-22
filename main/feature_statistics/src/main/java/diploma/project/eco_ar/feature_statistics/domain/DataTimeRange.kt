package diploma.project.eco_ar.feature_statistics.domain

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import diploma.project.eco_ar.feature_statistics.R
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList

enum class DataTimeRange {
    WEEK,
    MONTH,
    YEAR;

    companion object {
        @Composable
        fun names(context: Context): ImmutableList<Pair<String, String>> {
            return remember(context) {
                entries.map { Pair(it.toString(context), "") }.toPersistentList()
            }
        }
    }

    fun toString(context: Context): String {
        return when (this) {
            WEEK -> context.getString(R.string.for_last_week)
            MONTH -> context.getString(R.string.for_last_month)
            YEAR -> context.getString(R.string.for_last_year)
        }
    }
}