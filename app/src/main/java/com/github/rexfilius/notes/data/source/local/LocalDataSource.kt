package com.github.rexfilius.notes.data.source.local

import androidx.lifecycle.LiveData
import com.github.rexfilius.notes.data.source.DataSource
import com.github.rexfilius.notes.model.Note
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalDataSource internal constructor(
    private val notesDao: NotesDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : DataSource {

    override suspend fun insertInDB(note: Note) =
        withContext(dispatcher) {
            notesDao.insert(note)
        }

    override suspend fun updateInDB(note: Note) =
        withContext(dispatcher) {
            notesDao.update(note)
        }

    override suspend fun deleteInDB(note: Note) =
        withContext(dispatcher) {
            notesDao.delete(note)
        }

    override suspend fun deleteAllNotesInDB() =
        withContext(dispatcher) {
            notesDao.deleteAllNotes()
        }

    override fun getAllNotesInDB(): LiveData<List<Note>> {
        return notesDao.getAllNotes()
    }

    override fun getNoteInDB(noteId: Long): LiveData<Note> {
        return notesDao.getNote(noteId)
    }

}