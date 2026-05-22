package diploma.project.eco_ar.feature_ar.ui.screens

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import diploma.project.eco_ar.core.ui.components.buttons.VectorButton
import diploma.project.eco_ar.core.ui.components.card.SelectionCard
import diploma.project.eco_ar.core.ui.components.topBar.TopBar
import diploma.project.eco_ar.core.ui.permissions.rememberAndroidPermission
import diploma.project.eco_ar.core.ui.permissions.rememberAndroidPermissions
import diploma.project.eco_ar.core.ui.snackbar.LocalSnackbarState
import diploma.project.eco_ar.core.ui.snackbar.SnackbarType
import diploma.project.eco_ar.core.ui.theme.LocalColorTheme
import diploma.project.eco_ar.feature_ar.R
import diploma.project.eco_ar.feature_ar.domain.InfoLayer
import diploma.project.eco_ar.feature_ar.ui.components.CameraPreview
import diploma.project.eco_ar.feature_ar.ui.components.CylinderScene
import diploma.project.eco_ar.feature_ar.ui.dialogs.HelpDialog
import diploma.project.eco_ar.feature_ar.ui.dialogs.InfoDialog
import diploma.project.eco_ar.feature_ar.ui.viewModel.ARAction
import diploma.project.eco_ar.feature_ar.ui.viewModel.ARUiAction
import diploma.project.eco_ar.feature_ar.ui.viewModel.ARUiState
import kotlinx.collections.immutable.persistentListOf

@Composable
fun ARScreen(
    uiState: ARUiState,
    onAction: (ARAction) -> Unit,
    onUiAction: (ARUiAction) -> Unit
) {
    val context = LocalContext.current
    val colorTheme = LocalColorTheme.current
    val snackbarState = LocalSnackbarState.current

    var hasAskedForCamera by rememberSaveable { mutableStateOf(false) }
    val cameraPermissionNotGrantedText = stringResource(diploma.project.eco_ar.core.R.string.camera_permission_not_granted)
    val cameraPermission = rememberAndroidPermission(
        name = Manifest.permission.CAMERA,
        onNotGrantedCallback = {
            if (hasAskedForCamera) {
                snackbarState?.showSingle(cameraPermissionNotGrantedText, SnackbarType.FAILURE)
                onUiAction(ARUiAction.NavigateBack)
            }
        }
    )

    val locationPermission = rememberAndroidPermissions(
        names = persistentListOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ),
        onGrantedCallback = {
            onAction(ARAction.FetchReport(context))
            snackbarState?.dismissAll()
        }
    )

    var isInfoDialogShown by rememberSaveable { mutableStateOf(false) }
    var isHelpDialogShown by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        cameraPermission.askForPermission()
        hasAskedForCamera = true
    }

    LaunchedEffect(Unit) {
        if (locationPermission.isGranted) {
            onAction(ARAction.FetchReport(context))
            snackbarState?.dismissAll()
        } else {
            locationPermission.askForPermission()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (cameraPermission.isGranted) {
            CameraPreview(
                modifier = Modifier.fillMaxSize()
            )
            CylinderScene(
                modifier = Modifier.fillMaxSize(),
                layer = uiState.infoLayer,
                value = uiState.displayedValue
            )
        }
        TopBar(
            modifier = Modifier.padding(horizontal = 20.dp),
            title = stringResource(R.string.ar_review),
            textColor = colorTheme.colorWhite,
            backPressedButtonColor = colorTheme.colorWhite,
            onBackPressed = {
                onUiAction(ARUiAction.NavigateBack)
            }
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Bottom),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                VectorButton(
                    vectorResId = diploma.project.eco_ar.core.R.drawable.icon_info_circled,
                    tint = colorTheme.colorWhite,
                    onClick = {
                        isInfoDialogShown = true
                    }
                )
                VectorButton(
                    vectorResId = diploma.project.eco_ar.core.R.drawable.icon_help_circled,
                    tint = colorTheme.colorWhite,
                    onClick = {
                        isHelpDialogShown = true
                    }
                )
            }
            SelectionCard(
                modifier = Modifier.fillMaxWidth(),
                selectedIndex = uiState.infoLayer.ordinal,
                values = InfoLayer.names(),
                onSelected = { index ->
                    onAction(ARAction.SetInfoLayer(InfoLayer.entries[index]))
                }
            )
        }
    }

    InfoDialog(
        isShown = isInfoDialogShown,
        report = uiState.potentialReport,
        onDismiss = {
            isInfoDialogShown = false
        },
        onSaveReport = {
            onAction(ARAction.SaveReport)
            isInfoDialogShown = false
        }
    )

    HelpDialog(
        isShown = isHelpDialogShown,
        onDismiss = {
            isHelpDialogShown = false
        }
    )
}
