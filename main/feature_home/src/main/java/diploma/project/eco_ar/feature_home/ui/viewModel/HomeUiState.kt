package diploma.project.eco_ar.feature_home.ui.viewModel

import androidx.compose.runtime.Immutable
import diploma.project.eco_ar.core.domain.model.Report

@Immutable
data class HomeUiState(
    val report: Report? = null
)
