
package com.example.lab2hudz.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lab2hudz.viewmodel.NoteViewModel

private val Navy = Color(0xFF142B4A)
private val Blue = Color(0xFF315A85)
private val BlueBorder = Color(0xFF7899BF)
private val Background = Color(0xFFF4F7FC)

@Composable
fun NoteScreen(
    noteViewModel: NoteViewModel = viewModel()
) {
    val notes by noteViewModel.notes.collectAsStateWithLifecycle()
    val noteCount by noteViewModel.noteCount.collectAsStateWithLifecycle()
    val expandedNoteId by noteViewModel.expandedNoteId.collectAsStateWithLifecycle()

    var noteText by rememberSaveable {
        mutableStateOf("")
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 18.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = "🐈‍⬛ Мої нотатки",
                style = MaterialTheme.typography.headlineMedium,
                color = Navy
            )

            Text(
                text = "🐾 Нотаток: $noteCount",
                style = MaterialTheme.typography.titleMedium,
                color = Blue
            )

            OutlinedTextField(
                value = noteText,
                onValueChange = { noteText = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Текст нової нотатки") },
                minLines = 2,
                maxLines = 5,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Blue,
                    unfocusedBorderColor = BlueBorder,
                    focusedLabelColor = Blue,
                    cursorColor = Blue,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Button(
                onClick = {
                    noteViewModel.addNote(noteText)
                    noteText = ""
                },
                enabled = noteText.isNotBlank(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Navy,
                    contentColor = Color.White,
                    disabledContainerColor = Color(0xFFD5DEEA),
                    disabledContentColor = Color(0xFF718096)
                ),
                contentPadding = PaddingValues(
                    horizontal = 16.dp,
                    vertical = 12.dp
                )
            ) {
                Text(
                    text = "🐾 Зберегти нотатку",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            if (notes.isEmpty()) {
                Text(
                    text = "Тут поки порожньо 🐱\nДодай свою першу нотатку!",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Blue
                )
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(
                        top = 2.dp,
                        bottom = 16.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = notes,
                        key = { note -> note.id }
                    ) { note ->
                        NoteCard(
                            note = note,
                            isExpanded = expandedNoteId == note.id,
                            onToggle = {
                                noteViewModel.toggleNote(note.id)
                            },
                            onDelete = {
                                noteViewModel.deleteNote(note.id)
                            }
                        )
                    }
                }
            }
        }
    }
}
