package com.example.studywisely.viewmodel

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.example.studywisely.data.local.RoutineDatabaseHelper
import com.example.studywisely.data.model.RoutineVM

class ListRoutinesViewModel(application: Application) : AndroidViewModel(application) {

    private val db = RoutineDatabaseHelper(application)

    private val _routines = mutableStateOf<List<RoutineVM>>(emptyList())
    val routines: State<List<RoutineVM>> = _routines

    init {
        loadRoutines()
    }

    private fun loadRoutines() {
        _routines.value = db.getAllRoutines()
            .sortedBy { it.examDateTimeMillis ?: Long.MAX_VALUE }
    }

    fun deleteRoutine(id: Int) {
        db.deleteRoutine(id)
        loadRoutines()
    }

    fun refresh() {
        loadRoutines()
    }
}