package com.example.studywisely.data.util

import com.example.studywisely.data.model.PriorityType
import com.example.studywisely.data.model.RoutineVM

object RoutineUtils {
    // “Solution temporaire” avec liste locale (comme dans le cours en attendant DB)
    private val routinesList: MutableList<RoutineVM> = mutableListOf(
        RoutineVM(
            id = 1,
            title = "Réviser Mécanique",
            description = "Ajouter'hui - 14:00",
            timeLabel = "Aujourd'hui - 14:00",
            priority = PriorityType.Elevee
        ),
        RoutineVM(
            id = 2,
            title = "Relire Note de cours Électronique 2",
            description = "Demain - 18:00",
            timeLabel = "Demain - 18:00",
            priority = PriorityType.Elevee
        ),
        RoutineVM(
            id = 3,
            title = "Faire lab1 Réseaux",
            description = "Vendredi - 13:00",
            timeLabel = "Vendredi - 13:00",
            priority = PriorityType.Moyenne
        ),
    )

    fun getRoutines(): List<RoutineVM> = routinesList.toList()

    fun upsertRoutine(routine: RoutineVM) {
        val index = routinesList.indexOfFirst { it.id == routine.id }
        if (index >= 0) routinesList[index] = routine else routinesList.add(0, routine)
    }

    fun deleteRoutine(id: Int) {
        routinesList.removeAll { it.id == id }
    }
}
