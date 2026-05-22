package diploma.project.eco_ar.feature_statistics.ui.viewModel

import diploma.project.eco_ar.feature_statistics.domain.DataTimeRange

sealed interface StatisticsAction {
    data class SetTimeRange(val range: DataTimeRange) : StatisticsAction
}