package com.ahmed_nezhi.notifications

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ahmed_nezhi.notifications.Constants.ALARM_CHANNEL_NAME
import com.ahmed_nezhi.notifications.Constants.ALARM_ID
import com.ahmed_nezhi.notifications.Constants.MESSAGE
import com.ahmed_nezhi.notifications.Constants.STOP_ALARM
import com.ahmed_nezhi.notifications.Constants.TITLE

class AlarmReceiver : BroadcastReceiver() {


    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context?, intent: Intent?) {

        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val mediaPlayer: MediaPlayer =
            MediaPlayer.create(context, Settings.System.DEFAULT_NOTIFICATION_URI)
        mediaPlayer.isLooping = false


        if (intent?.action == STOP_ALARM) {
            val alarmId = intent.getIntExtra(ALARM_ID, 2)
            NotificationManagerCompat.from(context).cancel(alarmId)

            mediaPlayer.release()
            mediaPlayer.stop()

            val pIntent = PendingIntent.getBroadcast(
                context,
                alarmId,
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_IMMUTABLE
            )

            alarmManager.cancel(pIntent)

            return
        }
        val title = intent?.getStringExtra(TITLE) ?: return
        val message = intent.getStringExtra(MESSAGE)
        val alarmId = intent.getIntExtra(ALARM_ID, 1)
        val launchAppIntent =
            context.packageManager.getLaunchIntentForPackage(context.packageName)?.apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        // Create a PendingIntent to launch the app
        val pendingIntent: PendingIntent? = launchAppIntent?.let {
            PendingIntent.getActivity(context, 0, it, PendingIntent.FLAG_IMMUTABLE)
        }

        val stopPendingIntent = PendingIntent.getBroadcast(
            context, 1, Intent(context, AlarmReceiver::class.java).apply {
                action = STOP_ALARM
                putExtra(ALARM_ID, alarmId)
            },
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(context, ALARM_CHANNEL_NAME)
            .setSmallIcon(R.drawable.baseline_rocket_launch_24)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setContentIntent(pendingIntent)

        mediaPlayer.start()

        if (ActivityCompat.checkSelfPermission(
                context,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            NotificationManagerCompat.from(context).notify(1, builder.build())
        }
    }
}