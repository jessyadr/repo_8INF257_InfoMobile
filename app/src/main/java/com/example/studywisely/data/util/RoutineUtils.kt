package com.example.studywisely.data.util

import com.example.studywisely.data.model.PriorityType
import com.example.studywisely.data.model.RoutineVM
import java.util.Calendar
import java.util.concurrent.TimeUnit

object RoutineUtils {

    /**
     * Helpers pour créer des dates propres (heures rondes).
     * - routine (révision) : heures rondes (ex: 6, 8, 14, 19)
     * - examen : seulement 8, 13, 16, 19 (selon ta règle)
     */
    private fun at(daysFromNow: Int, hour24: Int, minute: Int = 0): Long {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, daysFromNow)
        cal.set(Calendar.HOUR_OF_DAY, hour24)
        cal.set(Calendar.MINUTE, minute)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        return cal.timeInMillis
    }

    private fun examAt(daysFromNow: Int, hour24: Int): Long {
        val allowed = setOf(8, 13, 16, 19)
        require(hour24 in allowed) {
            "Heure d'examen invalide ($hour24). Autorisées: 8, 13, 16, 19."
        }
        return at(daysFromNow, hour24, 0)
    }

    // “Solution temporaire” : liste locale (en attendant DB)
    private val routinesList: MutableList<RoutineVM> = mutableListOf(
        RoutineVM(
            id = 1,
            title = "Réviser Mécanique",
            description = "Chapitre 3 + exercices",
            routineDateTimeMillis = at(0, 14),     // aujourd'hui 14h00
            examDateTimeMillis = examAt(1, 8)      // demain 08h00  -> priorité élevée (souvent)
        ),
        RoutineVM(
            id = 2,
            title = "Réviser Statistiques",
            description = "Probabilités + exercices",
            routineDateTimeMillis = at(0, 19),     // aujourd'hui 19h00
            examDateTimeMillis = examAt(2, 13)     // dans 2 jours 13h00 -> priorité moyenne/élevée selon l'heure actuelle
        ),
        RoutineVM(
            id = 3,
            title = "Réviser Calcul",
            description = "Intégrales",
            routineDateTimeMillis = at(1, 14),     // demain 14h00
            examDateTimeMillis = examAt(4, 16)     // dans 4 jours 16h00 -> plutôt moyenne/faible
        ),
        RoutineVM(
            id = 4,
            title = "Projet synthèse - planning",
            description = "Découper tâches",
            routineDateTimeMillis = at(2, 8),      // dans 2 jours 08h00
            examDateTimeMillis = examAt(7, 13)     // dans 7 jours 13h00 -> faible
        ),
        RoutineVM(
            id = 5,
            title = "Relire notes Électronique 2",
            description = "Résumé + fiches",
            routineDateTimeMillis = at(3, 14),     // dans 3 jours 14h00
            examDateTimeMillis = examAt(10, 8)     // dans 10 jours 08h00 -> faible
        ),
        RoutineVM(
            id = 6,
            title = "Réviser Chimie",
            description = "Équilibres + quiz",
            routineDateTimeMillis = at(1, 8),      // demain 08h00
            examDateTimeMillis = examAt(5, 19)     // dans 5 jours 19h00 -> faible/moyenne
        ),
        RoutineVM(
            id = 7,
            title = "Réviser Automatique",
            description = "Lieu des racines",
            routineDateTimeMillis = at(4, 14),     // dans 4 jours 14h00
            examDateTimeMillis = examAt(6, 16)     // dans 6 jours 16h00
        ),
        RoutineVM(
            id = 8,
            title = "Lecture - Bases Kotlin",
            description = "Compose state + VM",
            routineDateTimeMillis = at(6, 8),      // dans 6 jours 08h00
            examDateTimeMillis = examAt(30, 13)    // dans 30 jours 13h00
        ),
        RoutineVM(
            id = 9,
            title = "Faire lab1 Réseaux",
            description = "Configurer + rapport",
            routineDateTimeMillis = at(2, 16),     // dans 2 jours 16h00
            examDateTimeMillis = examAt(12, 19)    // dans 12 jours 19h00
        ),
        RoutineVM(
            id = 10,
            title = "Préparer présentation",
            description = "Slides + speech",
            routineDateTimeMillis = at(5, 14),     // dans 5 jours 14h00
            examDateTimeMillis = examAt(8, 16)     // dans 8 jours 16h00
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
     * - Si examen null : Faible
     */
    private fun computePriorityFromExam(nowMillis: Long, examMillis: Long?): PriorityType {
        if (examMillis == null) return PriorityType.Faible
        val diff = examMillis - nowMillis

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
}