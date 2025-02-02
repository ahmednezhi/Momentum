package com.ahmed_nezhi.home_presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmed_nezhi.home_presentation.R

@Composable
fun EmptyDayComponent(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.start_ideas),
            contentDescription = "Start Ideas",
            modifier = Modifier.size(350.dp)
        )
        Text(
            stringResource(R.string.you_haven_t_saved_any_goals_for_this_day),
            fontSize = 18.sp,
            modifier = Modifier.padding(vertical = 16.dp),
            textAlign = TextAlign.Center
        )
    }
}