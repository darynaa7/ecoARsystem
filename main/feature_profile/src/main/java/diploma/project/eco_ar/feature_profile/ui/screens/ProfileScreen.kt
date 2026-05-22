package diploma.project.eco_ar.feature_profile.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import diploma.project.eco_ar.core.ui.components.buttons.VectorButton
import diploma.project.eco_ar.core.ui.components.card.TextCard
import diploma.project.eco_ar.core.ui.components.miscellaneous.ColumnScreen
import diploma.project.eco_ar.core.ui.components.miscellaneous.Spacer
import diploma.project.eco_ar.core.ui.components.topBar.TopBar
import diploma.project.eco_ar.core.ui.theme.LocalColorTheme
import diploma.project.eco_ar.core.ui.theme.robotoTextStyle
import diploma.project.eco_ar.feature_profile.R
import diploma.project.eco_ar.feature_profile.ui.viewModel.profile.ProfileUiAction
import diploma.project.eco_ar.feature_profile.ui.viewModel.profile.ProfileUiState

@Composable
fun ProfileScreen(
    uiState: ProfileUiState,
    onUiAction: (ProfileUiAction) -> Unit
) {
    val colorTheme = LocalColorTheme.current

    ColumnScreen(
        padding = PaddingValues(horizontal = 20.dp)
    ) {
        TopBar(
            title = stringResource(R.string.profile),
            endContent = {
                VectorButton(
                    vectorResId = diploma.project.eco_ar.core.R.drawable.icon_edit,
                    onClick = {
                        onUiAction(ProfileUiAction.NavigateToProfileEdit)
                    }
                )
            }
        )
        Spacer(32.dp)
        if (uiState.photoUri == null) {
            Image(
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .background(colorTheme.colorLightGray)
                    .padding(52.dp),
                imageVector = ImageVector.vectorResource(diploma.project.eco_ar.core.R.drawable.icon_account),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                colorFilter = ColorFilter.tint(colorTheme.colorGreenMain, BlendMode.SrcIn)
            )
        } else {
            AsyncImage(
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape),
                model = uiState.photoUri,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
        Spacer(12.dp)
        Text(
            text = uiState.name,
            style = robotoTextStyle(32.sp),
            color = colorTheme.colorTextDark
        )
        Spacer(8.dp)
        Text(
            text = uiState.email,
            style = robotoTextStyle(20.sp),
            color = colorTheme.colorTextDark
        )
        Spacer(32.dp)
        TextCard(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.my_reports),
            iconRes = diploma.project.eco_ar.core.R.drawable.icon_event_note,
            onClick = {
                onUiAction(ProfileUiAction.NavigateToReports)
            }
        )
        Spacer(24.dp)
        TextCard(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.settings),
            iconRes = diploma.project.eco_ar.core.R.drawable.icon_settings,
            onClick = {
                onUiAction(ProfileUiAction.NavigateToSettings)
            }
        )
    }
}