package com.example.studywisely

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.studywisely.navigation.AppNavHost
import com.example.studywisely.ui.theme.StudyWiselyTheme

class MainActivity : ComponentActivity() {
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