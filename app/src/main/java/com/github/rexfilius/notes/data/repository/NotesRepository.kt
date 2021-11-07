package com.github.rexfilius.notes.data.repository

import androidx.lifecycle.LiveData
import com.github.rexfilius.notes.data.source.local.DataSource
import com.github.rexfilius.notes.model.Note
import com.github.rexfilius.notes.util.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NotesRepository @Inject constructor(
    private val localDataSource: DataSource,
    private val dispatcher: DispatcherProvider
) : Repository {

    override suspend fun insertInDB(note: Note) =
        withContext(dispatcher.io) {
            localDataSource.insertInDB(note)
        }

    override suspend fun updateInDB(note: Note) =
        withContext(dispatcher.io) {
            localDataSource.updateInDB(note)
        }

    override suspend fun deleteInDB(note: Note) =
        withContext(dispatcher.io) {
            localDataSource.deleteInDB(note)
        }

    override suspend fun deleteAllInDB() =
        withContext(dispatcher.io) {
            localDataSource.deleteAllNotesInDB()
        }

    override fun getAllNotesInDB(): LiveData<List<Note>> {
        return localDataSource.getAllNotesInDB()
    }

    override fun getNoteInDB(noteId: Long): LiveData<Note> {
        return localDataSource.getNoteInDB(noteId)
    }

}