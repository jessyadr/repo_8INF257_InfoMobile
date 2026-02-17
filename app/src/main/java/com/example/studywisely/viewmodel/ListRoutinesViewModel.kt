package com.example.studywisely.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.studywisely.data.model.RoutineVM
import com.example.studywisely.data.util.RoutineUtils

class ListRoutinesViewModel : ViewModel() {

    // 2 états (privé mutable / public immuable) comme dans le cours
    private val _routines = mutableStateOf(RoutineUtils.getRoutines())
    val routines: State<List<RoutineVM>> = _routines

    fun refresh() {
        _routines.value = RoutineUtils.getRoutines()
    }

    fun deleteRoutine(id: Int) {
        RoutineUtils.deleteRoutine(id)
        refresh()
    }
}
