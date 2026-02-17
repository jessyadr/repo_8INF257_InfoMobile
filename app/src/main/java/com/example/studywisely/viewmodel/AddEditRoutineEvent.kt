package com.example.studywisely.viewmodel

import com.example.studywisely.data.model.PriorityType

sealed interface AddEditRoutineEvent {
    data class EnteredTitle(val value: String) : AddEditRoutineEvent
    data class EnteredDescription(val value: String) : AddEditRoutineEvent
    data class EnteredTimeLabel(val value: String) : AddEditRoutineEvent
    data class PickedPriority(val value: PriorityType) : AddEditRoutineEvent
    data object SaveRoutine : AddEditRoutineEvent
}
