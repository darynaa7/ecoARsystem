package diploma.project.eco_ar.feature_statistics.domain

import androidx.compose.runtime.Immutable
import diploma.project.eco_ar.core.domain.model.Report
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList

@Immutable
data class YAxisValue(
    val value: Float,
    val label: String
) {
    companion object {
        fun getYAxisValues(
            reportInfo: ReportInfo,
            reports: List<Report>,
            xAxisValues: List<XAxisValue>
        ): ImmutableList<YAxisValue> {
            return xAxisValues
                .map { xAxis ->
                    val reportsInPoint = reports.filter { report ->
                        xAxis.datePredicate(report.dateTime)
                    }

                    val yValue = if (reportsInPoint.isNotEmpty()) {
                        reportsInPoint
                            .map { report ->
                                when (reportInfo) {
                                    ReportInfo.TEMPERATURE -> report.temperature
                                    ReportInfo.HUMIDITY -> report.humidity.toFloat()
                                    ReportInfo.PRESSURE -> report.pressure.toFloat()
                                    ReportInfo.WIND_SPEED -> report.windSpeed
                                    ReportInfo.WIND_DEGREE -> report.windDegrees.toFloat()
                                    ReportInfo.AQI -> report.aqi.toFloat()
                                    ReportInfo.PM2_5 -> report.pm2point5
                                    ReportInfo.PM10 -> report.pm10
                                    ReportInfo.NO2 -> report.no2
                                    ReportInfo.O3 -> report.o3
                                    ReportInfo.CO -> report.co
                                }
                            }
                            .average()
                            .toFloat()
                    } else {
                        0f
                    }

                    YAxisValue(value = yValue, label = "%.02f".format(yValue))
                }
                .toPersistentList()
        }
    }
}