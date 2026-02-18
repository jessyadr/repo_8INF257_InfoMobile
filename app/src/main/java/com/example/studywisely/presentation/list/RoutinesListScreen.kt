package com.example.studywisely.presentation.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.studywisely.navigation.Screen
import com.example.studywisely.presentation.components.RoutineCard
import com.example.studywisely.ui.theme.*
import com.example.studywisely.viewmodel.ListRoutinesViewModel

@Composable
fun RoutinesListScreen(
    navController: NavController,
    viewModel: ListRoutinesViewModel = viewModel()
) {
    val routines = viewModel.routines.value

    LaunchedEffect(Unit) {
        viewModel.refresh()
    }

    Scaffold(
        containerColor = BackgroundMain
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            // 🔹 HEADER (inchangé)
            item {
                Text("StudyWisely", color = PurpleMain)
                Text("Votre assistant de réussite", color = PurpleMain)

                Spacer(modifier = Modifier.height(20.dp))

                Text("Mes routines", color = PurpleText)

                Spacer(modifier = Modifier.height(12.dp))
            }

            // 🔹 LISTE DES ROUTINES
            items(routines, key = { it.id }) { routine ->
                RoutineCard(
                    routine = routine,
                    onDelete = { id ->
                        viewModel.deleteRoutine(id)
                    }
                )
            }

            // 🔹 BOUTON EN BAS (inchangé)
            item {
                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = {
                        navController.navigate(Screen.AddEditRoutineScreen.route)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PurpleMain),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("Ajouter une nouvelle routine", color = White)
                }
            }
        }
    }
}
