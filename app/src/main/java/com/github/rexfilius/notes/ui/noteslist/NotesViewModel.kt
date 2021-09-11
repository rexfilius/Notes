package com.github.rexfilius.notes.ui.noteslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.rexfilius.notes.data.source.Repository
import com.github.rexfilius.notes.model.Note
import kotlinx.coroutines.launch

class NotesViewModel(
    private val repository: Repository
) : ViewModel() {

    fun deleteNote(note: Note) = viewModelScope.launch { repository.deleteInDB(note) }

    fun deleteAllNotes() = viewModelScope.launch { repository.deleteAllInDB() }

    fun getAllNotes() = repository.getAllNotesInDB()

}