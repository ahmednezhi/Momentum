package com.ahmed_nezhi.goal_presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ahmed_nezhi.common.enums.toHumanReadableCategory
import com.ahmed_nezhi.goal_domain.model.Goal
import com.ahmed_nezhi.goal_presentation.R

@Composable
fun GoalDetails(goal: Goal) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {

        Text(
            text = "${stringResource(R.string.goal_name)}: ${goal.name}",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "${stringResource(R.string.description)}: ${goal.description}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "${stringResource(R.string.category)}: ${goal.category.name.toHumanReadableCategory()}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary
        )
    }
//    Box(Modifier.fillMaxSize()) {
//        Image(
//            painter = painterResource(goal.category.imageResId),
//            contentDescription = null,
//            modifier = Modifier.fillMaxSize()
//        )
//    }
}