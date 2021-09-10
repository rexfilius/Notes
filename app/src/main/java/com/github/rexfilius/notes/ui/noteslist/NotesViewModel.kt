package com.github.rexfilius.notes.ui.noteslist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.github.rexfilius.notes.data.source.NotesRepository
import com.github.rexfilius.notes.data.source.local.NotesDatabase
import com.github.rexfilius.notes.model.Note
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = NotesRepository(NotesDatabase.getInstance(application))
	// TODO: Remove repository here and put in constructor and also extend ViewModel()
	// TODO: the above todo applies to AddEditViewModel class
	// TODO: and I think a ViewModelFactory has to be created for the two viewmodels

    fun deleteNote(note: Note) = viewModelScope.launch { repository.deleteInDB(note) }

    fun deleteAllNotes() = viewModelScope.launch { repository.deleteAllInDB() }

    fun getAllNotes() = repository.getAllNotesInDB()

}