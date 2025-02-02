package com.ahmed_nezhi.goal_domain.model

import com.ahmed_nezhi.common.enums.CategoryEnum
import com.ahmed_nezhi.common.enums.FrequencyEnum
import com.ahmed_nezhi.database.Constant.dateFormatter
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

data class Goal(
    val id: Int = 0,
    val name: String,
    val description: String,
    val category: CategoryEnum,
    val startDate: String,
    val endDate: String,
    val frequency: FrequencyEnum,
){
    fun duration(startDate: String): String {
        val formatter = dateFormatter

        // Parse the dates from string to LocalDate
        val start = LocalDate.parse(startDate, formatter)
        val end = LocalDate.parse(endDate, formatter)

        // Calculate the number of days between the start and end date
        // Return the result in a format like "1 day", "2 days", etc.
        return when (val daysBetween = ChronoUnit.DAYS.between(start, end)) {
            1L -> "$daysBetween day"
            else -> "$daysBetween days"
        }
    }
}
