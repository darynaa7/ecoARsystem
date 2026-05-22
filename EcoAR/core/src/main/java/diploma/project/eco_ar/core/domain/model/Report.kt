package diploma.project.eco_ar.core.domain.model

import androidx.compose.runtime.Immutable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Immutable
data class Report(
    val id: String,
    val coordinates: Pair<Double, Double>,
    val temperature: Float,
    val humidity: Int,
    val pressure: Int,
    val windSpeed: Float,
    val windDegrees: Int,
    val aqi: Int,
    val pm2point5: Float,
    val pm10: Float,
    val no2: Float,
    val o3: Float,
    val co: Float,
    val dateTime: LocalDateTime
) {
    fun dateTimeFormatted(): String {
        return dateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
    }

    fun getAirQuality(): AirQuality {
        return when (aqi) {
            in 1..2 -> AirQuality.CLEAN
            3 -> AirQuality.MEDIUM
            4 -> AirQuality.BAD
            else -> AirQuality.POLLUTED
        }
    }

    fun getRecommendations(): List<String> {
        return when (aqi) {
            1 -> listOf(
                "Ідеальний час для активних занять спортом на свіжому повітрі.",
                "Можна сміливо провітрювати приміщення.",
                "Жодних обмежень для чутливих груп людей (діти, люди похилого віку)."
            )
            2 -> listOf(
                "Гарний час для прогулянок, але дуже чутливим людям варто зменшити інтенсивні фізичні навантаження.",
                "Провітрювати приміщення рекомендується, але краще уникати годин пік біля доріг.",
                "Якщо ви відчуваєте подразнення горла, зменште час перебування на вулиці."
            )
            3 -> listOf(
                "Людям з респіраторними захворюваннями (наприклад, астмою) варто обмежити тривале перебування на вулиці.",
                "Закрийте вікна, якщо ви живете поблизу жвавих магістралей або заводів.",
                "Спорт краще перенести в закрите приміщення з кондиціонуванням."
            )
            4 -> listOf(
                "Уникайте тривалого перебування та фізичних вправ на відкритому повітрі.",
                "Тримайте вікна зачиненими, щоб не допустити потрапляння забрудненого повітря в дім.",
                "Використовуйте очищувач повітря в приміщенні, якщо він є."
            )
            else -> listOf(
                "Залишайтеся в приміщенні та максимально обмежте будь-яку активність на вулиці.",
                "Обов'язково використовуйте маску (типу N95/FFP2), якщо вам необхідно вийти назовні.",
                "Не провітрюйте приміщення; увімкніть режим рециркуляції повітря в системі вентиляції."
            )
        }
    }
}