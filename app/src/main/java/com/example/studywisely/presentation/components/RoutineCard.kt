package com.example.studywisely.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.studywisely.data.model.RoutineVM
import com.example.studywisely.ui.theme.PurpleMain
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun RoutineCard(
    routine: RoutineVM,
    onDelete: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.fillMaxWidth()) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(modifier = Modifier.weight(1f)) {

                Text(
                    text = routine.title,
                    style = MaterialTheme.typography.titleMedium
                )

                if (routine.description.isNotBlank()) {
                    Text(
                        text = routine.description,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Text(
                    text = "Priorité: ${routine.priority}",
                    style = MaterialTheme.typography.bodySmall
                )

                routine.examDateTimeMillis?.let {
                    Text(
                        text = "Examen: ${formatDateTime(it)}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            // BOUTON POUBELLE VIOLET (version compatible)
            IconButton(
                onClick = { onDelete(routine.id) },
                modifier = Modifier
                    .size(42.dp)
            ) {
                Surface(
                    shape = CircleShape,
                    color = PurpleMain
                ) {
                    Box(
                        modifier = Modifier.padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Supprimer",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    }
}

private fun formatDateTime(millis: Long): String {
    val sdf = SimpleDateFormat("EEE d MMM • HH:mm", Locale.FRENCH)
    return sdf.format(Date(millis))
}
