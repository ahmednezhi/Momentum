package com.ahmed_nezhi.ui.timeline

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ahmed_nezhi.ui.R
import com.ahmed_nezhi.ui.theme.Coral
import com.ahmed_nezhi.ui.theme.InterviewProjectTheme
import com.ahmed_nezhi.ui.theme.LightBlue
import com.ahmed_nezhi.ui.theme.LightCoral
import com.ahmed_nezhi.ui.theme.Purple
import com.ahmed_nezhi.ui.timeline.defaults.CircleParametersDefaults
import com.ahmed_nezhi.ui.timeline.defaults.LineParametersDefaults
import com.ahmed_nezhi.ui.timeline.models.CircleParameters
import com.ahmed_nezhi.ui.timeline.models.LineParameters
import com.ahmed_nezhi.ui.timeline.models.StrokeParameters
import com.ahmed_nezhi.ui.timeline.models.TimelineNodePosition

@Composable
fun TimelineNode(
    position: TimelineNodePosition,
    circleParameters: CircleParameters,
    lineParameters: LineParameters? = null,
    contentStartOffset: Dp = 16.dp,
    spacer: Dp = 32.dp,
    content: @Composable BoxScope.(modifier: Modifier) -> Unit
) {
    val iconPainter = circleParameters.icon?.let { painterResource(id = it) }
    Box(
        modifier = Modifier
            .wrapContentSize()
            .drawBehind {
                val circleRadiusInPx = circleParameters.radius.toPx()

                lineParameters?.let {
                    drawLine(
                        brush = it.brush,
                        start = Offset(x = circleRadiusInPx, y = circleRadiusInPx * 2),
                        end = Offset(x = circleRadiusInPx, y = this.size.height),
                        strokeWidth = it.strokeWidth.toPx()
                    )
                }

                drawCircle(
                    circleParameters.backgroundColor,
                    circleRadiusInPx,
                    center = Offset(x = circleRadiusInPx, y = circleRadiusInPx)
                )

                circleParameters.stroke?.let { stroke ->
                    val strokeWidthInPx = stroke.width.toPx()
                    drawCircle(
                        color = stroke.color,
                        radius = circleRadiusInPx - strokeWidthInPx / 2,
                        center = Offset(x = circleRadiusInPx, y = circleRadiusInPx),
                        style = Stroke(width = strokeWidthInPx)
                    )
                }

                iconPainter?.let { painter ->
                    this.withTransform(
                        transformBlock = {
                            translate(
                                left = circleRadiusInPx - painter.intrinsicSize.width / 2f,
                                top = circleRadiusInPx - painter.intrinsicSize.height / 2f
                            )
                        },
                        drawBlock = {
                            this.drawIntoCanvas {
                                with(painter) {
                                    draw(intrinsicSize)
                                }
                            }
                        })
                }
            }
    ) {
        content(
            Modifier
                .defaultMinSize(minHeight = circleParameters.radius * 2)
                .padding(
                    start = circleParameters.radius * 2 + contentStartOffset,
                    bottom = if (position != TimelineNodePosition.LAST) spacer else 0.dp
                )
        )
    }
}

@Composable
private fun MessageBubble(modifier: Modifier, containerColor: Color) {
    Card(
        modifier = modifier
            .width(200.dp)
            .height(100.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {}
}

@Preview(showBackground = true)
@Composable
private fun TimelinePreview() {
    InterviewProjectTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            TimelineNode(
                position = TimelineNodePosition.FIRST,
                circleParameters = CircleParametersDefaults.circleParameters(
                    backgroundColor = LightBlue
                ),
                lineParameters = LineParametersDefaults.linearGradient(
                    startColor = LightBlue,
                    endColor = Purple
                )
            ) { modifier -> MessageBubble(modifier, containerColor = LightBlue) }

            TimelineNode(
                position = TimelineNodePosition.MIDDLE,
                circleParameters = CircleParametersDefaults.circleParameters(
                    backgroundColor = Purple
                ),
                contentStartOffset = 16.dp,
                lineParameters = LineParametersDefaults.linearGradient(
                    startColor = Purple,
                    endColor = Coral
                )
            ) { modifier -> MessageBubble(modifier, containerColor = Purple) }

            TimelineNode(
                TimelineNodePosition.LAST,
                circleParameters = CircleParametersDefaults.circleParameters(
                    backgroundColor = LightCoral,
                    stroke = StrokeParameters(color = Coral, width = 2.dp),
                    icon = R.drawable.baseline_done_24
                )
            ) { modifier -> MessageBubble(modifier, containerColor = Coral) }
        }
    }
}
