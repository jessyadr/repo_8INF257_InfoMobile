package com.example.studywisely

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.room.*
import com.example.studywisely.data.local.RoutinesDatabase
import com.example.studywisely.navigation.AppNavHost
import com.example.studywisely.ui.theme.StudyWiselyTheme

class MainActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            RoutinesDatabase::class.java,
            RoutinesDatabase.DATABASE_NAME
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            StudyWiselyTheme {
                AppNavHost()
            }
        }
    }
}