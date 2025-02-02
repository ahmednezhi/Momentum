package com.ahmed_nezhi.goal_presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ahmed_nezhi.common.enums.FrequencyEnum
import com.ahmed_nezhi.common.enums.toHumanReadableFrequency
import com.ahmed_nezhi.goal_presentation.R

@Composable
fun FrequencyPicker(
    frequency: String,
    onFrequencySelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val frequencies = FrequencyEnum.entries
    Column(modifier = modifier) {
        Text(
            stringResource(R.string.frequency),
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(top = 8.dp)
        )

        frequencies.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onFrequencySelected(option.name) }
            ) {
                RadioButton(
                    selected = frequency == option.name,
                    onClick = { onFrequencySelected(option.name) },
                    modifier = Modifier.padding(start = 8.dp)
                )
                Text(
                    text = option.toHumanReadableFrequency(),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}
