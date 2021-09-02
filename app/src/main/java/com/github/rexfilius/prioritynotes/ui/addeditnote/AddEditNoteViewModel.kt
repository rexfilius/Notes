package com.github.rexfilius.prioritynotes.ui.addeditnote

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.github.rexfilius.prioritynotes.model.Note
import com.github.rexfilius.prioritynotes.data.local.NoteDatabase
import com.github.rexfilius.prioritynotes.data.repository.NoteRepository
import kotlinx.coroutines.launch

class AddEditNoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = NoteRepository(NoteDatabase.getInstance(application))

    fun insertNote(note: Note) = viewModelScope.launch { repository.insertInDB(note) }

    fun updateNote(note: Note) = viewModelScope.launch { repository.updateInDB(note) }

    fun getNote(noteId: Long) = repository.getNoteInDB(noteId)

}
