package diploma.project.eco_ar.feature_home.ui.viewModel

sealed interface HomeEvent {
    data class FetchReportFailure(val message: String? = null) : HomeEvent
}