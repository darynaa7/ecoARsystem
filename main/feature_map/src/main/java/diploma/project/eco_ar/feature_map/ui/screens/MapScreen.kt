package diploma.project.eco_ar.feature_map.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import diploma.project.eco_ar.core.R
import diploma.project.eco_ar.core.ui.components.miscellaneous.BoxScreen
import diploma.project.eco_ar.core.ui.components.miscellaneous.Spacer
import diploma.project.eco_ar.core.ui.modifier.shadow
import diploma.project.eco_ar.core.ui.permissions.rememberAndroidPermissions
import diploma.project.eco_ar.core.ui.snackbar.LocalSnackbarState
import diploma.project.eco_ar.core.ui.snackbar.SnackbarType
import diploma.project.eco_ar.core.ui.theme.LocalColorTheme
import diploma.project.eco_ar.feature_map.ui.components.GoogleMapButton
import diploma.project.eco_ar.feature_map.ui.components.GoogleMapPoint
import diploma.project.eco_ar.feature_map.ui.components.PredictionsSearchTextField
import diploma.project.eco_ar.feature_map.ui.miscellaneous.setGoogleMapsCameraOnMyLocation
import diploma.project.eco_ar.feature_map.ui.viewModel.MapAction
import diploma.project.eco_ar.feature_map.ui.viewModel.MapUiState
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun MapScreen(
    uiState: MapUiState,
    onAction: (MapAction) -> Unit
) {
    val context = LocalContext.current
    val colorTheme = LocalColorTheme.current
    val focusManager = LocalFocusManager.current
    val snackbarState = LocalSnackbarState.current

    val coroutineScope = rememberCoroutineScope()
    val placesClient = koinInject<PlacesClient>()

    val cameraPositionState = rememberCameraPositionState()
    var cameraCenteredOnMyLocation by rememberSaveable { mutableStateOf(false) }

    val pleaseAllowFineLocationText = stringResource(diploma.project.eco_ar.feature_map.R.string.please_allow_fine_location_please)

    val locationPermission = rememberAndroidPermissions(
        names = persistentListOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ),
        onGrantedCallback = {
            setGoogleMapsCameraOnMyLocation(context, coroutineScope, cameraPositionState)
            snackbarState?.dismissAll()
        },
        onNotGrantedCallback = {
            snackbarState?.showSingle(pleaseAllowFineLocationText, SnackbarType.INFO)
        }
    )

    val uiSettings = remember {
        MapUiSettings(
            myLocationButtonEnabled = false,
            zoomControlsEnabled = false
        )
    }
    val properties = remember(locationPermission.isGranted) {
        MapProperties(
            isMyLocationEnabled = locationPermission.isGranted
        )
    }

    LaunchedEffect(Unit) {
        if (locationPermission.isGranted) {
            snackbarState?.dismissAll()
            if (cameraCenteredOnMyLocation) return@LaunchedEffect

            val isFineLocationGranted = ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

            val isCoarseLocationGranted = ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

            if (!isFineLocationGranted || !isCoarseLocationGranted) {
                locationPermission.askForPermission()
                return@LaunchedEffect
            }

            cameraCenteredOnMyLocation = true
            setGoogleMapsCameraOnMyLocation(context, coroutineScope, cameraPositionState)
        } else {
            locationPermission.askForPermission()
        }
    }

    BoxScreen {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = properties,
            uiSettings = uiSettings,
            onMapClick = { latLan ->
                focusManager.clearFocus()

                onAction(MapAction.FetchDataForMapPoint(latLan))
            }
        ) {
            uiState.mapPointData?.let { (latLng, report) ->
                GoogleMapPoint(
                    latitude = latLng.latitude,
                    longitude = latLng.longitude,
                    report = report,
                    onClose = {
                        onAction(MapAction.ClearDataForMapPoint)
                    }
                )
            }
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .width(IntrinsicSize.Min)
                .navigationBarsPadding()
                .padding(bottom = 72.dp)
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GoogleMapButton(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .shadow(RoundedCornerShape(6.dp)),
                iconResId = R.drawable.icon_my_location,
                onClick = {
                    if (locationPermission.isGranted) {
                        snackbarState?.dismissAll()
                        setGoogleMapsCameraOnMyLocation(context, coroutineScope, cameraPositionState)
                    } else {
                        locationPermission.askForPermission()
                    }
                }
            )
            Spacer(10.dp)
            GoogleMapButton(
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                    .shadow(RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp)),
                iconResId = R.drawable.icon_add,
                onClick = {
                    coroutineScope.launch {
                        cameraPositionState.animate(
                            update = CameraUpdateFactory.zoomIn(),
                            durationMs = 400
                        )
                    }
                }
            )
            HorizontalDivider(color = colorTheme.colorDivider)
            GoogleMapButton(
                modifier = Modifier
                    .clip(RoundedCornerShape(bottomStart = 6.dp, bottomEnd = 6.dp))
                    .shadow(RoundedCornerShape(bottomStart = 6.dp, bottomEnd = 6.dp)),
                iconResId = R.drawable.icon_remove,
                onClick = {
                    coroutineScope.launch {
                        cameraPositionState.animate(
                            update = CameraUpdateFactory.zoomOut(),
                            durationMs = 400
                        )
                    }
                }
            )
        }
        PredictionsSearchTextField(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .statusBarsPadding()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            text = uiState.searchText,
            predictions = uiState.searchPredictions,
            onPredictionClick = { prediction ->
                val request = FetchPlaceRequest.newInstance(prediction.placeId, listOf(Place.Field.LOCATION))

                placesClient.fetchPlace(request).addOnSuccessListener { response ->
                    response.place.location?.let { latLng ->
                        coroutineScope.launch {
                            cameraPositionState.animate(
                                update = CameraUpdateFactory.newLatLngZoom(latLng, 12f),
                                durationMs = 400
                            )
                        }

                        onAction(MapAction.FetchDataForMapPoint(latLng))
                        onAction(MapAction.OnSearchTextChanged(""))
                        focusManager.clearFocus()
                    }
                }
            },
            onTextChanged = { text ->
                onAction(MapAction.OnSearchTextChanged(text))
            }
        )
    }
}