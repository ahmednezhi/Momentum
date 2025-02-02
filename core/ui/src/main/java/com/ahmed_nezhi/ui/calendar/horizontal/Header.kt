package com.ahmed_nezhi.ui.calendar.horizontal

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ahmed_nezhi.ui.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun Header(
    data: CalendarUiModel,
    onPrevClickListener: (LocalDate) -> Unit,
    onNextClickListener: (LocalDate) -> Unit,
) {
    Row {
        Text(
            // show "Today" if user selects today's date
            // else, show the full format of the date
            text = if (data.selectedDate.isToday) {
                "Today"
            } else {
                data.selectedDate.date.format(
                    DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
                )
            },
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
                .padding(start = 16.dp)
        )
        IconButton(onClick = {
            onPrevClickListener(data.startDate.date)
        }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = stringResource(R.string.previous_week)
            )
        }
        IconButton(onClick = {
            onNextClickListener(data.endDate.date)
        }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = stringResource(R.string.next_week)
            )
        }
    }
}