package com.example.studywisely.data.repository

import com.example.studywisely.data.local.RoutinesDatabase
import com.example.studywisely.data.model.PriorityType
import com.example.studywisely.data.model.RoutineEntity
import com.example.studywisely.data.model.RoutineVM
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoutineRepository(private val db: RoutinesDatabase) {
    fun getAllRoutines(): Flow<List<RoutineVM>> {
        return db.dao.getAllRoutines().map { list ->
            list.map { RoutineVM.fromEntity(it) }
        }
    }

    suspend fun getRoutineById(id: Int): RoutineVM? {
        return db.dao.getRoutineById(id)?.let { RoutineVM.fromEntity(it) }
    }

    suspend fun insertRoutine(routine: RoutineVM) {
        db.dao.insert(RoutineVM.toEntity(routine))
    }

    suspend fun updateRoutine(routine: RoutineVM) {
        db.dao.update(RoutineVM.toEntity(routine))
    }

    suspend fun deleteRoutine(id: Int) {
        db.dao.deleteById(id)
    }

}