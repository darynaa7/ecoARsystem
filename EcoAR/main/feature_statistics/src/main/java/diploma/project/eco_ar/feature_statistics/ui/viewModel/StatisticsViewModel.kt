package diploma.project.eco_ar.feature_statistics.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import diploma.project.eco_ar.core.data.repository.ReportsRepository
import diploma.project.eco_ar.feature_statistics.domain.DataTimeRange
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class StatisticsViewModel(
    private val reportsRepository: ReportsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(StatisticsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadAndFilterReports()
    }

    fun onAction(action: StatisticsAction) {
        when (action) {
            is StatisticsAction.SetTimeRange -> setTimeRange(action.range)
        }
    }

    private fun setTimeRange(range: DataTimeRange) {
        _uiState.update {
            it.copy(
                timeRange = range
            )
        }

        loadAndFilterReports()
    }

    private fun loadAndFilterReports(shouldRetry: Boolean = true) {
        viewModelScope.launch {
            val reports = when (uiState.value.timeRange) {
                DataTimeRange.WEEK -> reportsRepository.getLastWeekReports()
                DataTimeRange.MONTH -> reportsRepository.getLastMonthReports()
                DataTimeRange.YEAR -> reportsRepository.getLastYearReports()
            }

            if (shouldRetry && reports.isEmpty()) {
                reportsRepository.getReports().onSuccess { loadAndFilterReports(false) }
                return@launch
            }

            _uiState.update { state ->
                state.copy(
                    reports = reports.toPersistentList()
                )
            }
        }
    }
}