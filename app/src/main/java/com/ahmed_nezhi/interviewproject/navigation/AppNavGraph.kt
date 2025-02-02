package com.ahmed_nezhi.interviewproject.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ahmed_nezhi.goal_presentation.navigation.goalNavGraph
import com.ahmed_nezhi.home_presentation.navigation.homeNavGraph
import com.ahmed_nezhi.navigation.AppScreen

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreen.Home.route
    ) {
        homeNavGraph(modifier = modifier, navController = navController)
        goalNavGraph(modifier = modifier, navController = navController)
    }
}
