package com.ahmed_nezhi.home_presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ahmed_nezhi.goal_domain.model.Goal
import com.ahmed_nezhi.home_presentation.component.EmptyDayComponent
import com.ahmed_nezhi.home_presentation.component.GoalItem
import com.ahmed_nezhi.home_presentation.component.WelcomeTitle
import com.ahmed_nezhi.home_presentation.viewmodel.HomeViewModel
import com.ahmed_nezhi.ui.calendar.horizontal.HorizontalCalendar

@Composable
fun HomeScreen(
    onNavigateToAddGoal: () -> Unit,
    onNavigateToGoalDetails: (Int) -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val currentSelectedDate by homeViewModel.currentSelectedDate.collectAsStateWithLifecycle()

    Scaffold(
        floatingActionButton = {
            AddGoalButton(onClick = onNavigateToAddGoal)
        },
        content = { paddingValues ->
            HomeContent(
                uiState = uiState,
                currentSelectedDate = currentSelectedDate,
                onDateSelected = { homeViewModel.updateSelectedDate(it) },
                onNavigateToGoalDetails = onNavigateToGoalDetails,
                modifier = modifier.padding(paddingValues)
            )
        }
    )
}

@Composable
private fun AddGoalButton(onClick: () -> Unit) {
    ExtendedFloatingActionButton(
        onClick = onClick,
        icon = {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.add_new_goal)
            )
        },
        text = { Text(text = stringResource(R.string.add_new_goal)) }
    )
}

@Composable
private fun HomeContent(
    uiState: HomeViewModel.HomeUiState,
    currentSelectedDate: String,
    onDateSelected: (String) -> Unit,
    onNavigateToGoalDetails: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    if (uiState.goals.isNotEmpty()) {
        BackgroundImage()
    }
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        WelcomeTitle()
        HorizontalCalendar(onDateSelected = onDateSelected)
        when {
            uiState.isLoading -> LoadingIndicator()
            uiState.goals.isEmpty() -> EmptyDayComponent()
            else -> GoalsList(uiState.goals, currentSelectedDate, onNavigateToGoalDetails)
        }
    }
}

@Composable
private fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun GoalsList(
    goals: List<Goal>,
    currentSelectedDate: String,
    onClickListener: (Int) -> Unit
) {
    LazyColumn {
        items(goals) { goal ->
            GoalItem(
                goal = goal,
                currentSelectedDate = currentSelectedDate,
                onClickListener = { goalId ->
                    onClickListener(goalId)
                }
            )
        }
    }
}

@Composable
private fun BackgroundImage() {
    Image(
        painter = painterResource(com.ahmed_nezhi.ui.R.drawable.confident_poeple),
        contentDescription = "Background Image",
        modifier = Modifier.fillMaxSize()
    )
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        onNavigateToAddGoal = {},
        modifier = Modifier.padding(16.dp),
        onNavigateToGoalDetails = { }
    )
}