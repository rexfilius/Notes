package com.github.rexfilius.notes.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.github.rexfilius.notes.model.Note

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

    @Query("SELECT * from notes WHERE id = :noteId")
    fun getNote(noteId: Long): LiveData<Note>
}