package com.example.studywisely.viewmodel.routines_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studywisely.model.local.RoutineModel
import com.example.studywisely.repository.RoutineRepository
import kotlinx.coroutines.launch

class ListRoutinesViewModel(private val repository: RoutineRepository) : ViewModel() {
    private val _routines = mutableStateOf<List<RoutineModel>>(emptyList())
    val routines: State<List<RoutineModel>> = _routines

    init {
        loadRoutines()
    }

    private fun loadRoutines() {
        viewModelScope.launch{
            repository.getAllRoutines().collect { list ->
                _routines.value = list
            }
        }
    }

    fun deleteRoutine(id: Int) {
        viewModelScope.launch {
            repository.deleteRoutine(id)
        }
    }

}