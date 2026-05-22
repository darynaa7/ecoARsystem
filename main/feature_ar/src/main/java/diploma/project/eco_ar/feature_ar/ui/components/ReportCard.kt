package diploma.project.eco_ar.feature_ar.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import diploma.project.eco_ar.core.domain.model.Report
import diploma.project.eco_ar.core.ui.components.buttons.TextButton
import diploma.project.eco_ar.core.ui.components.card.Card
import diploma.project.eco_ar.core.ui.components.miscellaneous.Spacer
import diploma.project.eco_ar.core.ui.theme.LocalColorTheme
import diploma.project.eco_ar.core.ui.theme.robotoTextStyle

// TODO extract string resources

@Composable
fun ReportCard(
    modifier: Modifier = Modifier,
    report: Report,
    onSaveReport: () -> Unit
) {
    val colorTheme = LocalColorTheme.current

    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Координати: ${report.coordinates.first}, ${report.coordinates.second}",
                style = robotoTextStyle(16.sp),
                color = colorTheme.colorTextDark
            )
            Text(
                text = "Температура: ${report.temperature}",
                style = robotoTextStyle(16.sp),
                color = colorTheme.colorTextDark
            )
            Text(
                text = "Вологість: ${report.humidity}",
                style = robotoTextStyle(16.sp),
                color = colorTheme.colorTextDark
            )
            Text(
                text = "Тиск: ${report.pressure}",
                style = robotoTextStyle(16.sp),
                color = colorTheme.colorTextDark
            )
            Text(
                text = "Вітер: ${report.windSpeed} м/с, ${report.windDegrees}°",
                style = robotoTextStyle(16.sp),
                color = colorTheme.colorTextDark
            )
            Text(
                text = "AQI: ${report.aqi}",
                style = robotoTextStyle(16.sp),
                color = colorTheme.colorTextDark
            )
            Text(
                text = "PM2.5: ${report.pm2point5}",
                style = robotoTextStyle(16.sp),
                color = colorTheme.colorTextDark
            )
            Text(
                text = "PM10: ${report.pm10}",
                style = robotoTextStyle(16.sp),
                color = colorTheme.colorTextDark
            )
            Text(
                text = "NO₂: ${report.no2}",
                style = robotoTextStyle(16.sp),
                color = colorTheme.colorTextDark
            )
            Text(
                text = "O₃: ${report.o3}",
                style = robotoTextStyle(16.sp),
                color = colorTheme.colorTextDark
            )
            Text(
                text = "CO: ${report.co}",
                style = robotoTextStyle(16.sp),
                color = colorTheme.colorTextDark
            )
            Text(
                text = "Дата та час: ${report.dateTimeFormatted()}",
                style = robotoTextStyle(16.sp),
                color = colorTheme.colorTextDark
            )
            Spacer(8.dp)
            TextButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                text = "Зберегти у звіт",
                onClick = onSaveReport
            )
        }
    }
}