package com.example.studywisely.presentation.list

import androidx.compose.foundation.layout.*
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            Text(
                text = "StudyWisely",
                color = PurpleMain
            )

            Text(
                text = "Votre assistant de réussite",
                color = PurpleMain
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Mes routines",
                color = PurpleText
            )

            Spacer(modifier = Modifier.height(12.dp))

            routines.forEach { routine ->
                RoutineCard(
                    routine = routine,
                    modifier = Modifier.padding(vertical = 6.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    navController.navigate(Screen.AddEditRoutineScreen.route)
                },
                colors = ButtonDefaults.buttonColors(containerColor = PurpleMain),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = "Ajouter une nouvelle routine",
                    color = White
                )
            }
        }
    }
}
