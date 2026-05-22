package diploma.project.eco_ar.feature_home.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import diploma.project.eco_ar.core.ui.components.buttons.VectorButton
import diploma.project.eco_ar.core.ui.components.card.Card
import diploma.project.eco_ar.core.ui.components.miscellaneous.ScrollableColumnScreen
import diploma.project.eco_ar.core.ui.components.miscellaneous.Spacer
import diploma.project.eco_ar.core.ui.components.miscellaneous.SpacerNavigationBar
import diploma.project.eco_ar.core.ui.components.topBar.TopBar
import diploma.project.eco_ar.core.ui.modifier.shadow
import diploma.project.eco_ar.core.ui.theme.LocalColorTheme
import diploma.project.eco_ar.core.ui.theme.robotoTextStyle
import diploma.project.eco_ar.feature_home.R
import diploma.project.eco_ar.feature_home.ui.viewModel.HomeUiAction
import diploma.project.eco_ar.feature_home.ui.viewModel.HomeUiState

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onUiAction: (HomeUiAction) -> Unit
) {
    val context = LocalContext.current
    val colorTheme = LocalColorTheme.current

    Box {
        ScrollableColumnScreen(
            padding = PaddingValues(horizontal = 20.dp),
            horizontalAlignment = Alignment.Start
        ) {
            TopBar(
                title = stringResource(R.string.Home)
            )
            uiState.report?.let { report ->
                Spacer(16.dp)
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "AQI:",
                                style = robotoTextStyle(24.sp),
                                color = colorTheme.colorTextDark
                            )
                            Text(
                                modifier = Modifier
                                    .background(report.getAirQuality().color, RoundedCornerShape(50))
                                    .padding(horizontal = 10.dp, vertical = 6.dp),
                                text = report.getAirQuality().toString(context),
                                style = robotoTextStyle(20.sp),
                                color = report.getAirQuality().contentColor
                            )
                        }
                        Card {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "${report.aqi}",
                                    style = robotoTextStyle(32.sp),
                                    color = colorTheme.colorTextDark
                                )
                                Text(
                                    text = stringResource(R.string.total_mark),
                                    style = robotoTextStyle(16.sp),
                                    color = colorTheme.colorTextDark,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
                Spacer(16.dp)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Card(
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Тепло, °C",
                                style = robotoTextStyle(12.sp),
                                color = colorTheme.colorTextDark
                            )
                            Text(
                                text = "%.2f".format(report.temperature),
                                style = robotoTextStyle(28.sp),
                                color = colorTheme.colorTextDark
                            )
                        }
                    }
                    Card(
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Вологість, %",
                                style = robotoTextStyle(12.sp),
                                color = colorTheme.colorTextDark
                            )
                            Text(
                                text = "${report.humidity}",
                                style = robotoTextStyle(28.sp),
                                color = colorTheme.colorTextDark
                            )
                        }
                    }
                    Card(
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.wind),
                                style = robotoTextStyle(12.sp),
                                color = colorTheme.colorTextDark
                            )
                            Text(
                                text = "%.2f".format(report.windSpeed),
                                style = robotoTextStyle(28.sp),
                                color = colorTheme.colorTextDark
                            )
                        }
                    }
                }
                Spacer(16.dp)
                Text(
                    text = stringResource(R.string.recommendations),
                    style = robotoTextStyle(24.sp),
                    color = colorTheme.colorTextDark
                )
                Spacer(8.dp)
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        report.getRecommendations().forEach { recommendation ->
                            Card(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = recommendation,
                                    style = robotoTextStyle(16.sp),
                                    color = colorTheme.colorTextDark
                                )
                            }
                        }
                    }
                }
            }
            Spacer(162.dp)
            SpacerNavigationBar()
        }
        VectorButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 92.dp, end = 20.dp)
                .navigationBarsPadding()
                .size(60.dp)
                .shadow(CircleShape),
            vectorResId = diploma.project.eco_ar.core.R.drawable.icon_view_in_ar,
            tint = colorTheme.colorGreenMain,
            background = colorTheme.colorBackground,
            innerPadding = 8.dp,
            onClick = {
                onUiAction(HomeUiAction.NavigateToAR)
            }
        )
    }
}