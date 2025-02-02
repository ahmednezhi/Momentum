package com.ahmed_nezhi.goal_data.mapper

import com.ahmed_nezhi.database.entity.GoalProgressEntity
import com.ahmed_nezhi.goal_domain.model.GoalProgress

fun GoalProgressEntity.toDomainModel() = GoalProgress(
    goalId = goalId,
    photoUrl = photoUrl,
    date = date,
    description = description,
    status = status
)

fun GoalProgress.toEntity() = GoalProgressEntity(
    goalId = goalId,
    photoUrl = photoUrl,
    date = date,
    description = description,
    status = status
)
