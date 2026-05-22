package diploma.project.eco_ar.feature_map.ui.viewModel

import com.google.android.gms.maps.model.LatLng

sealed interface MapAction {
    data class OnSearchTextChanged(val text: String) : MapAction

    data class FetchDataForMapPoint(val latLng: LatLng) : MapAction
    data object ClearDataForMapPoint : MapAction
}