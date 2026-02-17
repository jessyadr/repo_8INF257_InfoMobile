package com.example.studywisely.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studywisely.presentation.addedit.AddEditRoutineScreen
import com.example.studywisely.presentation.list.RoutinesListScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.RoutinesListScreen.route
    ) {

        composable(Screen.RoutinesListScreen.route) {
            RoutinesListScreen(
                navController = navController
            )
        }

        composable(Screen.AddEditRoutineScreen.route) {
            AddEditRoutineScreen(
                navController = navController
            )
        }
    }
}
