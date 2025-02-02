package com.ahmed_nezhi.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ahmed_nezhi.database.converters.Converters
import com.ahmed_nezhi.database.dao.GoalDao
import com.ahmed_nezhi.database.dao.GoalProgressDao
import com.ahmed_nezhi.database.entity.GoalEntity
import com.ahmed_nezhi.database.entity.GoalProgressEntity

const val DATABASE_NAME = "APP_DATABASE"

@Database(
    entities = [GoalEntity::class, GoalProgressEntity::class],
    version = 6,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun goalDao(): GoalDao
    abstract fun goalProgressDao(): GoalProgressDao
}