package com.example.studywisely.presentation.addedit

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.material3.OutlinedTextFieldDefaults.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.studywisely.viewmodel.AddEditRoutineEvent
import com.example.studywisely.viewmodel.AddEditRoutineViewModel
import java.text.SimpleDateFormat
import java.util.*

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
                "Ajouter/modifier une routine",
                color = Color(0xFF6815A8)
            )

            OutlinedTextField(
                value = routine.title,
                onValueChange = {
                    viewModel.onEvent(AddEditRoutineEvent.EnteredTitle(it))
                },
                placeholder = {
                    Text("Veuillez entrer le nom de votre routine")
                },
                modifier = Modifier.fillMaxWidth(),
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
                    Text("Inscrivez une courte description")
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = colors(
                    focusedContainerColor = Color(0xFFFAF5FF),
                    unfocusedContainerColor = Color(0xFFFAF5FF)
                )
            )

            // DATE ROUTINE
            DateTimePickerField(
                label = "Date et heure de la routine",
                placeholder = "Choisir date et heure",
                selectedMillis = routine.routineDateTimeMillis,
                onPicked = {
                    viewModel.onEvent(AddEditRoutineEvent.PickedRoutineDateTime(it))
                }
            )

            // 📅 DATE EXAMEN
            DateTimePickerField(
                label = "Date d'examen ou livrable prévu",
                placeholder = "Veuillez inscrire la date d'examen ou livrable prévu",
                selectedMillis = routine.examDateTimeMillis,
                onPicked = {
                    viewModel.onEvent(AddEditRoutineEvent.PickedExamDateTime(it))
                }
            )

            Spacer(Modifier.height(10.dp))

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
                Text("Enregistrer la routine", color = Color.White)
            }
        }
    }
}

@Composable
private fun DateTimePickerField(
    label: String,
    placeholder: String,
    selectedMillis: Long?,
    onPicked: (Long?) -> Unit
) {
    val context = LocalContext.current
    val calendar = remember { Calendar.getInstance() }

    val formatted = selectedMillis?.let {
        val sdf = SimpleDateFormat("dd MMM yyyy • HH:mm", Locale.FRENCH)
        sdf.format(Date(it))
    } ?: ""

    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {

        Text(text = label, color = Color(0xFF6815A8))

        OutlinedTextField(
            value = formatted,
            onValueChange = {},
            readOnly = true,
            placeholder = { Text(placeholder) },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(
                    onClick = {
                        val now = Calendar.getInstance()

                        DatePickerDialog(
                            context,
                            { _, year, month, day ->

                                calendar.set(Calendar.YEAR, year)
                                calendar.set(Calendar.MONTH, month)
                                calendar.set(Calendar.DAY_OF_MONTH, day)

                                TimePickerDialog(
                                    context,
                                    { _, hour, minute ->

                                        calendar.set(Calendar.HOUR_OF_DAY, hour)
                                        calendar.set(Calendar.MINUTE, minute)

                                        onPicked(calendar.timeInMillis)
                                    },
                                    now.get(Calendar.HOUR_OF_DAY),
                                    now.get(Calendar.MINUTE),
                                    true
                                ).show()

                            },
                            now.get(Calendar.YEAR),
                            now.get(Calendar.MONTH),
                            now.get(Calendar.DAY_OF_MONTH)
                        ).show()
                    }
                ) {
                    Icon(Icons.Default.DateRange, contentDescription = "Choisir date")
                }
            }
        )
    }
}
