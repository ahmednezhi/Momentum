package com.ahmed_nezhi.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ahmed_nezhi.database.entity.GoalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goal: GoalEntity)

    @Query("SELECT * FROM goals WHERE DATE(:date) BETWEEN DATE(TRIM(startDate)) AND DATE(TRIM(endDate))")
    fun getAllGoalsOfDate(date: String): Flow<List<GoalEntity>>

    @Query("SELECT * FROM goals WHERE :id = id")
    fun getGoalById(id: Int): Flow<GoalEntity>

    @Delete
    suspend fun deleteGoal(goal: GoalEntity)
}