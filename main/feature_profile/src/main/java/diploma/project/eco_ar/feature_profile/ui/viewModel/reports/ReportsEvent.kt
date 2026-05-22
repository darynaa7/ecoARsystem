package diploma.project.eco_ar.feature_profile.ui.viewModel.reports

sealed interface ReportsEvent {
    data class ExportToCSVFailure(val message: String? = null) : ReportsEvent
    data class ExportToPDFFailure(val message: String? = null) : ReportsEvent
}