package com.example.studywisely.data.model

sealed class PriorityType(val label: String) {
    data object Faible : PriorityType("Faible")
    data object Moyenne : PriorityType("Moyenne")
    data object Elevee : PriorityType("Élevée")
}
