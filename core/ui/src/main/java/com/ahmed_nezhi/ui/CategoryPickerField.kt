package com.ahmed_nezhi.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmed_nezhi.common.enums.toHumanReadableCategory

@Composable
fun CategoryPickerField(
    category: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
            .clickable { onClick() }
            .padding(vertical = 16.dp)
            .border(
                1.dp,
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                MaterialTheme.shapes.extraSmall
            )
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = category.toHumanReadableCategory(),
            color = if (category == stringResource(R.string.select_category)) MaterialTheme.colorScheme.onSurface.copy(
                alpha = 0.6f
            ) else MaterialTheme.colorScheme.onSurface,
            fontSize = 16.sp,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
