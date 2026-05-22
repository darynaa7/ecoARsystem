package diploma.project.eco_ar.feature_ar.ui.viewModel

import androidx.compose.runtime.Immutable
import diploma.project.eco_ar.core.domain.model.Report
import diploma.project.eco_ar.feature_ar.domain.InfoLayer

@Immutable
data class ARUiState(
    val infoLayer: InfoLayer = InfoLayer.TEMPERATURE,
    val displayedValue: Float? = null,
    val potentialReport: Report? = null
)
