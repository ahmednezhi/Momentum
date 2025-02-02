package com.ahmed_nezhi.database.di

import android.content.Context
import androidx.room.Room
import com.ahmed_nezhi.database.AppDatabase
import com.ahmed_nezhi.database.DATABASE_NAME
import com.ahmed_nezhi.database.dao.GoalDao
import com.ahmed_nezhi.database.dao.GoalProgressDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideContext(
        @ApplicationContext context: Context,
    ): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideGoalDao(goalDatabase: AppDatabase): GoalDao {
        return goalDatabase.goalDao()
    }

    @Provides
    fun provideGoalProgressDao(goalDatabase: AppDatabase): GoalProgressDao {
        return goalDatabase.goalProgressDao()
    }
}