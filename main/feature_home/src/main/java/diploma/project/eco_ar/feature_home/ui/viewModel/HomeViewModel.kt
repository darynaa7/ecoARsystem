package diploma.project.eco_ar.feature_home.ui.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationServices
import diploma.project.eco_ar.core.data.repository.WeatherDataRepository
import diploma.project.eco_ar.core.domain.string.StringProvider
import diploma.project.eco_ar.feature_home.R
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@OptIn(FlowPreview::class)
class HomeViewModel(
    applicationContext: Context,
    private val weatherDataRepository: WeatherDataRepository,
    private val stringProvider: StringProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val _events = Channel<HomeEvent>()
    val events = _events.receiveAsFlow()

    init {
        viewModelScope.launch {
            val client = LocationServices.getFusedLocationProviderClient(applicationContext)

            val (latitude, longitude) = try {
                val location = client.lastLocation.await()
                val coordinates = location?.let { it.latitude to it.longitude }

                coordinates ?: let { null to null }
            } catch (e: Exception) {
                e.printStackTrace()

                null to null
            }

            if (latitude == null || longitude == null) {
                _events.send(HomeEvent.FetchReportFailure(stringProvider.provide(R.string.error_fetching_location)))
                return@launch
            }

            weatherDataRepository.getLocationWeatherData(latitude, longitude)
                .onSuccess { report ->
                    _uiState.update {
                        it.copy(
                            report = report
                        )
                    }
                }
                .onFailure {
                    _events.send(HomeEvent.FetchReportFailure(it.message))
                }
        }
    }
}