package diploma.project.eco_ar.core.data.mappers

import diploma.project.eco_ar.core.data.remote.dto.ReportDTO
import diploma.project.eco_ar.core.data.remote.dto.responses.LocationAirQualityDataResponseDTO
import diploma.project.eco_ar.core.data.remote.dto.responses.LocationWeatherDataResponseDTO
import diploma.project.eco_ar.core.domain.model.AirQualityReport
import diploma.project.eco_ar.core.domain.model.Report
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ReportMapper {

    fun dtoToModel(dto: LocationWeatherDataResponseDTO): Report {
        return Report(
            id = "",
            coordinates = Pair(dto.latitude, dto.longitude),
            temperature = dto.temperature,
            humidity = dto.humidity,
            pressure = dto.pressure,
            windSpeed = dto.windSpeed,
            windDegrees = dto.windDegrees,
            aqi = dto.aqi,
            pm2point5 = dto.pm2point5,
            pm10 = dto.pm10,
            no2 = dto.no2,
            o3 = dto.o3,
            co = dto.co,
            dateTime = Instant.parse(dto.createdAt)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
        )
    }

    fun dtoToModel(dto: ReportDTO): Report {
        return Report(
            id = dto.id,
            coordinates = Pair(dto.latitude, dto.longitude),
            temperature = dto.temperature,
            humidity = dto.humidity,
            pressure = dto.pressure,
            windSpeed = dto.windSpeed,
            windDegrees = dto.windDegrees,
            aqi = dto.aqi,
            pm2point5 = dto.pm2point5,
            pm10 = dto.pm10,
            no2 = dto.no2,
            o3 = dto.o3,
            co = dto.co,
            dateTime = Instant.parse(dto.createdAt)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
        )
    }

    fun dtoToModel(latitude: Double, longitude: Double, dto: LocationAirQualityDataResponseDTO): AirQualityReport {
        return AirQualityReport(
            location = "${dto.location.country}, ${dto.location.state}, ${dto.location.city}",
            coordinates = Pair(latitude, longitude),
            temperature = dto.weather.temperature.toFloat(),
            humidity = dto.weather.humidity,
            windSpeed = dto.weather.windSpeed,
            aqi = dto.pollution.aqi,
        )
    }

    fun formatForCSV(models: List<Report>): String {
        val sb = StringBuilder()

        sb.append("ID,Latitude,Longitude,Temperature,Humidity,Pressure,WindSpeed,WindDegrees,AQI,PM2,PM10,NO2,O3,CO,Date-Time\n")

        models.forEach { report ->
            sb.append("${report.id},")
            sb.append("${report.coordinates.first},")
            sb.append("${report.coordinates.second},")
            sb.append("${report.temperature},")
            sb.append("${report.humidity},")
            sb.append("${report.pressure},")
            sb.append("${report.windSpeed},")
            sb.append("${report.windDegrees},")
            sb.append("${report.aqi},")
            sb.append("${report.pm2point5},")
            sb.append("${report.pm10},")
            sb.append("${report.no2},")
            sb.append("${report.o3},")
            sb.append("${report.co},")
            sb.append("${report.dateTime}")
        }

        return sb.toString()
    }
}