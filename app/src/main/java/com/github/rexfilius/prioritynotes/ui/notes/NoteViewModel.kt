package com.github.rexfilius.prioritynotes.ui.notes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.github.rexfilius.prioritynotes.data.local.NoteDatabase
import com.github.rexfilius.prioritynotes.data.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val noteRepository = NoteRepository(NoteDatabase.getInstance(application))

    fun deleteAllNotes() = viewModelScope.launch { noteRepository.deleteAllInDB() }

    fun getAllNotes() = noteRepository.getAllNotes()
}