package diploma.project.eco_ar.feature_profile.ui.viewModel.reports

import androidx.compose.runtime.Immutable
import diploma.project.eco_ar.core.domain.model.Report
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class ReportsUiState(
    val reports: ImmutableList<Report> = persistentListOf()
)
