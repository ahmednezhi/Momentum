package com.ahmed_nezhi.ui.timeline

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.ahmed_nezhi.common.enums.GoalStatus
import com.ahmed_nezhi.ui.R
import com.ahmed_nezhi.ui.theme.Gray200
import com.ahmed_nezhi.ui.theme.Green500
import com.ahmed_nezhi.ui.theme.Orange500
import com.ahmed_nezhi.ui.timeline.defaults.CircleParametersDefaults
import com.ahmed_nezhi.ui.timeline.defaults.LineParametersDefaults
import com.ahmed_nezhi.ui.timeline.models.LineParameters
import com.ahmed_nezhi.ui.timeline.models.StrokeParameters
import com.ahmed_nezhi.ui.timeline.models.TimeLineData
import com.ahmed_nezhi.ui.timeline.models.TimelineNodePosition

@Composable
fun LazyGoalTimeline(goals: List<TimeLineData>) {
    LazyColumn(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        content = {
            itemsIndexed(goals) { index, goalTimeline ->
                TimelineNode(
                    position = mapToTimelineNodePosition(index, goals.size),
                    circleParameters = CircleParametersDefaults.circleParameters(
                        backgroundColor = getIconColor(goalTimeline),
                        stroke = getIconStrokeColor(goalTimeline),
                        icon = getIcon(goalTimeline)
                    ),
                    lineParameters = getLineBrush(
                        circleRadius = 12.dp,
                        index = index,
                        items = goals
                    ),
                    contentStartOffset = 16.dp,
                    spacer = 24.dp
                ) { modifier ->
                    GoalMessage(goalTimeline, modifier)
                }
            }
        },
        contentPadding = PaddingValues(16.dp)
    )
}

@Composable
private fun GoalMessage(goalTimeline: TimeLineData, modifier: Modifier) {
    var showFullScreenImage by remember { mutableStateOf(false) }
    val uri = Uri.parse(goalTimeline.photoUrl)
    Column(modifier = modifier) {
        Text(text = goalTimeline.goalName, style = MaterialTheme.typography.labelSmall)
        Text(text = goalTimeline.goalDescription, style = MaterialTheme.typography.bodyMedium)
        AsyncImage(
            model = uri,
            contentDescription = goalTimeline.goalName,
            modifier = Modifier
                .size(200.dp)
                .padding(8.dp)
                .clickable { showFullScreenImage = true }
        )
        Text(text = goalTimeline.date, style = MaterialTheme.typography.bodyMedium)
    }

    if (showFullScreenImage) {
        Dialog(onDismissRequest = { showFullScreenImage = false }) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
                    .clickable { showFullScreenImage = false },
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = uri,
                    contentDescription = goalTimeline.goalName,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

@Composable
private fun getLineBrush(
    circleRadius: Dp,
    index: Int,
    items: List<TimeLineData>
): LineParameters? {
    return if (index != items.lastIndex) {
        val currentGoal = items[index]
        val nextGoal = items[index + 1]
        val circleRadiusInPx = with(LocalDensity.current) { circleRadius.toPx() }
        LineParametersDefaults.linearGradient(
            strokeWidth = 3.dp,
            startColor = (getIconStrokeColor(currentGoal)?.color ?: getIconColor(currentGoal)),
            endColor = (getIconStrokeColor(nextGoal)?.color ?: getIconColor(items[index + 1])),
            startY = circleRadiusInPx * 2
        )
    } else {
        null
    }
}

private fun getIconColor(goalTimeline: TimeLineData): Color {
    return when (goalTimeline.status) {
        GoalStatus.COMPLETED -> Green500
        GoalStatus.IN_PROGRESS -> Orange500
        GoalStatus.UPCOMING -> Color.White
    }
}

private fun getIconStrokeColor(goalTimeline: TimeLineData): StrokeParameters? {
    return if (goalTimeline.status == GoalStatus.UPCOMING) {
        StrokeParameters(color = Gray200, width = 2.dp)
    } else {
        null
    }
}

@Composable
private fun getIcon(goalTimeline: TimeLineData): Int? {
    return if (goalTimeline.status == GoalStatus.IN_PROGRESS) {
        R.drawable.baseline_done_24
    } else {
        null
    }
}

private fun mapToTimelineNodePosition(index: Int, collectionSize: Int) = when (index) {
    0 -> TimelineNodePosition.FIRST
    collectionSize - 1 -> TimelineNodePosition.LAST
    else -> TimelineNodePosition.MIDDLE
}

@Preview(showBackground = true)
@Composable
private fun TimeLineComponentPreview() {
    val goals = listOf(
        TimeLineData(
            "Goal 1",
            "Description of Goal 1",
            "url1.jpg",
            "2025-02-01",
            GoalStatus.COMPLETED
        ),
        TimeLineData(
            "Goal 2",
            "Description of Goal 2",
            "url2.jpg",
            "2025-02-02",
            GoalStatus.IN_PROGRESS
        ),
        TimeLineData(
            "Goal 3",
            "Description of Goal 3",
            "url3.jpg",
            "2025-02-03",
            GoalStatus.UPCOMING
        )
    )
    LazyGoalTimeline(goals = goals)
}
