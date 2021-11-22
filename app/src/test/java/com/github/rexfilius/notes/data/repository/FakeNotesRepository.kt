package com.github.rexfilius.notes.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.rexfilius.notes.model.Note

class FakeNotesRepository : Repository {

    private val notes = mutableListOf<Note>()
    private val observableNotes = MutableLiveData<List<Note>>(notes)
    private val observableNote = MutableLiveData<Note>()

    private fun refreshLiveData() {
        observableNotes.postValue(notes)
    }

    override suspend fun insertInDB(note: Note) {
        notes.add(note)
        refreshLiveData()
    }

    // TODO: RE-CHECK
    override suspend fun updateInDB(note: Note) {
        notes[note.id.toInt()] = note
        refreshLiveData()
    }

    override suspend fun deleteInDB(note: Note) {
        notes.remove(note)
        refreshLiveData()
    }

    override suspend fun deleteAllInDB() {
        notes.clear()
    }

    override fun getAllNotesInDB(): LiveData<List<Note>> {
        return observableNotes
    }

    // TODO: Re-Check
    override fun getNoteInDB(noteId: Long): LiveData<Note> {
        return observableNote
    }
}