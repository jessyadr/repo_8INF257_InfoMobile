package com.example.studywisely.navigation

sealed class Screen(val route: String) {
    data object RoutinesListScreen : Screen(route = "routines_list_screen")
    data object AddEditRoutineScreen : Screen(route = "add_edit_routine_screen")
}
