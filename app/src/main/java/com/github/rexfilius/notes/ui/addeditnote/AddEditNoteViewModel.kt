package com.github.rexfilius.notes.ui.addeditnote

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.rexfilius.notes.data.source.Repository
import com.github.rexfilius.notes.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    private val repository: Repository
) : ViewModel() {

    fun insertNote(note: Note) = viewModelScope.launch { repository.insertInDB(note) }

    fun updateNote(note: Note) = viewModelScope.launch { repository.updateInDB(note) }

    fun getNote(noteId: Long) = repository.getNoteInDB(noteId)

}
