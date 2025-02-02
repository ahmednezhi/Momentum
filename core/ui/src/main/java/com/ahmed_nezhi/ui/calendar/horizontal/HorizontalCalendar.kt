package com.ahmed_nezhi.ui.calendar.horizontal

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.format.DateTimeFormatter

@Composable
fun HorizontalCalendar(
    modifier: Modifier = Modifier,
    onDateSelected: (String) -> Unit
) {
    val dataSource = CalendarDataSource()
    var calendarUiModel by remember { mutableStateOf(dataSource.getData(lastSelectedDate = dataSource.today)) }

    Column(modifier = modifier.fillMaxWidth()) {
        CalendarContent(data = calendarUiModel, onDateClickListener = { date ->
            calendarUiModel = calendarUiModel.copy(
                selectedDate = date,
                visibleDates = calendarUiModel.visibleDates.map {
                    it.copy(isSelected = it.date.isEqual(date.date))
                }
            )
            val formattedDate = date.date.format(DateTimeFormatter.ISO_LOCAL_DATE)
            onDateSelected(formattedDate)
        })

        Header(
            data = calendarUiModel,
            onPrevClickListener = { startDate ->
                val finalStartDate = startDate.minusDays(1)
                calendarUiModel = dataSource.getData(
                    startDate = finalStartDate,
                    lastSelectedDate = calendarUiModel.selectedDate.date
                )
            },
            onNextClickListener = { endDate ->
                val finalStartDate = endDate.plusDays(2)
                calendarUiModel = dataSource.getData(
                    startDate = finalStartDate,
                    lastSelectedDate = calendarUiModel.selectedDate.date
                )
            }
        )
    }
}


@Composable
private fun CalendarContent(
    data: CalendarUiModel,
    onDateClickListener: (CalendarUiModel.Date) -> Unit,
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        items(items = data.visibleDates) { date ->
            ContentItem(
                date = date,
                onDateClickListener
            )
        }
    }
}


@Composable
fun ContentItem(
    date: CalendarUiModel.Date,
    onClickListener: (CalendarUiModel.Date) -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(vertical = 6.dp, horizontal = 6.dp)
            .clickable {
                onClickListener(date)
            },
        colors = CardDefaults.cardColors(
            // background colors of the selected date
            // and the non-selected date are different
            containerColor = if (date.isSelected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.secondary
            }
        ),
    ) {
        Column(
            modifier = Modifier
                .width(40.dp)
                .height(48.dp)
                .padding(4.dp)
        ) {
            Text(
                text = date.day, // day "Mon", "Tue"
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = date.date.dayOfMonth.toString(), // date "15", "16"
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}