package diploma.project.eco_ar.feature_map.ui.miscellaneous

import android.Manifest
import android.content.Context
import android.location.Location
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
fun getMyLocation(context: Context, onLocationReceived: (Location?) -> Unit) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
        if (location != null) {
            onLocationReceived(location)
        } else {
            fusedLocationClient
                .getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                .addOnSuccessListener { newLocation: Location? ->
                    onLocationReceived(newLocation)
                }
        }
    }
}

@RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
fun setGoogleMapsCameraOnMyLocation(
    context: Context,
    coroutineScope: CoroutineScope,
    cameraPositionState: CameraPositionState
) {
    getMyLocation(context) { location ->
        location?.let {
            val userLatLng = LatLng(it.latitude, it.longitude)

            coroutineScope.launch {
                cameraPositionState.animate(
                    update = CameraUpdateFactory.newLatLngZoom(userLatLng, 15f),
                    durationMs = 1000
                )
            }
        }
    }
}