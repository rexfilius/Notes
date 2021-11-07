package com.github.rexfilius.notes.ui.noteslist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.rexfilius.notes.data.repository.Repository
import com.github.rexfilius.notes.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    private val repository: Repository
) : ViewModel() {

    fun deleteNote(note: Note) = viewModelScope.launch { repository.deleteInDB(note) }

    fun deleteAllNotes() = viewModelScope.launch { repository.deleteAllInDB() }

    fun insertNote(note: Note) = viewModelScope.launch { repository.insertInDB(note) }

    fun getAllNotes() = repository.getAllNotesInDB()

}