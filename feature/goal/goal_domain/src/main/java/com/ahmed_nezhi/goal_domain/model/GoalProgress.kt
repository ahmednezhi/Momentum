package com.ahmed_nezhi.goal_domain.model

import com.ahmed_nezhi.common.enums.GoalStatus

data class GoalProgress(
    val goalId: Int,
    val photoUrl: String,
    val date: String,
    val description: String,
    val status: GoalStatus
)
