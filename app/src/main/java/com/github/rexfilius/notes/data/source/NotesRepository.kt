package com.github.rexfilius.notes.data.source

import androidx.lifecycle.LiveData
import com.github.rexfilius.notes.model.Note
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NotesRepository(
    private val localDataSource: DataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : Repository {

    override suspend fun insertInDB(note: Note) =
        withContext(dispatcher) {
            localDataSource.insertInDB(note)
        }

    override suspend fun updateInDB(note: Note) =
        withContext(dispatcher) {
            localDataSource.updateInDB(note)
        }

    override suspend fun deleteInDB(note: Note) =
        withContext(dispatcher) {
            localDataSource.deleteInDB(note)
        }

    override suspend fun deleteAllInDB() =
        withContext(dispatcher) {
            localDataSource.deleteAllNotesInDB()
        }

    override fun getAllNotesInDB(): LiveData<List<Note>> {
        return localDataSource.getAllNotesInDB()
    }

    override fun getNoteInDB(noteId: Long): LiveData<Note> {
        return localDataSource.getNoteInDB(noteId)
    }

}