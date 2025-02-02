package com.ahmed_nezhi.ui.timeline.models

import com.ahmed_nezhi.common.enums.GoalStatus

data class TimeLineData(
    val goalName: String,
    val goalDescription: String,
    val photoUrl: String,
    val date: String,
    val status: GoalStatus
)
