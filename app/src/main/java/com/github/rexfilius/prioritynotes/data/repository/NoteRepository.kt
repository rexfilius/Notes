package com.github.rexfilius.prioritynotes.data.repository

import androidx.lifecycle.LiveData
import com.github.rexfilius.prioritynotes.data.local.NoteDatabase
import com.github.rexfilius.prioritynotes.data.model.Note
import kotlinx.coroutines.*

class NoteRepository(private val noteDatabase: NoteDatabase) {

    suspend fun insertInDB(note: Note) = noteDatabase.noteDao.insert(note)

    suspend fun updateInDB(note: Note) = noteDatabase.noteDao.update(note)

    suspend fun deleteInDB(note: Note) = noteDatabase.noteDao.delete(note)

    suspend fun deleteAllInDB() = noteDatabase.noteDao.deleteAllNotes()

    fun getAllNotes(): LiveData<List<Note>> = noteDatabase.noteDao.getAllNotes()

//    suspend fun getANoteById(noteId: Long): Note {
//        GlobalScope.launch {
//            noteDatabase.noteDao.getANoteById(noteId)
//        }
//
//    }

    fun getANoteById(noteId: Long): LiveData<Note> = noteDatabase.noteDao.getANoteById(noteId)

}