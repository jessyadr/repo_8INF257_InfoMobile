package com.example.studywisely.data.local

import androidx.room.*
import com.example.studywisely.data.model.RoutineEntity

@Database(entities = [RoutineEntity::class], version = 1)
abstract class RoutinesDatabase : RoomDatabase() {
    abstract val dao: RoutineDao

    companion object {
        const val DATABASE_NAME = "routines.db"
    }
}