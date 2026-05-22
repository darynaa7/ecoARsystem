package diploma.project.eco_ar.feature_statistics.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import diploma.project.eco_ar.core.ui.components.card.SelectionCard
import diploma.project.eco_ar.core.ui.components.miscellaneous.ScrollableColumnScreen
import diploma.project.eco_ar.core.ui.components.miscellaneous.Spacer
import diploma.project.eco_ar.core.ui.components.topBar.TopBar
import diploma.project.eco_ar.core.ui.modifier.shadow
import diploma.project.eco_ar.core.ui.theme.LocalColorTheme
import diploma.project.eco_ar.core.ui.theme.robotoTextStyle
import diploma.project.eco_ar.feature_statistics.R
import diploma.project.eco_ar.feature_statistics.domain.DataTimeRange
import diploma.project.eco_ar.feature_statistics.domain.ReportInfo
import diploma.project.eco_ar.feature_statistics.domain.XAxisValue.Companion.getXAxisValues
import diploma.project.eco_ar.feature_statistics.domain.YAxisValue.Companion.getYAxisValues
import diploma.project.eco_ar.feature_statistics.ui.components.ColumnChart
import diploma.project.eco_ar.feature_statistics.ui.viewModel.StatisticsAction
import diploma.project.eco_ar.feature_statistics.ui.viewModel.StatisticsUiState

@Composable
fun StatisticsScreen(
    uiState: StatisticsUiState,
    onAction: (StatisticsAction) -> Unit
) {
    val context = LocalContext.current
    val colorTheme = LocalColorTheme.current

    val xAxisValues = remember(uiState.timeRange) { getXAxisValues(uiState.timeRange) }

    val daysString = stringResource(R.string.days)
    val monthsString = stringResource(R.string.months)
    val xTitle = remember(uiState.timeRange) {
        when (uiState.timeRange) {
            DataTimeRange.WEEK -> daysString
            DataTimeRange.MONTH -> daysString
            DataTimeRange.YEAR -> monthsString
        }
    }

    ScrollableColumnScreen(
        padding = PaddingValues(horizontal = 20.dp)
    ) {
        TopBar(
            title = stringResource(R.string.statistics)
        )
        Spacer(24.dp)
        SelectionCard(
            modifier = Modifier.fillMaxWidth(),
            selectedIndex = uiState.timeRange.ordinal,
            values = DataTimeRange.names(context),
            onSelected = { index ->
                onAction(StatisticsAction.SetTimeRange(DataTimeRange.entries[index]))
            }
        )
        Spacer(12.dp)
        if (uiState.reports.isEmpty()) {
            Spacer(48.dp)
            Text(
                text = stringResource(R.string.data_is_absent),
                style = robotoTextStyle(20.sp, FontWeight.Normal),
                color = colorTheme.colorTextDark
            )
        } else {
            ColumnChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .shadow(RoundedCornerShape(16.dp))
                    .background(colorTheme.colorBackground, RoundedCornerShape(16.dp)),
                yAxisTitle = stringResource(R.string.temperature),
                xAxisTitle = xTitle,
                yAxisValues = remember(xAxisValues, uiState.reports) { getYAxisValues(ReportInfo.TEMPERATURE, uiState.reports, xAxisValues) },
                xAxisValues = xAxisValues,
                columnColor = colorTheme.colorTemperatures
            )
            ColumnChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .shadow(RoundedCornerShape(16.dp))
                    .background(colorTheme.colorBackground, RoundedCornerShape(16.dp)),
                yAxisTitle = stringResource(R.string.humidity),
                xAxisTitle = xTitle,
                yAxisValues = remember(xAxisValues, uiState.reports) { getYAxisValues(ReportInfo.HUMIDITY, uiState.reports, xAxisValues) },
                xAxisValues = xAxisValues,
                columnColor = colorTheme.colorHumidities
            )
            ColumnChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .shadow(RoundedCornerShape(16.dp))
                    .background(colorTheme.colorBackground, RoundedCornerShape(16.dp)),
                yAxisTitle = stringResource(R.string.pressure),
                xAxisTitle = xTitle,
                yAxisValues = remember(xAxisValues, uiState.reports) { getYAxisValues(ReportInfo.PRESSURE, uiState.reports, xAxisValues) },
                xAxisValues = xAxisValues,
                columnColor = colorTheme.colorPressures
            )
            ColumnChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .shadow(RoundedCornerShape(16.dp))
                    .background(colorTheme.colorBackground, RoundedCornerShape(16.dp)),
                yAxisTitle = stringResource(R.string.wind_speed),
                xAxisTitle = xTitle,
                yAxisValues = remember(xAxisValues, uiState.reports) { getYAxisValues(ReportInfo.WIND_SPEED, uiState.reports, xAxisValues) },
                xAxisValues = xAxisValues,
                columnColor = colorTheme.colorWindSpeeds
            )
            ColumnChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .shadow(RoundedCornerShape(16.dp))
                    .background(colorTheme.colorBackground, RoundedCornerShape(16.dp)),
                yAxisTitle = stringResource(R.string.wind_direction),
                xAxisTitle = xTitle,
                yAxisValues = remember(xAxisValues, uiState.reports) { getYAxisValues(ReportInfo.WIND_DEGREE, uiState.reports, xAxisValues) },
                xAxisValues = xAxisValues,
                columnColor = colorTheme.colorWindDegrees
            )
            ColumnChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .shadow(RoundedCornerShape(16.dp))
                    .background(colorTheme.colorBackground, RoundedCornerShape(16.dp)),
                yAxisTitle = stringResource(R.string.air_quality_index),
                xAxisTitle = xTitle,
                yAxisValues = remember(xAxisValues, uiState.reports) { getYAxisValues(ReportInfo.AQI, uiState.reports, xAxisValues) },
                xAxisValues = xAxisValues,
                columnColor = colorTheme.colorAQIs
            )
            ColumnChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .shadow(RoundedCornerShape(16.dp))
                    .background(colorTheme.colorBackground, RoundedCornerShape(16.dp)),
                yAxisTitle = stringResource(R.string.small_dust),
                xAxisTitle = xTitle,
                yAxisValues = remember(xAxisValues, uiState.reports) { getYAxisValues(ReportInfo.PM2_5, uiState.reports, xAxisValues) },
                xAxisValues = xAxisValues,
                columnColor = colorTheme.colorPM2point5s
            )
            ColumnChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .shadow(RoundedCornerShape(16.dp))
                    .background(colorTheme.colorBackground, RoundedCornerShape(16.dp)),
                yAxisTitle = stringResource(R.string.dust),
                xAxisTitle = xTitle,
                yAxisValues = remember(xAxisValues, uiState.reports) { getYAxisValues(ReportInfo.PM10, uiState.reports, xAxisValues) },
                xAxisValues = xAxisValues,
                columnColor = colorTheme.colorPM10s
            )
            ColumnChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .shadow(RoundedCornerShape(16.dp))
                    .background(colorTheme.colorBackground, RoundedCornerShape(16.dp)),
                yAxisTitle = stringResource(R.string.nitrogen),
                xAxisTitle = xTitle,
                yAxisValues = remember(xAxisValues, uiState.reports) { getYAxisValues(ReportInfo.NO2, uiState.reports, xAxisValues) },
                xAxisValues = xAxisValues,
                columnColor = colorTheme.colorNO2s
            )
            ColumnChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .shadow(RoundedCornerShape(16.dp))
                    .background(colorTheme.colorBackground, RoundedCornerShape(16.dp)),
                yAxisTitle = stringResource(R.string.ozone),
                xAxisTitle = xTitle,
                yAxisValues = remember(xAxisValues, uiState.reports) { getYAxisValues(ReportInfo.O3, uiState.reports, xAxisValues) },
                xAxisValues = xAxisValues,
                columnColor = colorTheme.colorO3s
            )
            ColumnChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .shadow(RoundedCornerShape(16.dp))
                    .background(colorTheme.colorBackground, RoundedCornerShape(16.dp)),
                yAxisTitle = stringResource(R.string.co),
                xAxisTitle = xTitle,
                yAxisValues = remember(xAxisValues, uiState.reports) { getYAxisValues(ReportInfo.CO, uiState.reports, xAxisValues) },
                xAxisValues = xAxisValues,
                columnColor = colorTheme.colorCOs
            )
            Spacer(12.dp)
            Spacer(112.dp)
        }
    }
}