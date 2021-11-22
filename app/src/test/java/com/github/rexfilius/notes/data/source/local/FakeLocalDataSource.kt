package com.github.rexfilius.notes.data.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.rexfilius.notes.model.Note

class FakeLocalDataSource(
    var notesList: MutableList<Note>? = mutableListOf()
) : DataSource {

    private val observableNotes = MutableLiveData<List<Note>>(notesList)
    private val observableNote = MutableLiveData<Note>()

    override suspend fun insertInDB(note: Note) {
        notesList?.add(note)
        refreshLiveData()
    }

    // TODO: RE-CHECK
    override suspend fun updateInDB(note: Note) {
        notesList?.set(note.id.toInt(), note)
    }

    override suspend fun deleteInDB(note: Note) {
        notesList?.remove(note)
        refreshLiveData()
    }

    override suspend fun deleteAllNotesInDB() {
        notesList?.clear()
        refreshLiveData()
    }

    override fun getAllNotesInDB(): LiveData<List<Note>> {
        return observableNotes
    }

    // TODO: RE-CHECK
    override fun getNoteInDB(noteId: Long): LiveData<Note> {
        return observableNote
    }

    private fun refreshLiveData() {
        observableNotes.postValue(notesList)
    }
}