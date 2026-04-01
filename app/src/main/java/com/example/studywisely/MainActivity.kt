package com.example.studywisely

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.room.*
import com.example.studywisely.model.data.RoutinesDatabase
import com.example.studywisely.ui.navigation.AppNavHost
import com.example.studywisely.ui.theme.StudyWiselyTheme
import com.example.studywisely.repository.RoutineRepository
import com.example.studywisely.viewmodel.add_edit_routine.AddEditRoutineViewModel
import com.example.studywisely.viewmodel.routines_list.ListRoutinesViewModel

class MainActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            RoutinesDatabase::class.java,
            RoutinesDatabase.DATABASE_NAME
        ).build()
    }

    private val repository by lazy { RoutineRepository(db) }
    private val listRoutinesViewModel by lazy { ListRoutinesViewModel(repository) }
    private val addEditRoutineViewModel by lazy { AddEditRoutineViewModel(repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            StudyWiselyTheme {
                AppNavHost(listRoutinesViewModel = listRoutinesViewModel,
                    addEditRoutineViewModel = addEditRoutineViewModel)
            }
        }
    }
}