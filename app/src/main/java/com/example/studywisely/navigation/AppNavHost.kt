package com.example.studywisely.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studywisely.presentation.list.RoutinesListScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.RoutinesListScreen.route
    ) {
        composable(Screen.RoutinesListScreen.route) {
            RoutinesListScreen(navController = navController)
        }
        composable(Screen.AddEditRoutineScreen.route) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Écran d'ajout — à venir (PP2)")
            }
        }
    }
}