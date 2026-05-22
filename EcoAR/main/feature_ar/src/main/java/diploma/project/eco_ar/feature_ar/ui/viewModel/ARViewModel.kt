package diploma.project.eco_ar.feature_ar.ui.viewModel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationServices
import diploma.project.eco_ar.core.data.repository.ReportsRepository
import diploma.project.eco_ar.core.data.repository.WeatherDataRepository
import diploma.project.eco_ar.core.domain.string.StringProvider
import diploma.project.eco_ar.feature_ar.R
import diploma.project.eco_ar.feature_ar.domain.InfoLayer
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@OptIn(FlowPreview::class)
class ARViewModel(
    private val reportsRepository: ReportsRepository,
    private val weatherDataRepository: WeatherDataRepository,
    private val stringProvider: StringProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow(ARUiState())
    val uiState = _uiState.asStateFlow()

    private val _events = Channel<AREvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: ARAction) {
        when (action) {
            is ARAction.FetchReport -> fetchReport(action.context)
            is ARAction.SetInfoLayer -> setInfoLayer(action.layer)

            ARAction.SaveReport -> saveReport()
        }
    }

     @SuppressLint("MissingPermission")
     private fun fetchReport(context: Context) {
         viewModelScope.launch {
             val client = LocationServices.getFusedLocationProviderClient(context)

             val (latitude, longitude) = try {
                 val location = client.lastLocation.await()
                 val coordinates = location?.let { it.latitude to it.longitude }

                 coordinates ?: let { null to null }
             } catch (e: Exception) {
                 e.printStackTrace()

                 null to null
             }

             if (latitude == null || longitude == null) {
                 _events.send(AREvent.FetchReportFailure(stringProvider.provide(R.string.error_fetching_location)))
                 return@launch
             }

             weatherDataRepository.getLocationWeatherData(latitude, longitude)
                 .onSuccess { report ->
                     _uiState.update {
                         it.copy(
                             potentialReport = report
                         )
                     }

                     setInfoLayer(uiState.value.infoLayer)
                 }
                 .onFailure {
                     _events.send(AREvent.FetchReportFailure(it.message))
                 }
         }
    }

     private fun setInfoLayer(layer: InfoLayer) {
        _uiState.update {
            it.copy(
                infoLayer = layer,
                displayedValue = when (layer) {
                    InfoLayer.TEMPERATURE -> it.potentialReport?.temperature
                    InfoLayer.HUMIDITY -> it.potentialReport?.humidity?.toFloat()
                    InfoLayer.PRESSURE -> it.potentialReport?.pressure?.toFloat()
                    InfoLayer.WIND_SPEED -> it.potentialReport?.windSpeed
                    InfoLayer.WIND_DEGREE -> it.potentialReport?.windDegrees?.toFloat()
                    InfoLayer.AQI -> it.potentialReport?.aqi?.toFloat()
                    InfoLayer.PM2_5 -> it.potentialReport?.pm2point5
                    InfoLayer.PM10 -> it.potentialReport?.pm10
                    InfoLayer.NO2 -> it.potentialReport?.no2
                    InfoLayer.O3 -> it.potentialReport?.o3
                    InfoLayer.CO -> it.potentialReport?.co
                }
            )
        }
    }

    private fun saveReport() {
        viewModelScope.launch {
            uiState.value.potentialReport?.let {
                reportsRepository.saveReport(it)
            }
        }
    }
}