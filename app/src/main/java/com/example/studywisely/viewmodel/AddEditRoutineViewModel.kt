package com.example.studywisely.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.studywisely.data.model.PriorityType
import com.example.studywisely.data.model.RoutineVM
import com.example.studywisely.data.util.RoutineUtils

class AddEditRoutineViewModel : ViewModel() {

    private val _routine = mutableStateOf(RoutineVM())
    val routine: State<RoutineVM> = _routine

    fun onEvent(event: AddEditRoutineEvent) {
        when (event) {
            is AddEditRoutineEvent.EnteredTitle -> {
                _routine.value = _routine.value.copy(title = event.value)
            }
            is AddEditRoutineEvent.EnteredDescription -> {
                _routine.value = _routine.value.copy(description = event.value)
            }
            is AddEditRoutineEvent.EnteredTimeLabel -> {
                _routine.value = _routine.value.copy(timeLabel = event.value)
            }
            is AddEditRoutineEvent.PickedPriority -> {
                _routine.value = _routine.value.copy(priority = event.value)
            }
            AddEditRoutineEvent.SaveRoutine -> {
                // si timeLabel vide, on peut le laisser vide (PP1 simple)
                RoutineUtils.upsertRoutine(_routine.value)
                // reset formulaire
                _routine.value = RoutineVM(priority = PriorityType.Moyenne)
            }
        }
    }
}
