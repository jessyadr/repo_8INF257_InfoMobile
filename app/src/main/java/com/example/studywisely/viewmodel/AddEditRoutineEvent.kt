package com.example.studywisely.viewmodel

sealed interface AddEditRoutineEvent {

    data class EnteredTitle(val value: String) : AddEditRoutineEvent

    data class EnteredDescription(val value: String) : AddEditRoutineEvent

    data class PickedRoutineDateTime(val millis: Long?) : AddEditRoutineEvent

    data class PickedExamDateTime(val millis: Long?) : AddEditRoutineEvent

    data object SaveRoutine : AddEditRoutineEvent
}
