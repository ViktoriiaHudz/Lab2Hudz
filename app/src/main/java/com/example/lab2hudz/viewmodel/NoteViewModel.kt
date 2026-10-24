
package com.example.lab2hudz.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab2hudz.model.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted

class NoteViewModel : ViewModel() {

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    private val _expandedNoteId = MutableStateFlow<Int?>(null)
    val expandedNoteId: StateFlow<Int?> = _expandedNoteId.asStateFlow()

    private var nextId = 1

    val noteCount: StateFlow<Int> = notes
        .map { it.size }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = 0
        )

    fun addNote(text: String) {
        val cleanText = text.trim()
        if (cleanText.isBlank()) return

        val note = Note(
            id = nextId++,
            text = cleanText,
            timestamp = System.currentTimeMillis()
        )

        _notes.value = _notes.value + note
    }

    fun toggleNote(id: Int) {
        _expandedNoteId.value =
            if (_expandedNoteId.value == id) null else id
    }

    fun deleteNote(id: Int) {
        _notes.value = _notes.value.filterNot { it.id == id }

        if (_expandedNoteId.value == id) {
            _expandedNoteId.value = null
        }
    }
}
