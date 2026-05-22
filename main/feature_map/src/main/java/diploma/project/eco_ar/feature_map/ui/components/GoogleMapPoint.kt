package diploma.project.eco_ar.feature_map.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLocale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMapComposable
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.rememberUpdatedMarkerState
import diploma.project.eco_ar.core.domain.model.AirQualityReport
import diploma.project.eco_ar.core.ui.components.miscellaneous.Spacer
import diploma.project.eco_ar.core.ui.modifier.shadow
import diploma.project.eco_ar.core.ui.theme.LocalColorTheme
import diploma.project.eco_ar.core.ui.theme.robotoTextStyle
import diploma.project.eco_ar.feature_map.R

@Composable
@GoogleMapComposable
fun GoogleMapPoint(
    latitude: Double,
    longitude: Double,
    report: AirQualityReport,
    onClose: () -> Unit
) {
    val context = LocalContext.current
    val colorTheme = LocalColorTheme.current

    val state = rememberUpdatedMarkerState(LatLng(latitude, longitude))

    MarkerComposable(
        latitude, longitude, report,
        state = state
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .widthIn(min = 240.dp)
                .width(IntrinsicSize.Min)
                .shadow(RoundedCornerShape(16.dp))
                .drawBehind {
                    val path = Path().apply {
                        moveTo((size.width - 20.dp.toPx()) / 2, size.height)
                        lineTo((size.width + 20.dp.toPx()) / 2, size.height)
                        lineTo(size.width / 2, size.height + 16.dp.toPx())
                        close()
                    }

                    drawPath(
                        path = path,
                        color = colorTheme.colorBackground
                    )
                }
                .clip(RoundedCornerShape(16.dp))
                .background(colorTheme.colorBackground)
                .combinedClickable(
                    onClick = {},
                    onLongClick = onClose
                )
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(R.string.location, report.location),
                style = robotoTextStyle(16.sp),
                color = colorTheme.colorTextDark
            )
            Text(
                text = String.format(
                    LocalLocale.current.platformLocale,
                    stringResource(R.string.coordinates_4f_4f),
                    latitude, longitude
                ),
                style = robotoTextStyle(16.sp),
                color = colorTheme.colorTextDark
            )
            Text(
                text = stringResource(
                    R.string.pollution_level,
                    report.aqi
                ),
                style = robotoTextStyle(16.sp),
                color = colorTheme.colorTextDark
            )
            Text(
                text = stringResource(
                    R.string.air_temperature,
                    report.temperature
                ),
                style = robotoTextStyle(16.sp),
                color = colorTheme.colorTextDark
            )
            Text(
                text = stringResource(
                    R.string.level_of_humidity,
                    report.humidity
                ),
                style = robotoTextStyle(16.sp),
                color = colorTheme.colorTextDark
            )
            Text(
                text = "Швидкість вітру: ${report.windSpeed} м/с",
                style = robotoTextStyle(16.sp),
                color = colorTheme.colorTextDark
            )
            Spacer(4.dp)
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(report.getAirQuality().color, CircleShape)
                )
                Text(
                    text = report.getAirQuality().toString(context),
                    style = robotoTextStyle(16.sp),
                    color = colorTheme.colorTextDark
                )
            }
        }
    }
}