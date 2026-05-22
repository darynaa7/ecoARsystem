package diploma.project.eco_ar.feature_profile.ui.viewModel.reports

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import diploma.project.eco_ar.core.data.mappers.ReportMapper
import diploma.project.eco_ar.core.domain.string.StringProvider
import diploma.project.eco_ar.feature_profile.R
import diploma.project.eco_ar.core.data.repository.ReportsRepository
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

@OptIn(FlowPreview::class)
class ReportsViewModel(
    private val reportsRepository: ReportsRepository,
    private val reportMapper: ReportMapper,
    private val stringProvider: StringProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReportsUiState())
    val uiState = _uiState.asStateFlow()

    private val _events = Channel<ReportsEvent>()
    val events = _events.receiveAsFlow()

    init {
        viewModelScope.launch {
            reportsRepository.getReports()
                .onSuccess { reports ->
                    _uiState.update {
                        it.copy(
                            reports = reports.toImmutableList()
                        )
                    }
                }
        }
    }

    fun onAction(action: ReportsAction) {
        when (action) {
            is ReportsAction.ExportToPDF -> exportToPDF(action.context)
            is ReportsAction.ExportToCSV -> exportToCSV(action.context)
        }
    }

    // Maybe export texts to resources
    private fun exportToPDF(context: Context) {
        try {
            val pdfDocument = PdfDocument()
            val titlePaint = Paint().apply { isFakeBoldText = true; textSize = 18f; color = Color.BLACK }
            val headerPaint = Paint().apply { isFakeBoldText = true; textSize = 14f; color = Color.DKGRAY }
            val textPaint = Paint().apply { textSize = 12f; color = Color.BLACK }

            val pageWith = 595
            val pageHeight = 842
            var pageNumber = 1

            var pageInfo = PdfDocument.PageInfo.Builder(pageWith, pageHeight, pageNumber).create()
            var page = pdfDocument.startPage(pageInfo)
            var canvas = page.canvas
            var yPosition = 50f

            canvas.drawText("Детальний звіт по якості повітря", 40f, yPosition, titlePaint)
            yPosition += 40f

            uiState.value.reports.forEach { report ->
                if (yPosition > 720f) {
                    pdfDocument.finishPage(page)
                    pageNumber++
                    pageInfo = PdfDocument.PageInfo.Builder(pageWith, pageHeight, pageNumber).create()
                    page = pdfDocument.startPage(pageInfo)
                    canvas = page.canvas
                    yPosition = 50f
                }

                canvas.drawText("Звіт #${report.id} від ${report.dateTimeFormatted()}", 40f, yPosition, headerPaint)
                yPosition += 20f

                canvas.drawText(
                    "Loc: ${report.coordinates.first}, ${report.coordinates.second} | Temp: ${report.temperature}°C | Hum: ${report.humidity}%",
                    50f, yPosition, textPaint
                )
                yPosition += 18f

                canvas.drawText(
                    "AQI: ${report.aqi} | PM2.5: ${report.pm2point5} | PM10: ${report.pm10} | CO: ${report.co}",
                    50f, yPosition, textPaint
                )
                yPosition += 18f

                canvas.drawText(
                    "NO2: ${report.no2} | O3: ${report.o3} | Wind: ${report.windSpeed}m/s (${report.windDegrees}°)",
                    50f, yPosition, textPaint
                )
                yPosition += 30f
            }

            pdfDocument.finishPage(page)

            val fileName = "reports_${System.currentTimeMillis()}.csv"
            val tempFile = File(context.cacheDir, fileName)
            pdfDocument.writeTo(tempFile.outputStream())
            pdfDocument.close()

            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                tempFile
            )

            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "application/pdf"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            val chooserIntent = Intent.createChooser(intent, stringProvider.provide(R.string.send_report_via))
            context.startActivity(chooserIntent)

        } catch (e: Exception) {
            _events.trySend(ReportsEvent.ExportToPDFFailure(e.message))
        }
    }

    private fun exportToCSV(context: Context) {
        try {
            val csvData = reportMapper.formatForCSV(uiState.value.reports)
            val fileName = "reports_${System.currentTimeMillis()}.csv"

            val tempFile = File(context.cacheDir, fileName)
            tempFile.writeText(csvData)

            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                tempFile
            )

            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/csv"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            val chooserIntent = Intent.createChooser(intent, stringProvider.provide(R.string.send_report_via))

            context.startActivity(chooserIntent)
        } catch (e: Exception) {
            _events.trySend(ReportsEvent.ExportToCSVFailure(e.message))
        }
    }
}