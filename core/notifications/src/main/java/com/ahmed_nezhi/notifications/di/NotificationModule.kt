package com.ahmed_nezhi.notifications.di

import android.content.Context
import com.ahmed_nezhi.notifications.AlarmReceiver
import com.ahmed_nezhi.notifications.AlarmScheduler
import com.ahmed_nezhi.notifications.AlarmSchedulerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {

    @Provides
    @Singleton
    fun provideAlarmReceiver(): AlarmReceiver {
        return AlarmReceiver()
    }

    @Provides
    @Singleton
    fun provideAlarmScheduler(@ApplicationContext context: Context): AlarmScheduler {
        return AlarmSchedulerImpl(context)
    }
}
