
package com.example.lab2hudz.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.lab2hudz.model.Note
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val Navy = Color(0xFF142B4A)
private val BlueBorder = Color(0xFF5279A8)
private val CardBackground = Color(0xFFEAF1FA)
private val TextColor = Color(0xFF1D304A)

@Composable
fun NoteCard(
    note: Note,
    isExpanded: Boolean,
    onToggle: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val formattedTime = SimpleDateFormat(
        "dd.MM.yyyy HH:mm",
        Locale.getDefault()
    ).format(Date(note.timestamp))

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onToggle() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBackground
        ),
        border = BorderStroke(1.5.dp, BlueBorder),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "🐱 $formattedTime",
                    style = MaterialTheme.typography.labelMedium,
                    color = BlueBorder
                )

                TextButton(onClick = onDelete) {
                    Text(
                        text = "×",
                        color = Navy,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }

            Text(
                text = if (isExpanded || note.text.length <= 50) {
                    note.text
                } else {
                    note.text.take(50) + "..."
                },
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodyLarge,
                color = TextColor,
                maxLines = if (isExpanded) Int.MAX_VALUE else 2,
                overflow = TextOverflow.Ellipsis
            )

            if (note.text.length > 50) {
                TextButton(onClick = onToggle) {
                    Text(
                        text = if (isExpanded) {
                            "▲ Згорнути"
                        } else {
                            "▼ Читати повністю"
                        },
                        color = Navy
                    )
                }
            }
        }
    }
}
