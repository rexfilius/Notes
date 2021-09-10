package com.github.rexfilius.notes.ui.addeditnote

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.github.rexfilius.notes.model.Note
import com.github.rexfilius.notes.data.source.local.NotesDatabase
import com.github.rexfilius.notes.data.source.NotesRepository
import kotlinx.coroutines.launch

class AddEditNoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = NotesRepository(NotesDatabase.getInstance(application))

    fun insertNote(note: Note) = viewModelScope.launch { repository.insertInDB(note) }

    fun updateNote(note: Note) = viewModelScope.launch { repository.updateInDB(note) }

    fun getNote(noteId: Long) = repository.getNoteInDB(noteId)

}
