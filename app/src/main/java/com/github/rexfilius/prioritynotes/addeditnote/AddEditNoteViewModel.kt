package com.github.rexfilius.prioritynotes.addeditnote

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.github.rexfilius.prioritynotes.data.Note
import com.github.rexfilius.prioritynotes.data.NoteDatabase
import com.github.rexfilius.prioritynotes.data.NoteRepository
import kotlinx.coroutines.launch

class AddEditNoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = NoteRepository(NoteDatabase.getInstance(application))

    fun insertNote(note: Note) = viewModelScope.launch { repository.insertInDB(note) }

    fun updateNote(note: Note) = viewModelScope.launch { repository.updateInDB(note) }
}
