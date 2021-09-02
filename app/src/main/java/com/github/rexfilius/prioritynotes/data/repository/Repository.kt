package com.github.rexfilius.prioritynotes.data.repository

import androidx.lifecycle.LiveData
import com.github.rexfilius.prioritynotes.model.Note

interface Repository {

    suspend fun insertInDB(note: Note)

    suspend fun updateInDB(note: Note)

    suspend fun deleteInDB(note: Note)

    suspend fun deleteAllInDB()

    fun getAllNotesInDB(): LiveData<List<Note>>

    fun getNoteInDB(noteId: Long): LiveData<Note>

}