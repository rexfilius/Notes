package com.github.rexfilius.prioritynotes.ui.addeditnote

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.rexfilius.prioritynotes.data.model.Note
import com.github.rexfilius.prioritynotes.data.local.NoteDatabase
import com.github.rexfilius.prioritynotes.data.repository.NoteRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddEditNoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = NoteRepository(NoteDatabase.getInstance(application))

    fun insertNote(note: Note) = viewModelScope.launch { repository.insertInDB(note) }

    fun updateNote(note: Note) = viewModelScope.launch { repository.updateInDB(note) }

    fun getANoteById(noteId: Long) = repository.getANoteById(noteId)

//    private val _note = MutableLiveData<Note>()
//    val note: LiveData<Note>
//        get() = _note
//
//    suspend fun getANoteById(noteId: Long):Note {
//        val note = viewModelScope.async {
//            _note.value = repository.getANoteById(noteId)
//        }
//        return note
//    }

//    suspend fun getANoteById(noteId: Long): Note =
//        withContext(viewModelScope.coroutineContext) {
//            repository.getANoteById(noteId)
//        }

}
