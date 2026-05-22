package diploma.project.eco_ar.feature_map.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.PlaceTypes
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import diploma.project.eco_ar.core.data.repository.WeatherDataRepository
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class MapViewModel(
    private val weatherDataRepository: WeatherDataRepository,
    private val placesClient: PlacesClient
) : ViewModel() {

    private val _uiState = MutableStateFlow(MapUiState())
    val uiState = _uiState.asStateFlow()

    private var getMapPointsJob: Job? = null

    init {
        uiState
            .map { it.searchText }
            .distinctUntilChanged()
            .debounce(300)
            .onEach { text ->
                searchCities(text)
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: MapAction) {
        when (action) {
            is MapAction.OnSearchTextChanged -> onSearchTextChanged(action.text)

            is MapAction.FetchDataForMapPoint -> fetchDataForMapPoint(action.latLng)
            MapAction.ClearDataForMapPoint -> clearDataForMapPoint()
        }
    }

    private fun onSearchTextChanged(text: String) {
        _uiState.update {
            it.copy(
                searchText = text
            )
        }
    }

    private fun fetchDataForMapPoint(latLng: LatLng) {
        getMapPointsJob?.cancel()
        getMapPointsJob = viewModelScope.launch {
            weatherDataRepository.getLocationAirQualityData(latLng.latitude, latLng.longitude)
                .onSuccess { report ->
                    _uiState.update {
                        it.copy(
                            mapPointData = latLng to report
                        )
                    }
                }
        }
    }

    private fun clearDataForMapPoint() {
        _uiState.update {
            it.copy(
                mapPointData = null
            )
        }
    }

    private fun searchCities(text: String) {
        if (text.isBlank()) {
            _uiState.update {
                it.copy(
                    searchPredictions = null
                )
            }
        } else {
            if (text.length < 2) return

            val request = FindAutocompletePredictionsRequest.builder()
                .setQuery(text)
                .setTypesFilter(listOf(PlaceTypes.CITIES))
                .build()

            placesClient.findAutocompletePredictions(request)
                .addOnSuccessListener { response ->
                    _uiState.update {
                        it.copy(
                            searchPredictions = response.autocompletePredictions.toImmutableList()
                        )
                    }
                }
                .addOnFailureListener {
                    _uiState.update {
                        it.copy(
                            searchPredictions = persistentListOf()
                        )
                    }
                }
        }
    }
}