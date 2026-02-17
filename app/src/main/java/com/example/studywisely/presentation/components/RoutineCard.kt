package com.example.studywisely.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.studywisely.data.model.PriorityType
import com.example.studywisely.data.model.RoutineVM

@Composable
fun RoutineCard(
    routine: RoutineVM,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = routine.title,
                style = MaterialTheme.typography.titleMedium
            )
            if (routine.timeLabel.isNotBlank()) {
                Text(
                    text = routine.timeLabel,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Text(
                text = "Priorité: ${routine.priority.label}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
