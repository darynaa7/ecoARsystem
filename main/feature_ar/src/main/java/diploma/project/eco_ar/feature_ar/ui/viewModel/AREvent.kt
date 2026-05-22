package diploma.project.eco_ar.feature_ar.ui.viewModel

sealed interface AREvent {
    data class FetchReportFailure(val message: String? = null) : AREvent
}