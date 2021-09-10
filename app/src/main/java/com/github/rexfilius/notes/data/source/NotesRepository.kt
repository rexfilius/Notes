package com.github.rexfilius.notes.data.source

import androidx.lifecycle.LiveData
import com.github.rexfilius.notes.data.source.local.NotesDatabase
import com.github.rexfilius.notes.model.Note

class NotesRepository(private val notesDatabase: NotesDatabase) : Repository {

    override suspend fun insertInDB(note: Note) {
        notesDatabase.notesDao.insert(note)
    }

    override suspend fun updateInDB(note: Note) {
        notesDatabase.notesDao.update(note)
    }

    override suspend fun deleteInDB(note: Note) {
        notesDatabase.notesDao.delete(note)
    }

    override suspend fun deleteAllInDB() {
        notesDatabase.notesDao.deleteAllNotes()
    }

    override fun getAllNotesInDB(): LiveData<List<Note>> {
        return notesDatabase.notesDao.getAllNotes()
    }

    override fun getNoteInDB(noteId: Long): LiveData<Note> {
        return notesDatabase.notesDao.getNote(noteId)
    }

}