package diploma.project.eco_ar.feature_profile.ui.viewModel.reports

import android.content.Context

sealed interface ReportsAction {
    data class ExportToPDF(val context: Context) : ReportsAction
    data class ExportToCSV(val context: Context) : ReportsAction
}