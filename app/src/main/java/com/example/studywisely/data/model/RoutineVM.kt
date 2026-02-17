package com.example.studywisely.data.model

import kotlin.random.Random

data class RoutineVM(
    val id: Int = Random.nextInt(),
    val title: String = "",
    val description: String = "",
    val timeLabel: String = "",     // ex: "Aujourd'hui - 14:00"
    val priority: PriorityType = PriorityType.Moyenne
)
