package com.github.rexfilius.notes.data.source

import androidx.lifecycle.LiveData
import com.github.rexfilius.notes.model.Note

interface DataSource {

    suspend fun insertInDB(note: Note)

    suspend fun updateInDB(note: Note)

    suspend fun deleteInDB(note: Note)

    suspend fun deleteAllNotesInDB()

    fun getAllNotesInDB(): LiveData<List<Note>>

    fun getNoteInDB(noteId: Long): LiveData<Note>

}