package diploma.project.eco_ar.feature_statistics.domain

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Locale

@Immutable
data class XAxisValue(
    val value: Float,
    val label: String,
    val datePredicate: (LocalDateTime) -> Boolean
) {
    companion object {
        fun getXAxisValues(timeRange: DataTimeRange): ImmutableList<XAxisValue> {
            val today = LocalDate.now()

            return when (timeRange) {
                DataTimeRange.WEEK -> {
                    List(7) { i ->
                        val date = today.minusDays((6 - i).toLong())
                        val format = (if (i < 10) "%01d" else "%02d")

                        XAxisValue(
                            value = (i + 1).toFloat(),
                            label = format.format(date.dayOfMonth),
                            datePredicate = { reportDate -> reportDate.toLocalDate() == date }
                        )
                    }.toPersistentList()
                }

                DataTimeRange.MONTH -> {
                    List(30) { i ->
                        val date = today.minusDays((29 - i).toLong())
                        val format = (if (i < 10) "%01d" else "%02d")

                        XAxisValue(
                            value = (i + 1).toFloat(),
                            label = format.format(date.dayOfMonth),
                            datePredicate = { reportDate -> reportDate.toLocalDate() == date }
                        )
                    }.toPersistentList()
                }

                DataTimeRange.YEAR -> {
                    List(12) { i ->
                        val monthDate = today.minusMonths((11 - i).toLong())

                        XAxisValue(
                            value = (i + 1).toFloat(),
                            label = monthDate.month.name
                                .take(3)
                                .lowercase()
                                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                            datePredicate = { reportDate ->
                                reportDate.year == monthDate.year && reportDate.month == monthDate.month
                            }
                        )
                    }.toPersistentList()
                }
            }
        }
    }
}