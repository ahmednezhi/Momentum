package com.ahmed_nezhi.goal_presentation.details

import com.ahmed_nezhi.goal_domain.model.GoalProgress
import com.ahmed_nezhi.ui.timeline.models.TimeLineData

fun GoalProgress.toTimeLineData(goalName: String, goalDescription: String): TimeLineData {
    return TimeLineData(
        goalName = goalName,
        goalDescription = description,
        photoUrl = this.photoUrl,
        date = this.date,
        status = this.status
    )
}

fun List<GoalProgress>.toTimeLineDataList(goalName: String, goalDescription: String): List<TimeLineData> {
    return this.map { progress ->
        TimeLineData(
            goalName = goalName,
            goalDescription = progress.description,
            photoUrl = progress.photoUrl,
            date = progress.date,
            status = progress.status
        )
    }
}
