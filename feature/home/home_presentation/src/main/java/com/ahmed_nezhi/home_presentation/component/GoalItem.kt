package com.ahmed_nezhi.home_presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahmed_nezhi.common.enums.CategoryEnum
import com.ahmed_nezhi.common.enums.FrequencyEnum
import com.ahmed_nezhi.goal_domain.model.Goal
import com.ahmed_nezhi.home_presentation.R

@Composable
fun GoalItem(
    goal: Goal,
    currentSelectedDate: String = "",
    modifier: Modifier = Modifier,
    onClickListener: (Int) -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 64.dp)
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clickable {
                onClickListener.invoke(goal.id)
            },
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = goal.category.bgColorResId),
        ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            GoalDetails(goal)
            GoalDuration(goal, currentSelectedDate)
        }
    }
}

@Composable
private fun GoalDuration(
    goal: Goal,
    currentSelectedDate: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .heightIn(min = 48.dp)
                .width(1.dp)
                .background(Color.White)
        )
        Column(
            modifier = Modifier.padding(horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_calendar_month_24),
                contentDescription = null,
            )
            Text(
                goal.duration(currentSelectedDate),
                style = MaterialTheme.typography.labelSmall,
            )
        }
    }
}

@Composable
private fun GoalDetails(goal: Goal) {
    Row {
        Icon(
            painter = painterResource(goal.category.imageResId),
            contentDescription = null,
            modifier = Modifier
                .padding(8.dp)
                .size(56.dp)
        )
        Column {
            Text(
                goal.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 12.dp)
            )
            Text(
                goal.description,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GoalItemPreview() {
    Column {
        GoalItem(
            Goal(
                name = "test",
                description = "test",
                category = CategoryEnum.FITNESS,
                startDate = "0",
                endDate = "",
                frequency = FrequencyEnum.DAILY,
            )
        )
    }
}