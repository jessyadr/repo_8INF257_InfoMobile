package com.example.studywisely.viewmodel.add_edit_routine

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studywisely.model.local.PriorityType
import com.example.studywisely.model.local.RoutineModel
import com.example.studywisely.repository.RoutineRepository
import kotlinx.coroutines.launch

class AddEditRoutineViewModel(private val repository: RoutineRepository) : ViewModel() {
    private val _routine = mutableStateOf(RoutineModel())
    val routine: State<RoutineModel> = _routine

    fun loadRoutine(id: Int) {
        viewModelScope.launch {
            val routine = repository.getRoutineById(id)
            _routine.value = routine ?: RoutineModel()
        }
    }

    fun onEvent(event: AddEditRoutineEvent) {
        when (event) {

            is AddEditRoutineEvent.EnteredTitle -> {
                _routine.value = _routine.value.copy(title = event.value)
            }

            is AddEditRoutineEvent.EnteredDescription -> {
                _routine.value = _routine.value.copy(description = event.value)
            }

            is AddEditRoutineEvent.PickedRoutineDateTime -> {
                _routine.value = _routine.value.copy(
                    routineDateTimeMillis = event.millis
                )
            }

            is AddEditRoutineEvent.PickedExamDateTime -> {
                _routine.value = _routine.value.copy(
                    examDateTimeMillis = event.millis
                )
            }

            AddEditRoutineEvent.SaveRoutine -> {
                viewModelScope.launch {
                    val current = _routine.value
                    val now = System.currentTimeMillis()
                    val examDate = current.examDateTimeMillis
                    val priority = if (examDate != null) {
                        val diffDays = (examDate - now) / (1000 * 60 * 60 * 24)
                        when {
                            diffDays <= 3 -> PriorityType.Elevee
                            diffDays <= 7 -> PriorityType.Moyenne
                            else -> PriorityType.Faible
                        }
                    } else {
                        PriorityType.Faible
                    }
                    val routineToSave = current.copy(priority = priority)

                    if (routineToSave.id == 0 || routineToSave.id == null) {
                        repository.insertRoutine(routineToSave)
                    } else {
                        repository.updateRoutine(routineToSave)
                    }
                    _routine.value = RoutineModel()
                }
            }
        }
    }
}