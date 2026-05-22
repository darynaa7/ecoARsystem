package diploma.project.eco_ar.feature_ar.ui.viewModel

import android.content.Context
import diploma.project.eco_ar.feature_ar.domain.InfoLayer

sealed interface ARAction {
    data class FetchReport(val context: Context) : ARAction
    data class SetInfoLayer(val layer: InfoLayer) : ARAction

    data object SaveReport : ARAction
}