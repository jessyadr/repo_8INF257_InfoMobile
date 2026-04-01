package com.example.studywisely.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.studywisely.data.model.PriorityType

@Entity(tableName = "routines")
data class RoutineEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val title: String,
    val description: String? = null,
    val routineDateTimeMillis: Long? = null,
    val examDateTimeMillis: Long? = null,
    val priority: PriorityType = PriorityType.Moyenne
)