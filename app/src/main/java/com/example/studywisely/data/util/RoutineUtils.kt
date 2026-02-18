package com.example.studywisely.data.util

import com.example.studywisely.data.model.PriorityType
import com.example.studywisely.data.model.RoutineVM
import java.util.concurrent.TimeUnit

object RoutineUtils {

    // “Solution temporaire” : liste locale (en attendant DB)
    private val routinesList: MutableList<RoutineVM> = mutableListOf(
        RoutineVM(
            id = 1,
            title = "Réviser Mécanique",
            description = "Chapitre 3 + exercices",
            routineDateTimeMillis = nowPlusHours(2),
            examDateTimeMillis = nowPlusHours(20)
        ),
        RoutineVM(
            id = 2,
            title = "Relire notes Électronique 2",
            description = "Résumé + fiches",
            routineDateTimeMillis = nowPlusHours(26),
            examDateTimeMillis = nowPlusDays(5)
        ),
        RoutineVM(
            id = 3,
            title = "Faire lab1 Réseaux",
            description = "Configurer + rapport",
            routineDateTimeMillis = nowPlusDays(3),
            examDateTimeMillis = nowPlusDays(10)
        ),
        RoutineVM(
            id = 4,
            title = "Réviser Chimie",
            description = "Équilibres + quiz",
            routineDateTimeMillis = nowPlusHours(1),
            examDateTimeMillis = nowPlusDays(8)
        ),
        RoutineVM(
            id = 5,
            title = "Réviser Calcul",
            description = "Intégrales",
            routineDateTimeMillis = nowPlusDays(1),
            examDateTimeMillis = nowPlusDays(2)
        ),
        RoutineVM(
            id = 6,
            title = "Projet synthèse - planning",
            description = "Découper tâches",
            routineDateTimeMillis = nowPlusDays(2),
            examDateTimeMillis = nowPlusDays(3)
        ),
        RoutineVM(
            id = 7,
            title = "Réviser Automatique",
            description = "Lieu des racines",
            routineDateTimeMillis = nowPlusDays(4),
            examDateTimeMillis = nowPlusDays(4)
        ),
        RoutineVM(
            id = 8,
            title = "Lecture - Bases Kotlin",
            description = "Compose state + VM",
            routineDateTimeMillis = nowPlusDays(6),
            examDateTimeMillis = nowPlusDays(30)
        ),
        RoutineVM(
            id = 9,
            title = "Réviser Statistiques",
            description = "Probabilités + exercices",
            routineDateTimeMillis = nowPlusDays(1),
            examDateTimeMillis = nowPlusDays(1)
        ),
        RoutineVM(
            id = 10,
            title = "Préparer présentation",
            description = "Slides + speech",
            routineDateTimeMillis = nowPlusDays(7),
            examDateTimeMillis = nowPlusDays(7)
        ),

    )

    fun getRoutines(): List<RoutineVM> {
        val now = System.currentTimeMillis()

        return routinesList
            .map { r ->
                val computed = computePriorityFromExam(now, r.examDateTimeMillis)
                r.copy(priority = computed)
            }
            .sortedWith(
                compareBy<RoutineVM> { priorityRank(it.priority) }
                    .thenBy { it.examDateTimeMillis ?: Long.MAX_VALUE }
                    .thenBy { it.routineDateTimeMillis ?: Long.MAX_VALUE }
            )
    }

    fun upsertRoutine(routine: RoutineVM) {
        val now = System.currentTimeMillis()
        val computed = computePriorityFromExam(now, routine.examDateTimeMillis)

        val routineWithPriority = routine.copy(priority = computed)

        val index = routinesList.indexOfFirst { it.id == routineWithPriority.id }
        if (index >= 0) routinesList[index] = routineWithPriority
        else routinesList.add(0, routineWithPriority)
    }

    fun deleteRoutine(id: Int) {
        routinesList.removeAll { it.id == id }
    }

    /**
     * Priorité basée sur la date d'examen/livrable.
     * - Élevée : examen <= 24h
     * - Moyenne : examen <= 72h
     * - Faible : au-delà
     * - Si examen null : Faible (par défaut)
     */
    private fun computePriorityFromExam(nowMillis: Long, examMillis: Long?): PriorityType {
        if (examMillis == null) return PriorityType.Faible
        val diff = examMillis - nowMillis

        // si examen déjà passé -> on le considère faible (tu peux changer plus tard)
        if (diff <= 0) return PriorityType.Faible

        val hours = TimeUnit.MILLISECONDS.toHours(diff)

        return when {
            hours <= 24 -> PriorityType.Elevee
            hours <= 72 -> PriorityType.Moyenne
            else -> PriorityType.Faible
        }
    }

    private fun priorityRank(p: PriorityType): Int = when (p) {
        PriorityType.Elevee -> 0
        PriorityType.Moyenne -> 1
        PriorityType.Faible -> 2
    }

    private fun nowPlusHours(h: Int): Long =
        System.currentTimeMillis() + TimeUnit.HOURS.toMillis(h.toLong())

    private fun nowPlusDays(d: Int): Long =
        System.currentTimeMillis() + TimeUnit.DAYS.toMillis(d.toLong())
}
