package diploma.project.eco_ar.feature_profile.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import diploma.project.eco_ar.core.ui.components.buttons.TextButton
import diploma.project.eco_ar.core.ui.components.miscellaneous.BoxScreen
import diploma.project.eco_ar.core.ui.components.miscellaneous.Spacer
import diploma.project.eco_ar.core.ui.components.topBar.TopBar
import diploma.project.eco_ar.core.ui.modifier.shadow
import diploma.project.eco_ar.core.ui.theme.LocalColorTheme
import diploma.project.eco_ar.core.ui.theme.interTextStyle
import diploma.project.eco_ar.feature_profile.R
import diploma.project.eco_ar.feature_profile.ui.components.ReportCard
import diploma.project.eco_ar.feature_profile.ui.viewModel.reports.ReportsAction
import diploma.project.eco_ar.feature_profile.ui.viewModel.reports.ReportsUiAction
import diploma.project.eco_ar.feature_profile.ui.viewModel.reports.ReportsUiState

@Composable
fun ReportsScreen(
    uiState: ReportsUiState,
    onAction: (ReportsAction) -> Unit,
    onUiAction: (ReportsUiAction) -> Unit
) {
    val context = LocalContext.current
    val colorTheme = LocalColorTheme.current

    BoxScreen {
        Column {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                stickyHeader {
                    TopBar(
                        modifier = Modifier
                            .shadow(RectangleShape)
                            .background(colorTheme.colorBackground)
                            .padding(horizontal = 20.dp)
                            .padding(bottom = 8.dp),
                        title = stringResource(R.string.reports),
                        onBackPressed = {
                            onUiAction(ReportsUiAction.NavigateBack)
                        }
                    )
                }
                item {
                    Spacer(8.dp)
                }
                itemsIndexed(
                    items = uiState.reports,
                    key = { _, it -> it.id }
                ) { index, report ->
                    ReportCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 8.dp),
                        index = index + 1,
                        report = report
                    )
                }
                item {
                    Spacer(96.dp)
                }
            }
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .navigationBarsPadding()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.export_to_pdf),
                textStyle = interTextStyle(12.sp, FontWeight.Bold),
                onClick = {
                    onAction(ReportsAction.ExportToPDF(context))
                }
            )
            TextButton(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.export_to_csv),
                textStyle = interTextStyle(12.sp, FontWeight.Bold),
                onClick = {
                    onAction(ReportsAction.ExportToCSV(context))
                }
            )
        }
    }
}