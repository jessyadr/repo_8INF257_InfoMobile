package com.example.studywisely.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MapPickerScreen(navController: NavController) {

    var latitude by remember { mutableStateOf(0.0) }
    var longitude by remember { mutableStateOf(0.0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(text = "Sélectionner un lieu", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Latitude : $latitude")
        Text(text = "Longitude : $longitude")

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            // Simulation d'un clic sur la carte (on fera la vraie map après)
            latitude = 48.8566
            longitude = 2.3522
        }) {
            Text("Choisir ce lieu")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            navController.popBackStack()
        }) {
            Text("Valider et revenir")
        }
    }
}
