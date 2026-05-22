package diploma.project.eco_ar.core.data.repository

import android.util.Log
import diploma.project.eco_ar.core.data.common.errors.parseAsError
import diploma.project.eco_ar.core.data.mappers.ReportMapper
import diploma.project.eco_ar.core.data.remote.dto.ReportDTO
import diploma.project.eco_ar.core.data.remote.dto.requests.LocationRequestDTO
import diploma.project.eco_ar.core.domain.miscellaneous.authFailure
import diploma.project.eco_ar.core.domain.miscellaneous.emptySuccess
import diploma.project.eco_ar.core.domain.model.Report
import diploma.project.eco_ar.core.domain.string.StringProvider
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

class ReportsRepository(
    private val httpClient: HttpClient,
    private val stringProvider: StringProvider,
    private val userDataRepository: UserDataRepository,
    private val reportMapper: ReportMapper
) {

    private var _cachedReports: List<Report>? = null

    suspend fun getReports(): Result<List<Report>> {
        val savedToken = userDataRepository.getToken() ?: return Result.authFailure(stringProvider)

        return withContext(Dispatchers.IO) {
            try {
                val response = httpClient.get("/environment/user/history") {
                    contentType(ContentType.Application.Json)
                    bearerAuth(savedToken)
                }

                if (response.status == HttpStatusCode.OK) {
                    val reportsResponseDTO = response.body<List<ReportDTO>>()
                    val reports = reportsResponseDTO.map { reportMapper.dtoToModel(it) }

                    _cachedReports = reports

                    Log.d("TAG", "$reports")

                    Result.success(reports)
                } else {
                    response.parseAsError()
                }
            } catch (e: Exception) {
                e.printStackTrace()

                Result.failure(e)
            }
        }
    }

    suspend fun saveReport(report: Report): Result<Unit> {
        val savedToken = userDataRepository.getToken() ?: return Result.authFailure(stringProvider)

        return withContext(Dispatchers.IO) {
            try {
                val request = LocationRequestDTO(
                    latitude = report.coordinates.first,
                    longitude = report.coordinates.second
                )

                val response = httpClient.post("/environment/user/save") {
                    contentType(ContentType.Application.Json)
                    setBody(request)
                    bearerAuth(savedToken)
                }

                if (response.status == HttpStatusCode.OK) {
                    Result.emptySuccess()
                } else {
                    response.parseAsError()
                }
            } catch (e: Exception) {
                e.printStackTrace()

                Result.failure(e)
            }
        }
    }

    fun getLastWeekReports(): List<Report> {
        val lastWeek = LocalDateTime.now().minusDays(7)

        return _cachedReports
            ?.filter { it.dateTime >= lastWeek }
            ?.sortedByDescending { it.id }
            ?: emptyList()
    }

    fun getLastMonthReports(): List<Report> {
        val lastMonth = LocalDateTime.now().minusMonths(1)

        return _cachedReports
            ?.filter { it.dateTime >= lastMonth }
            ?.sortedByDescending { it.id }
            ?: emptyList()
    }

    fun getLastYearReports(): List<Report> {
        val lastYear = LocalDateTime.now().minusYears(1)

        return _cachedReports
            ?.filter { it.dateTime >= lastYear }
            ?.sortedByDescending { it.id }
            ?: emptyList()
    }

    fun clearCache() {
        _cachedReports = null
    }
}