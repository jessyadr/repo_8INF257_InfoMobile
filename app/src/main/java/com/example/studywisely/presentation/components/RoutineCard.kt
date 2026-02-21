package com.example.studywisely.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studywisely.data.model.RoutineVM
import com.example.studywisely.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun RoutineCard(
    routine: RoutineVM,
    onDelete: (Int) -> Unit
) {
    val sdf = SimpleDateFormat("dd MMM yyyy • HH:mm", Locale.FRENCH)

    val routineFormatted = routine.routineDateTimeMillis?.let { sdf.format(Date(it)) } ?: "Non définie"
    val examFormatted = routine.examDateTimeMillis?.let { sdf.format(Date(it)) } ?: "Non définie"

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column(modifier = Modifier.weight(1f)) {

                Text(
                    text = routine.title,
                    color = PurpleMain,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )

                Text(
                    text = "Priorité: ${routine.priority}",
                    fontSize = 18.sp
                )

                Text(
                    text = "À faire le: $routineFormatted",
                    fontSize = 18.sp
                )

                Text(
                    text = "Examen/Livrable: $examFormatted",
                    fontSize = 18.sp
                )
            }

            IconButton(
                onClick = { onDelete(routine.id) }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Supprimer",
                    tint = PurpleMain,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}