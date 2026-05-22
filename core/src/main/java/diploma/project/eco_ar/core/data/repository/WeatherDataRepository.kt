package diploma.project.eco_ar.core.data.repository

import diploma.project.eco_ar.core.data.common.errors.parseAsError
import diploma.project.eco_ar.core.data.mappers.ReportMapper
import diploma.project.eco_ar.core.data.remote.dto.requests.LocationRequestDTO
import diploma.project.eco_ar.core.data.remote.dto.responses.LocationAirQualityDataResponseDTO
import diploma.project.eco_ar.core.data.remote.dto.responses.LocationWeatherDataResponseDTO
import diploma.project.eco_ar.core.domain.miscellaneous.authFailure
import diploma.project.eco_ar.core.domain.model.AirQualityReport
import diploma.project.eco_ar.core.domain.model.Report
import diploma.project.eco_ar.core.domain.string.StringProvider
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherDataRepository(
    private val httpClient: HttpClient,
    private val stringProvider: StringProvider,
    private val userDataRepository: UserDataRepository,
    private val reportMapper: ReportMapper
) {

    private val _cachedLocationData = mutableMapOf<Pair<Double, Double>, Report>()

    suspend fun getLocationWeatherData(latitude: Double, longitude: Double): Result<Report> {
        val savedToken = userDataRepository.getToken() ?: return Result.authFailure(stringProvider)

        _cachedLocationData[latitude to longitude]?.let { return Result.success(it) }

        return withContext(Dispatchers.IO) {
            try {
                val request = LocationRequestDTO(
                    latitude = latitude,
                    longitude = longitude
                )

                val response = httpClient.post("/environment/fetch") {
                    contentType(ContentType.Application.Json)
                    setBody(request)
                    bearerAuth(savedToken)
                }

                if (response.status == HttpStatusCode.OK) {
                    val locationWeatherDataResponseDTO = response.body<LocationWeatherDataResponseDTO>()
                    val report = reportMapper.dtoToModel(locationWeatherDataResponseDTO)

                    _cachedLocationData[latitude to longitude] = report

                    Result.success(report)
                } else {
                    response.parseAsError()
                }
            } catch (e: Exception) {
                e.printStackTrace()

                Result.failure(e)
            }
        }
    }

    suspend fun getLocationAirQualityData(latitude: Double, longitude: Double): Result<AirQualityReport> {
        val savedToken = userDataRepository.getToken() ?: return Result.authFailure(stringProvider)

        return withContext(Dispatchers.IO) {
            try {
                val request = LocationRequestDTO(
                    latitude = latitude,
                    longitude = longitude
                )

                val response = httpClient.post("/map/air-quality") {
                    contentType(ContentType.Application.Json)
                    setBody(request)
                    bearerAuth(savedToken)
                }

                if (response.status == HttpStatusCode.OK) {
                    val locationAirQualityDataResponseDTO = response.body<LocationAirQualityDataResponseDTO>()
                    val report = reportMapper.dtoToModel(latitude, longitude, locationAirQualityDataResponseDTO)

                    Result.success(report)
                } else {
                    response.parseAsError()
                }
            } catch (e: Exception) {
                e.printStackTrace()

                Result.failure(e)
            }
        }
    }
}