package diploma.project.eco_ar.core.ui.dialog

import android.Manifest
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import diploma.project.eco_ar.core.R
import diploma.project.eco_ar.core.ui.components.buttons.VectorButton
import diploma.project.eco_ar.core.ui.components.miscellaneous.Spacer
import diploma.project.eco_ar.core.ui.components.miscellaneous.SpacerNavigationBar
import diploma.project.eco_ar.core.ui.modifier.noIndicationClickable
import diploma.project.eco_ar.core.ui.permissions.rememberAndroidPermission
import diploma.project.eco_ar.core.ui.snackbar.LocalSnackbarState
import diploma.project.eco_ar.core.ui.snackbar.SnackbarType
import diploma.project.eco_ar.core.ui.theme.LocalColorTheme
import diploma.project.eco_ar.core.ui.theme.interTextStyle
import diploma.project.eco_ar.core.ui.theme.robotoTextStyle
import java.io.File

@Composable
fun ImageSourceBottomDialog(
    isShown: Boolean,
    onDismiss: () -> Unit,
    onPictureSelected: (Uri) -> Unit
) {
    val context = LocalContext.current
    val colorTheme = LocalColorTheme.current
    val snackbarState = LocalSnackbarState.current

    var tempCameraUri by remember { mutableStateOf<Uri?>(null) }

    var isTakingAPhoto by remember { mutableStateOf(false) }

    fun createTempPictureUri(context: Context): Uri {
        val tempFile = File.createTempFile("picture_${System.currentTimeMillis()}", ".jpg", context.cacheDir)
        tempFile.createNewFile()
        tempFile.deleteOnExit()

        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            tempFile
        )
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            onPictureSelected(uri)
        }

        onDismiss()
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            tempCameraUri?.let {
                onPictureSelected(it)
            }
        }

        isTakingAPhoto = false
        onDismiss()
    }

    val cameraPermissionNotGrantedText = stringResource(R.string.camera_permission_not_granted)
    val cameraPermission = rememberAndroidPermission(
        name = Manifest.permission.CAMERA,
        onGrantedCallback = {
            if (isTakingAPhoto) {
                createTempPictureUri(context).let {
                    tempCameraUri = it
                    cameraLauncher.launch(it)
                }
            }
        }
    )

    BottomSheetDialog(
        isShown = isShown,
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(R.string.select_source),
                    style = interTextStyle(20.sp, FontWeight.Bold),
                    color = colorTheme.colorTextDark
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .noIndicationClickable(onClick = onDismiss),
                    text = stringResource(R.string.cancel),
                    style = robotoTextStyle(14.sp, FontWeight.Bold),
                    color = colorTheme.colorBlueMain
                )
            }
            Spacer(24.dp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                VectorButton(
                    modifier = Modifier.size(56.dp),
                    vectorResId = R.drawable.icon_add_photo_camera,
                    tint = colorTheme.colorGreenMain,
                    onClick = {
                        if (cameraPermission.isGranted) {
                            createTempPictureUri(context).let {
                                tempCameraUri = it
                                cameraLauncher.launch(it)
                            }
                        } else {
                            isTakingAPhoto = true
                            snackbarState?.showSingle(cameraPermissionNotGrantedText, SnackbarType.FAILURE)
                            cameraPermission.askForPermission()
                        }
                    }
                )
                VectorButton(
                    modifier = Modifier.size(56.dp),
                    vectorResId = R.drawable.icon_add_photo_gallery,
                    tint = colorTheme.colorGreenMain,
                    onClick = {
                        galleryLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                )
            }
            Spacer(16.dp)
            SpacerNavigationBar()
        }
    }
}