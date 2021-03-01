package com.github.rexfilius.prioritynotes.data

import androidx.lifecycle.LiveData

class NoteRepository(private val noteDatabase: NoteDatabase) {

    suspend fun insertInDB(note: Note) = noteDatabase.noteDao.insert(note)

    suspend fun updateInDB(note: Note) = noteDatabase.noteDao.update(note)

    suspend fun deleteInDB(note: Note) = noteDatabase.noteDao.delete(note)

    suspend fun deleteAllInDB() = noteDatabase.noteDao.deleteAllNotes()

    fun getAllNotes(): LiveData<List<Note>> = noteDatabase.noteDao.getAllNotes()

}