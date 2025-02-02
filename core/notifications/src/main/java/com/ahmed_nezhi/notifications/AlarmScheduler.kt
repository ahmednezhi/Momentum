package com.ahmed_nezhi.notifications

interface AlarmScheduler {

    fun schedule(alarm: Alarm)
    fun cancel(alarm: Alarm)
}