package com.github.rexfilius.prioritynotes.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.github.rexfilius.prioritynotes.data.model.Note

@Dao
interface NoteDao {

    @Insert
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("DELETE FROM notes")
    suspend fun deleteAllNotes()

    @Query("SELECT * FROM notes ORDER BY priority DESC")
    fun getAllNotes(): LiveData<List<Note>>

//    @Query("SELECT * from notes WHERE id = :noteId")
//    suspend fun getANoteById(noteId: Long): Note

    @Query("SELECT * from notes WHERE id = :noteId")
    fun getANoteById(noteId: Long): LiveData<Note>
}