package com.ahmed_nezhi.home_presentation.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

import com.ahmed_nezhi.home_presentation.HomeScreen
import com.ahmed_nezhi.navigation.AppScreen

fun NavGraphBuilder.homeNavGraph(modifier: Modifier, navController: NavController) {
    composable(route = AppScreen.Home.route) {
        HomeScreen(
            modifier = modifier,
            onNavigateToAddGoal = { navController.navigate(AppScreen.AddGoalScreen.route) },
            onNavigateToGoalDetails = { goalId ->
                navController.navigate(AppScreen.GoalDetailsScreen(goalId).routeWithArgs())
            }
        )
    }
}


