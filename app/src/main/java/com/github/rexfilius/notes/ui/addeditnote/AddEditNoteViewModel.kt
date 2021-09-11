package com.github.rexfilius.notes.ui.addeditnote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.rexfilius.notes.data.source.Repository
import com.github.rexfilius.notes.model.Note
import kotlinx.coroutines.launch

class AddEditNoteViewModel(
    private val repository: Repository
) : ViewModel() {

    fun insertNote(note: Note) = viewModelScope.launch { repository.insertInDB(note) }

    fun updateNote(note: Note) = viewModelScope.launch { repository.updateInDB(note) }

    fun getNote(noteId: Long) = repository.getNoteInDB(noteId)

}
