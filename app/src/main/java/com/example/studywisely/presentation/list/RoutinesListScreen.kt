package com.example.studywisely.presentation.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.studywisely.data.model.RoutineVM
import com.example.studywisely.navigation.Screen
import com.example.studywisely.presentation.components.RoutineCard
import com.example.studywisely.ui.theme.*
import com.example.studywisely.viewmodel.ListRoutinesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutinesListScreen(
    navController: NavController,
    viewModel: ListRoutinesViewModel = viewModel()
) {
    val routines = viewModel.routines.value

    LaunchedEffect(Unit) {
        viewModel.refresh()
    }

    var showDeleteDialog by remember { mutableStateOf(false) }
    var routineToDelete by remember { mutableStateOf<RoutineVM?>(null) }

    Scaffold(
        containerColor = BackgroundMain,
        topBar = {
            TopAppBar(
                modifier= Modifier
                    .height(160.dp),
                title = {
                    Column {
                        Text("StudyWisely", color = PurpleMain)
                        Text("Votre assistant de réussite", color = PurpleMain)

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            modifier= Modifier
                                .padding(5.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ){
                            Text("Mes routines", color = PurpleText)
                            Button(
                                onClick = {
                                    navController.navigate(Screen.AddEditRoutineScreen.route)
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = PurpleMain),
                                modifier = Modifier
                                    .height(50.dp)
                            ) {
                                Text("Ajouter une nouvelle routine", color = White)
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            // 🔹 LISTE DES ROUTINES
            items(routines, key = { it.id }) { routine ->
                RoutineCard(
                    routine = routine,
                    onDelete = {
                        routineToDelete = routine
                        showDeleteDialog = true
                    }
                )
            }
        }

        if (showDeleteDialog && routineToDelete != null) {
            AlertDialog(
                onDismissRequest = {
                    showDeleteDialog = false
                    routineToDelete = null
                },
                text = {
                    Text("Voulez-vous vraiment supprimer la routine \"${routineToDelete!!.title}\" ?",
                        style= TextStyle(textAlign = TextAlign.Center),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold)
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.deleteRoutine(routineToDelete!!.id)
                            showDeleteDialog = false
                            routineToDelete = null
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PurpleMain)
                    ) {
                        Text("Oui",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold)
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showDeleteDialog = false
                            routineToDelete = null
                        }
                    ) {
                        Text("Non",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold)
                    }
                }
            )
        }
    }
}
