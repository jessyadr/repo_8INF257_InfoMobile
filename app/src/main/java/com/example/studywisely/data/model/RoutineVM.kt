package com.example.studywisely.data.model

import kotlin.random.Random

data class RoutineVM(
    val id: Int = Random.nextInt(),
    val title: String = "",
    val description: String = "",

    // Date/heure de la routine (révision)
    val routineDateTimeMillis: Long? = null,

    // Date/heure de l'examen ou livrable prévu
    val examDateTimeMillis: Long? = null,

    // Priorité (calculée selon examDateTimeMillis, mais stockée aussi)
    val priority: PriorityType = PriorityType.Moyenne
)
