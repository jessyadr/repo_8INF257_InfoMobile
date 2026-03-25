package com.example.studywisely.data.local

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.studywisely.data.model.PriorityType
import com.example.studywisely.data.model.RoutineVM

class RoutineDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "routine_db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE routines (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                title TEXT,
                description TEXT,
                routineDateTimeMillis INTEGER,
                examDateTimeMillis INTEGER,
                priority TEXT
            )
        """.trimIndent()

        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS routines")
        onCreate(db)
    }

    fun insertRoutine(routine: RoutineVM) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("title", routine.title)
            put("description", routine.description)
            put("routineDateTimeMillis", routine.routineDateTimeMillis)
            put("examDateTimeMillis", routine.examDateTimeMillis)
            put("priority", routine.priority.toString())
        }
        db.insert("routines", null, values)
    }

    fun updateRoutine(routine: RoutineVM) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("title", routine.title)
            put("description", routine.description)
            put("routineDateTimeMillis", routine.routineDateTimeMillis)
            put("examDateTimeMillis", routine.examDateTimeMillis)
            put("priority", routine.priority.toString())
        }

        db.update(
            "routines",
            values,
            "id = ?",
            arrayOf(routine.id.toString())
        )
    }

    fun getAllRoutines(): List<RoutineVM> {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM routines", null)

        val list = mutableListOf<RoutineVM>()

        while (cursor.moveToNext()) {

            val priorityString =
                cursor.getString(cursor.getColumnIndexOrThrow("priority"))

            val priority = when (priorityString.lowercase()) {
                "faible" -> PriorityType.Faible
                "elevee", "elevée" -> PriorityType.Elevee
                else -> PriorityType.Moyenne
            }

            val routine = RoutineVM(
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                title = cursor.getString(cursor.getColumnIndexOrThrow("title")),
                description = cursor.getString(cursor.getColumnIndexOrThrow("description")),
                routineDateTimeMillis = cursor.getLong(cursor.getColumnIndexOrThrow("routineDateTimeMillis")),
                examDateTimeMillis = cursor.getLong(cursor.getColumnIndexOrThrow("examDateTimeMillis")),
                priority = priority
            )

            list.add(routine)
        }

        cursor.close()
        return list
    }

    fun deleteRoutine(id: Int) {
        val db = writableDatabase
        db.delete("routines", "id = ?", arrayOf(id.toString()))
    }
}