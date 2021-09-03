package com.github.rexfilius.notes.data.repository

import androidx.lifecycle.LiveData
import com.github.rexfilius.notes.data.local.NoteDatabase
import com.github.rexfilius.notes.model.Note

class NoteRepository(private val noteDatabase: NoteDatabase) : Repository {

    override suspend fun insertInDB(note: Note) {
        noteDatabase.noteDao.insert(note)
    }

    override suspend fun updateInDB(note: Note) {
        noteDatabase.noteDao.update(note)
    }

    override suspend fun deleteInDB(note: Note) {
        noteDatabase.noteDao.delete(note)
    }

    override suspend fun deleteAllInDB() {
        noteDatabase.noteDao.deleteAllNotes()
    }

    override fun getAllNotesInDB(): LiveData<List<Note>> {
        return noteDatabase.noteDao.getAllNotes()
    }

    override fun getNoteInDB(noteId: Long): LiveData<Note> {
        return noteDatabase.noteDao.getNote(noteId)
    }

}