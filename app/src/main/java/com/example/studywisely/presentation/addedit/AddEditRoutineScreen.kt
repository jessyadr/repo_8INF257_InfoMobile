package com.example.studywisely.presentation.addedit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.OutlinedTextFieldDefaults.colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.studywisely.viewmodel.AddEditRoutineViewModel
import com.example.studywisely.viewmodel.AddEditRoutineEvent

@Composable
fun AddEditRoutineScreen(
    navController: NavController,
    viewModel: AddEditRoutineViewModel = viewModel()
) {
    val routine = viewModel.routine.value

    Scaffold { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            Text(
                "Ajouter une routine",
                color = Color(0xFF6815A8)
            )

            OutlinedTextField(
                value = routine.title,
                onValueChange = {
                    viewModel.onEvent(AddEditRoutineEvent.EnteredTitle(it))
                },
                placeholder = {
                    Text(
                        "Veuillez entrer le nom de votre routine",
                        color = Color(0xFF6815A8)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = colors(
                    focusedContainerColor = Color(0xFFFAF5FF),
                    unfocusedContainerColor = Color(0xFFFAF5FF)
                )
            )

            OutlinedTextField(
                value = routine.description,
                onValueChange = {
                    viewModel.onEvent(AddEditRoutineEvent.EnteredDescription(it))
                },
                placeholder = {
                    Text(
                        "Inscrivez une courte description",
                        color = Color(0xFF6815A8)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = colors(
                    focusedContainerColor = Color(0xFFFAF5FF),
                    unfocusedContainerColor = Color(0xFFFAF5FF)
                )
            )

            OutlinedTextField(
                value = routine.timeLabel,
                onValueChange = {
                    viewModel.onEvent(AddEditRoutineEvent.EnteredTimeLabel(it))
                },
                placeholder = {
                    Text("Date et heure", color = Color(0xFF6815A8))
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = colors(
                    focusedContainerColor = Color(0xFFFAF5FF),
                    unfocusedContainerColor = Color(0xFFFAF5FF)
                )
            )

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = {
                    viewModel.onEvent(AddEditRoutineEvent.SaveRoutine)
                    navController.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF7A0DCE)
                ),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(
                    "Enregistrer la routine",
                    color = Color.White
                )
            }
        }
    }
}
