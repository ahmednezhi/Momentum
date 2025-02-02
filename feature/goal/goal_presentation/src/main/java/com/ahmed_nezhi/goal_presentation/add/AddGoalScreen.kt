package com.ahmed_nezhi.goal_presentation.add

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ahmed_nezhi.common.enums.getCategoryList
import com.ahmed_nezhi.common.enums.toHumanReadableCategory
import com.ahmed_nezhi.goal_presentation.R
import com.ahmed_nezhi.goal_presentation.component.FrequencyPicker
import com.ahmed_nezhi.goal_presentation.add.viewmodel.AddGoalViewModel
import com.ahmed_nezhi.ui.CategoryPickerField
import com.ahmed_nezhi.ui.button.DateRangeButton
import com.ahmed_nezhi.ui.textfield.CustomOutlinedTextField
import java.time.LocalDate
import com.ahmed_nezhi.goal_presentation.add.viewmodel.AddGoalViewModel.FormEvent
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGoalScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    viewModel: AddGoalViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text(stringResource(R.string.add_goal)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            GoalInputFields(uiState, viewModel)
            SubmitButton(uiState, viewModel, onBack, context)
        }
    }

    if (uiState.showCategorySheet) {
        CategoryPicker(
            categories = getCategoryList(),
            onSelect = { viewModel.onEvent(FormEvent.CategoryChanged(it)) },
            onDismiss = { viewModel.onEvent(FormEvent.ToggleCategorySheet) }
        )
    }

    if (uiState.showDateRangePicker) {
        DateRangePickerModal(
            onDateRangeSelected = { selectedRange ->
                val (startMillis, endMillis) = selectedRange
                if (startMillis != null && endMillis != null) {
                    val formatter = DateTimeFormatter.ISO_LOCAL_DATE
                    val startDate = LocalDate.ofEpochDay(startMillis / (24 * 60 * 60 * 1000)).format(formatter)
                    val endDate = LocalDate.ofEpochDay(endMillis / (24 * 60 * 60 * 1000)).format(formatter)
                    viewModel.onEvent(FormEvent.DateRangeChanged(startDate, endDate))
                }
            },
            onDismiss = { viewModel.onEvent(FormEvent.ToggleDateRangePicker) }
        )
    }
}

@Composable
private fun GoalInputFields(uiState: AddGoalViewModel.UiState, viewModel: AddGoalViewModel) {
    Column {
        CustomOutlinedTextField(
            value = uiState.name,
            onValueChange = { viewModel.onEvent(FormEvent.NameChanged(it)) },
            label = stringResource(R.string.goal_name)
        )

        Spacer(modifier = Modifier.height(8.dp))

        CustomOutlinedTextField(
            value = uiState.description,
            onValueChange = { viewModel.onEvent(FormEvent.DescriptionChanged(it)) },
            label = stringResource(R.string.description)
        )

        Spacer(modifier = Modifier.height(8.dp))

        CategoryPickerField(
            category = uiState.category,
            onClick = { viewModel.onEvent(FormEvent.ToggleCategorySheet) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        DateRangeButton(
            startDate = uiState.startDate,
            endDate = uiState.endDate,
            onClick = { viewModel.onEvent(FormEvent.ToggleDateRangePicker) },
            modifier = Modifier.heightIn(min = 56.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        FrequencyPicker(
            frequency = uiState.frequency,
            onFrequencySelected = { viewModel.onEvent(FormEvent.FrequencyChanged(it)) }
        )
    }
}

@Composable
private fun SubmitButton(
    uiState: AddGoalViewModel.UiState,
    viewModel: AddGoalViewModel,
    onBack: () -> Unit,
    context: Context
) {
    Spacer(modifier = Modifier.height(16.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            if (uiState.isValid) {
                viewModel.onEvent(FormEvent.SubmitGoal)
                onBack()
            } else {
                Toast.makeText(context, context.getString(R.string.please_fill_all_fields), Toast.LENGTH_SHORT).show()
            }
        }) {
            Text(stringResource(R.string.let_s_go))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePickerModal(
    onDateRangeSelected: (Pair<Long?, Long?>) -> Unit,
    onDismiss: () -> Unit
) {
    val dateRangePickerState = rememberDateRangePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateRangeSelected(dateRangePickerState.selectedStartDateMillis to dateRangePickerState.selectedEndDateMillis)
                onDismiss()
            }) {
                Text(stringResource(R.string.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    ) {
        DateRangePicker(
            state = dateRangePickerState,
            title = { Text(stringResource(R.string.select_date_range)) },
            showModeToggle = false,
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(16.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryPicker(
    categories: List<String>,
    onSelect: (String) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        categories.forEach { category ->
            Text(
                text = category.toHumanReadableCategory(),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onSelect(category)
                        onDismiss()
                    }
                    .padding(16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddGoalScreenPreview() {
    AddGoalScreen(onBack = {})
}
