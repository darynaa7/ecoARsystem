package diploma.project.eco_ar.feature_map.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.PlaceTypes
import diploma.project.eco_ar.core.R
import diploma.project.eco_ar.core.ui.components.textField.SearchTextField
import diploma.project.eco_ar.core.ui.modifier.shadow
import diploma.project.eco_ar.core.ui.theme.LocalColorTheme
import diploma.project.eco_ar.core.ui.theme.interTextStyle
import kotlinx.collections.immutable.ImmutableList

@Composable
fun PredictionsSearchTextField(
    modifier: Modifier = Modifier,
    text: String,
    predictions: ImmutableList<AutocompletePrediction>?,
    onPredictionClick: (AutocompletePrediction) -> Unit,
    onTextChanged: (String) -> Unit
) {
    val colorTheme = LocalColorTheme.current

    Column(
        modifier = modifier.shadow(RoundedCornerShape(12.dp))
    ) {
        SearchTextField(
            modifier = Modifier.fillMaxWidth(),
            text = text,
            shape = if (predictions == null) {
                RoundedCornerShape(12.dp)
            } else {
                RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
            },
            onTextChanged = onTextChanged
        )
        predictions?.let {
            if (predictions.isEmpty()) {
                val shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape)
                        .background(colorTheme.colorWhite)
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(diploma.project.eco_ar.feature_map.R.string.no_cities_found),
                        style = interTextStyle(20.sp),
                        color = colorTheme.colorTextDark
                    )
                }
            } else {
                predictions.forEachIndexed { index, prediction ->
                    val shape = if (index == predictions.lastIndex) {
                        RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
                    } else {
                        RectangleShape
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(shape)
                            .background(colorTheme.colorWhite)
                            .clickable(
                                onClick = {
                                    onPredictionClick(prediction)
                                }
                            )
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = Modifier.size(20.dp),
                            imageVector = ImageVector.vectorResource(
                                id = when {
                                    PlaceTypes.COUNTRY in prediction.types -> R.drawable.icon_globe
                                    else -> R.drawable.icon_city
                                }),
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds,
                            colorFilter = ColorFilter.tint(colorTheme.colorTextDark)
                        )
                        Text(
                            text = prediction.getFullText(null).toString(),
                            style = interTextStyle(20.sp),
                            color = colorTheme.colorTextDark
                        )
                    }
                }
            }
        }
    }
}