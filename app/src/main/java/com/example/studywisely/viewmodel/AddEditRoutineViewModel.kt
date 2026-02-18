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

            is AddEditRoutineEvent.PickedRoutineDateTime -> {
                _routine.value =
                    _routine.value.copy(routineDateTimeMillis = event.millis)
            }

            is AddEditRoutineEvent.PickedExamDateTime -> {
                _routine.value =
                    _routine.value.copy(examDateTimeMillis = event.millis)
            }

            AddEditRoutineEvent.SaveRoutine -> {
                RoutineUtils.upsertRoutine(_routine.value)
                _routine.value = RoutineVM(priority = PriorityType.Moyenne)
            }
        }
    }
}
