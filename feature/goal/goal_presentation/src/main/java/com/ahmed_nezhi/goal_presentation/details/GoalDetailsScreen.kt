package com.ahmed_nezhi.goal_presentation.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ahmed_nezhi.goal_presentation.R
import com.ahmed_nezhi.goal_presentation.component.GoalDetails
import com.ahmed_nezhi.goal_presentation.component.GoalProgressBottomSheet
import com.ahmed_nezhi.goal_presentation.details.viewmodel.GoalDetailsViewModel
import com.ahmed_nezhi.ui.timeline.LazyGoalTimeline
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalDetailsScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    goalId: Int,
    viewModel: GoalDetailsViewModel = hiltViewModel()
) {
    LaunchedEffect(goalId) {
        viewModel.loadGoal(goalId)
        viewModel.loadGoalProgress(goalId)
    }

    val goalProgressUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val sheetState =
        rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var isBottomSheetVisible by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            GoalDetailsTopAppBar(onBack = onBack)
        },
        floatingActionButton = {
            AddProgressFab(
                onClick = { isBottomSheetVisible = true }
            )
        }
    ) { innerPadding ->
        GoalDetailsContent(
            modifier = modifier,
            goalProgressUiState = goalProgressUiState,
            innerPadding = innerPadding
        )

        if (isBottomSheetVisible) {
            GoalProgressBottomSheet(
                onDismissRequest = {
                    scope.launch {
                        if (sheetState.isVisible) {
                            sheetState.hide()
                        }
                        isBottomSheetVisible = false
                    }
                },
                sheetState = sheetState,
                scope = scope,
                viewModel = viewModel,
                goalId = goalId
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalDetailsTopAppBar(onBack: () -> Unit) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {},
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
    )
}

@Composable
fun AddProgressFab(onClick: () -> Unit) {
    ExtendedFloatingActionButton(
        onClick = onClick,
        icon = {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Progress")
        },
        text = {
            Text(
                stringResource(R.string.add_progress),
            )
        },
    )
}

@Composable
fun GoalDetailsContent(
    modifier: Modifier,
    goalProgressUiState: GoalDetailsViewModel.GoalDetailsUiState,
    innerPadding: androidx.compose.foundation.layout.PaddingValues
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = innerPadding.calculateTopPadding() + 8.dp) // Adjusted top padding
    ) {
        if (goalProgressUiState.isLoading) {
            Box(Modifier.fillMaxSize()) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        } else {
            // Goal Details Section
            goalProgressUiState.goal?.let { goal ->
                GoalDetails(goal)
            }

            if (goalProgressUiState.progress.isEmpty()) {
                goalProgressUiState.goal?.let { goal ->
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Image(
                            painter = painterResource(goal.category.imageResId),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            } else {
                // Timeline Section
                LazyGoalTimeline(goalProgressUiState.progress)
            }
        }
    }
}
