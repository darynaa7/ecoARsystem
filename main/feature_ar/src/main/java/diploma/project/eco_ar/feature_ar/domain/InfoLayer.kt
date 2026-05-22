package diploma.project.eco_ar.feature_ar.domain

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import diploma.project.eco_ar.feature_ar.R
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList

enum class InfoLayer(val optimalMin: Float?, val optimalMax: Float?) {
    TEMPERATURE(18f, 24f),
    HUMIDITY(40f, 60f),
    PRESSURE(1013f, 1020f),
    WIND_SPEED(0f, 5f),
    WIND_DEGREE(null, null),
    AQI(100f, 200f),
    PM2_5(0f, 5f),
    PM10(0f, 15f),
    NO2(0f, 25f),
    O3(0f, 100f),
    CO(0f, 40f);

    companion object {
        @Composable
        fun names(): ImmutableList<Pair<String, String>> {
            val context = LocalContext.current

            return remember(context) {
                entries.map { Pair(it.toString(context), it.getDescription(context)) }.toPersistentList()
            }
        }
    }

    fun toString(context: Context): String {
        return when (this) {
            TEMPERATURE -> context.getString(R.string.temperature)
            HUMIDITY -> context.getString(R.string.humidity)
            PRESSURE -> context.getString(R.string.pressure)
            WIND_SPEED -> context.getString(R.string.wind_speed)
            WIND_DEGREE -> context.getString(R.string.wind_degree)
            AQI -> context.getString(R.string.aqi)
            PM2_5 -> context.getString(R.string.pm2_5)
            PM10 -> context.getString(R.string.pm10)
            NO2 -> context.getString(R.string.no2)
            O3 -> context.getString(R.string.ozone)
            CO -> context.getString(R.string.co)
        }
    }

    fun getDescription(context: Context): String {
        return when (this) {
            TEMPERATURE -> context.getString(R.string.temperature_desc)
            HUMIDITY -> context.getString(R.string.humidity_desc)
            PRESSURE -> context.getString(R.string.pressure_desc)
            WIND_SPEED -> context.getString(R.string.wind_speed_desc)
            WIND_DEGREE -> context.getString(R.string.wind_degree_desc)
            AQI -> context.getString(R.string.aqi_desc)
            PM2_5 -> context.getString(R.string.pm2_5_desc)
            PM10 -> context.getString(R.string.pm10_desc)
            NO2 -> context.getString(R.string.no2_desc)
            O3 -> context.getString(R.string.ozone_desc)
            CO -> context.getString(R.string.co_desc)
        }
    }
}