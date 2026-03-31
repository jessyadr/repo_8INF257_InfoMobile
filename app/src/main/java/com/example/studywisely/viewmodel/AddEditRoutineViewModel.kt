package com.example.studywisely.viewmodel

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.example.studywisely.data.local.RoutineDatabaseHelper
import com.example.studywisely.data.model.PriorityType
import com.example.studywisely.data.model.RoutineVM

class AddEditRoutineViewModel(application: Application) : AndroidViewModel(application) {

    private val db = RoutineDatabaseHelper(application)

    private val _routine = mutableStateOf(RoutineVM())
    val routine: State<RoutineVM> = _routine

    fun loadRoutine(id: Int) {
        val routine = db.getAllRoutines().find { it.id == id }
        if (routine != null) {
            _routine.value = routine
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
                val now = System.currentTimeMillis()
                val examDate = _routine.value.examDateTimeMillis

                val diffDays = if (examDate != null) {
                    (examDate - now) / (1000 * 60 * 60 * 24)
                } else {
                    Long.MAX_VALUE
                }

                val priority = when {
                    diffDays <= 3 -> PriorityType.Elevee
                    diffDays <= 7 -> PriorityType.Moyenne
                    else -> PriorityType.Faible
                }

                val updatedRoutine = _routine.value.copy(priority = priority)

                if (updatedRoutine.id == 0) {
                    db.insertRoutine(updatedRoutine)
                } else {
                    db.updateRoutine(updatedRoutine)
                }

                _routine.value = RoutineVM()
            }
        }
    }
}