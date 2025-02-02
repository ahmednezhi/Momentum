package com.ahmed_nezhi.interviewproject

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.ahmed_nezhi.interviewproject.navigation.AppNavigation
import com.ahmed_nezhi.notifications.AlarmScheduler
import com.ahmed_nezhi.notifications.Constants.ALARM_CHANNEL_NAME
import com.ahmed_nezhi.ui.theme.InterviewProjectTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var alarmScheduler: AlarmScheduler

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            launchNotificationScheduler()
        } else {
            Toast.makeText(
                this,
                "In Order to get daily notifications, you need to grant permission",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestNotificationPermission()
        enableEdgeToEdge()
        setContent {
            InterviewProjectTheme {
                AppNavigation()

            }
        }
    }

    private fun requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is already granted, launch functionality
            launchNotificationScheduler()
        } else {
            // Permission is not granted, request permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun launchNotificationScheduler() {
        val name = "Goal Reminder"
        val channelDescription = "goal_reminder_channel"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val mChannel = NotificationChannel(ALARM_CHANNEL_NAME, name, importance)
        mChannel.description = channelDescription
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(mChannel)
    }
}