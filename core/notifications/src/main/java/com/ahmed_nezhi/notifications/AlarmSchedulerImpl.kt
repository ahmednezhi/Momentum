package com.ahmed_nezhi.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.ahmed_nezhi.notifications.Constants.ALARM_ID
import com.ahmed_nezhi.notifications.Constants.MESSAGE
import com.ahmed_nezhi.notifications.Constants.TITLE
import kotlin.random.Random

class AlarmSchedulerImpl(private val context: Context) : AlarmScheduler {

    private val alarmManager: AlarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override fun schedule(alarm: Alarm) {

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            alarm.hashCode(),
            Intent(context, AlarmReceiver::class.java).apply {
                putExtra(TITLE, alarm.title)
                putExtra(MESSAGE, alarm.message)
                putExtra(ALARM_ID, Random.nextLong())
            },
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                alarm.scheduleAt,
                pendingIntent
            )
        } else {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                alarm.scheduleAt,
                pendingIntent
            )
        }
    }

    override fun cancel(alarm: Alarm) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                alarm.hashCode(),
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        )
    }

}