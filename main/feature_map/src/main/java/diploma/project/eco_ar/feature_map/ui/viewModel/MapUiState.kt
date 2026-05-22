package diploma.project.eco_ar.feature_map.ui.viewModel

import androidx.compose.runtime.Immutable
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.AutocompletePrediction
import diploma.project.eco_ar.core.domain.model.AirQualityReport
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class MapUiState(
    val searchText: String = "",
    val searchPredictions: ImmutableList<AutocompletePrediction>? = null,
    val mapPointData: Pair<LatLng, AirQualityReport>? = null
)
