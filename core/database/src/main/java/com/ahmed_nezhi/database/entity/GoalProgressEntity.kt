package com.ahmed_nezhi.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.ahmed_nezhi.common.enums.GoalStatus

@Entity(
    tableName = "goal_progress",
    foreignKeys = [
        ForeignKey(
            entity = GoalEntity::class,
            parentColumns = ["id"],
            childColumns = ["goalId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["goalId"])]
)
data class GoalProgressEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val goalId: Int,
    val photoUrl: String,
    val date: String,
    val description: String,
    val status: GoalStatus
)
