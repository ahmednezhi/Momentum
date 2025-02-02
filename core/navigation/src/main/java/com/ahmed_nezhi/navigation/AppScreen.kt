package com.ahmed_nezhi.navigation

sealed class AppScreen(val route: String) {
    data object Home : AppScreen("home_route")
    data object AddGoalScreen : AppScreen("goal_route")
    data class GoalDetailsScreen(val goalId: Int) : AppScreen("goal_details_route/{goalId}") {
        fun routeWithArgs() = "goal_details_route/$goalId"
    }
}