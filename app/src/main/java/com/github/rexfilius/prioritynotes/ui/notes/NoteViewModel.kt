package com.github.rexfilius.prioritynotes.ui.notes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.github.rexfilius.prioritynotes.data.local.NoteDatabase
import com.github.rexfilius.prioritynotes.data.repository.NoteRepository
import com.github.rexfilius.prioritynotes.model.Note
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = NoteRepository(NoteDatabase.getInstance(application))

    fun deleteNote(note: Note) = viewModelScope.launch { repository.deleteInDB(note) }

    fun deleteAllNotes() = viewModelScope.launch { repository.deleteAllInDB() }

    fun getAllNotes() = repository.getAllNotesInDB()

}