package com.github.rexfilius.prioritynotes.data

import androidx.lifecycle.LiveData

class NoteRepository(private val noteDatabase: NoteDatabase) {

    suspend fun insertNote(note: Note) = noteDatabase.noteDao.insert(note)

    suspend fun updateNote(note: Note) = noteDatabase.noteDao.update(note)

    suspend fun deleteNote(note: Note) = noteDatabase.noteDao.delete(note)

    suspend fun deleteAllNotes() = noteDatabase.noteDao.deleteAllNotes()

    fun getAllNotes(): LiveData<List<Note>> = noteDatabase.noteDao.getAllNotes()

}