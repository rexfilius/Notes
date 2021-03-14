package com.github.rexfilius.prioritynotes.notes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.github.rexfilius.prioritynotes.data.Note
import com.github.rexfilius.prioritynotes.data.NoteDatabase
import com.github.rexfilius.prioritynotes.data.NoteRepository
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private val noteRepository = NoteRepository(NoteDatabase.getInstance(application))

    // may not be needed in NotesFragment
    fun insertNote(note: Note) = viewModelScope.launch { noteRepository.insertInDB(note) }

    fun deleteNote(note: Note) = viewModelScope.launch { noteRepository.deleteInDB(note) }

    // may not be needed in NotesFragment
    fun updateNote(note: Note) = viewModelScope.launch { noteRepository.updateInDB(note) }

    fun deleteAllNotes() = viewModelScope.launch { noteRepository.deleteAllInDB() }

    fun getAllNotes() = noteRepository.getAllNotes()
}