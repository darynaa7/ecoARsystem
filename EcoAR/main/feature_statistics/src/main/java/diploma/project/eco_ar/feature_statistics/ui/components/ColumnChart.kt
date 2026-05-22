package diploma.project.eco_ar.feature_statistics.ui.components

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import diploma.project.eco_ar.core.ui.theme.robotoTextStyle
import diploma.project.eco_ar.feature_statistics.domain.XAxisValue
import diploma.project.eco_ar.feature_statistics.domain.YAxisValue
import kotlinx.collections.immutable.ImmutableList

@Composable
fun ColumnChart(
    modifier: Modifier = Modifier,
    yAxisTitle: String,
    xAxisTitle: String,
    yAxisValues: ImmutableList<YAxisValue>,
    xAxisValues: ImmutableList<XAxisValue>,
    textColor: Color = Color.Black,
    columnColor: Color
) {
    val titleTextStyle = robotoTextStyle(16.sp)
    val defaultTextStyle = robotoTextStyle(12.sp)
    val smallerTextStyle = robotoTextStyle(10.sp)
    val evenSmallerTextStyle = robotoTextStyle(8.sp)

    val textMeasurer = rememberTextMeasurer()
    val yAxisTextLayout = textMeasurer.measure(
        text = yAxisTitle,
        style = titleTextStyle
    )
    val xAxisTextLayout = textMeasurer.measure(
        text = xAxisTitle,
        style = titleTextStyle
    )

    val data = remember(xAxisValues, yAxisValues) { xAxisValues.zip(yAxisValues) }
    if (data.isEmpty()) return

    Log.d("TAG", "$data $xAxisValues $yAxisValues")

    val maxYValue = remember(data) { data.maxOf { it.second.value } }

    val dataTextLayouts = remember(data) {
        data.map { (x, y) ->
            Pair(
                textMeasurer.measure(
                    text = x.label,
                    style = if (xAxisValues.size > 20) evenSmallerTextStyle else defaultTextStyle
                ),
                textMeasurer.measure(
                    text = y.label,
                    style = if (xAxisValues.size > 20) smallerTextStyle else defaultTextStyle
                )
            )
        }
    }

    Canvas(
        modifier = modifier.height(240.dp)
    ) {
        val leftColumnSize = Size(size.width * 0.08f, size.height * 0.9f)
        val bottomColumnSize = Size(size.width * 0.9f, size.height * 0.1f)

        // Background
        drawRoundRect(
            color = columnColor,
            cornerRadius = CornerRadius(12.dp.toPx(), 12.dp.toPx()),
            alpha = 0.1f,
        )

        inset(
            left = 0f,
            top = yAxisTextLayout.size.height * 1.5f,
            right = 0f,
            bottom = 0f
        ) {
            // Axis Lines
            drawLine(
                color = textColor,
                start = Offset(leftColumnSize.width, -yAxisTextLayout.size.height * 1.5f),
                end = Offset(leftColumnSize.width, size.height),
                strokeWidth = 3f
            )
            drawLine(
                color = textColor,
                start = Offset(0f, size.height - bottomColumnSize.height),
                end = Offset(size.width, size.height - bottomColumnSize.height),
                strokeWidth = 3f
            )

            // Axis Texts
            rotate(
                degrees = -90f,
                pivot = leftColumnSize.center - Offset(0f, bottomColumnSize.height)
            ) {
                drawText(
                    textLayoutResult = yAxisTextLayout,
                    topLeft = Offset(
                        x = leftColumnSize.width / 2f - yAxisTextLayout.size.width / 2f,
                        y = leftColumnSize.height / 2f - yAxisTextLayout.size.height / 2f - bottomColumnSize.height
                    ),
                    color = textColor
                )
            }

            drawText(
                textLayoutResult = xAxisTextLayout,
                topLeft = Offset(
                    x = leftColumnSize.width + bottomColumnSize.width / 2f - xAxisTextLayout.size.width / 2f,
                    y = -xAxisTextLayout.size.height.toFloat()
                ),
                color = textColor
            )

            // Data and Data Texts
            inset(
                left = leftColumnSize.width * 1.25f,
                top = 0f,
                right = leftColumnSize.width * 0.25f,
                bottom = bottomColumnSize.height
            ) {
                val columnWidth = size.width / (data.size + 1)
                val columnHorizontalPadding = columnWidth / data.size / 2f

                data.forEachIndexed { index, (x, y) ->
                    val columnHeight = size.height * 0.9f * y.value / maxYValue
                    val columnX = columnHorizontalPadding + index * (columnHorizontalPadding * 2f + columnWidth)

                    val xTextLayout = dataTextLayouts[index].first
                    val yTextLayout = dataTextLayouts[index].second

                    val xTextPosition = Offset(
                        x = columnX + columnWidth / 2f - xTextLayout.size.width / 2f,
                        y = size.height + bottomColumnSize.height / 2f - xTextLayout.size.height / 2f
                    )
                    val yTextPosition = Offset(
                        x = columnX + columnWidth / 2f - yTextLayout.size.width / 2f,
                        y = size.height - columnHeight - 8f - yTextLayout.size.height
                    )

                    drawRect(
                        color = columnColor,
                        topLeft = Offset(
                            x = columnX,
                            y = size.height - columnHeight
                        ),
                        size = Size(
                            width = columnWidth,
                            height = columnHeight
                        )
                    )

                    if (x.value != 0f) {
                        drawText(
                            textLayoutResult = xTextLayout,
                            topLeft = xTextPosition,
                            color = textColor
                        )
                    }
                    if (y.value != 0f) {
                        drawText(
                            textLayoutResult = yTextLayout,
                            topLeft = yTextPosition,
                            color = textColor
                        )
                    }
                }
            }
        }
    }
}