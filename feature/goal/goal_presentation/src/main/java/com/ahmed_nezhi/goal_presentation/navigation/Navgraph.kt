package com.ahmed_nezhi.goal_presentation.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ahmed_nezhi.goal_presentation.add.AddGoalScreen
import com.ahmed_nezhi.goal_presentation.details.GoalDetailsScreen
import com.ahmed_nezhi.navigation.AppScreen

fun NavGraphBuilder.goalNavGraph(modifier: Modifier ,navController: NavController) {
    composable(route = AppScreen.AddGoalScreen.route) {
        AddGoalScreen(
            modifier = modifier,
            onBack = { navController.popBackStack() }
        )
    }
    composable(route = "goal_details_route/{goalId}") { backStackEntry ->
        // Get the goalId from the arguments
        val goalId = backStackEntry.arguments?.getString("goalId")?.toIntOrNull() ?: 0
        GoalDetailsScreen(
            modifier = modifier,
            onBack = { navController.popBackStack() },
            goalId = goalId
        )
    }
}
