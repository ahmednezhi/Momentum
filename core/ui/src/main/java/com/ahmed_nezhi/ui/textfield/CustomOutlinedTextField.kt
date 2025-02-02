package com.ahmed_nezhi.ui.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    maxLength: Int = 200
) {
    OutlinedTextField(
        value = value,
        onValueChange = { if (it.length <= maxLength) onValueChange(it) },
        label = { Text(label) },
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
            .background(MaterialTheme.colorScheme.surface),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            disabledTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
            errorTextColor = MaterialTheme.colorScheme.error,
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            disabledContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.12f),
            errorContainerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.12f),
            cursorColor = MaterialTheme.colorScheme.primary,
            errorCursorColor = MaterialTheme.colorScheme.error,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            disabledBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            errorBorderColor = MaterialTheme.colorScheme.error
        )
    )
}

@Preview
@Composable
private fun CustomOutlinedTextFieldPreview() {

    CustomOutlinedTextField(
        value = "Text",
        onValueChange = {  },
        label = "Goal Name"
    )

}