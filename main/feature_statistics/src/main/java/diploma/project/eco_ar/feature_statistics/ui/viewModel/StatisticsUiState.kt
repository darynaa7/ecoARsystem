package diploma.project.eco_ar.feature_statistics.ui.viewModel

import androidx.compose.runtime.Immutable
import diploma.project.eco_ar.core.domain.model.Report
import diploma.project.eco_ar.feature_statistics.domain.DataTimeRange
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class StatisticsUiState(
    val timeRange: DataTimeRange = DataTimeRange.WEEK,
    val reports: ImmutableList<Report> = persistentListOf()
)
