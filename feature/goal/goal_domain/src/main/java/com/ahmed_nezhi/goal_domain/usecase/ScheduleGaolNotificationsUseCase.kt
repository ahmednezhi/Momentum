package com.ahmed_nezhi.goal_domain.usecase

import com.ahmed_nezhi.common.enums.FrequencyEnum
import com.ahmed_nezhi.database.Constant.dateFormatter
import com.ahmed_nezhi.goal_domain.model.Goal
import com.ahmed_nezhi.notifications.Alarm
import com.ahmed_nezhi.notifications.AlarmScheduler
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.Random
import javax.inject.Inject

class ScheduleNotificationsUseCase @Inject constructor(
    private val alarmScheduler: AlarmScheduler
) {
    operator fun invoke(goal: Goal) {
        val startDate = LocalDate.parse(goal.startDate, dateFormatter)
        val endDate = LocalDate.parse(goal.endDate, dateFormatter)
        val random = Random()

        var currentDate = startDate
        while (!currentDate.isAfter(endDate)) {
            if (shouldScheduleNotificationForDate(currentDate, goal.frequency, startDate)) {
                val randomHour = 19 + random.nextInt(2)
                val randomMinute = random.nextInt(60)

                //val alarmTime = LocalDateTime.of(currentDate, LocalTime.of(randomHour, randomMinute))
                // For testing purposes :), uncomment if u want to check notification after 1min
                val alarmTime = LocalDateTime.now().plusSeconds(10)
                val alarmTimeInMillis =
                    alarmTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

                alarmScheduler.schedule(
                    Alarm(
                        title = goal.name,
                        message = goal.description,
                        scheduleAt = alarmTimeInMillis
                    )
                )
            }

            currentDate = when (goal.frequency) {
                FrequencyEnum.DAILY -> currentDate.plusDays(1)
                FrequencyEnum.EACH_TWO_DAYS -> currentDate.plusDays(2)
                FrequencyEnum.ONCE_A_WEEK -> currentDate.plusWeeks(1)
                FrequencyEnum.ONCE_A_MONTH -> currentDate.plusMonths(1)
            }
        }
    }

    private fun shouldScheduleNotificationForDate(
        date: LocalDate,
        frequency: FrequencyEnum,
        startDate: LocalDate
    ): Boolean {
        return when (frequency) {
            FrequencyEnum.DAILY -> true
            FrequencyEnum.EACH_TWO_DAYS -> ChronoUnit.DAYS.between(startDate, date) % 2 == 0L
            FrequencyEnum.ONCE_A_WEEK -> ChronoUnit.WEEKS.between(startDate, date) % 1 == 0L
            FrequencyEnum.ONCE_A_MONTH -> ChronoUnit.MONTHS.between(
                startDate,
                date
            ) % 1 == 0L && date.dayOfMonth == startDate.dayOfMonth
        }
    }
}
