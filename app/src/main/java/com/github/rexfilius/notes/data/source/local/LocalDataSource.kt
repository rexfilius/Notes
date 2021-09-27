package com.github.rexfilius.notes.data.source.local

import androidx.lifecycle.LiveData
import com.github.rexfilius.notes.data.source.DataSource
import com.github.rexfilius.notes.model.Note
import com.github.rexfilius.notes.util.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val notesDao: NotesDao,
    private val dispatcher: DispatcherProvider
) : DataSource {

    override suspend fun insertInDB(note: Note) =
        withContext(dispatcher.io) {
            notesDao.insert(note)
        }

    override suspend fun updateInDB(note: Note) =
        withContext(dispatcher.io) {
            notesDao.update(note)
        }

    override suspend fun deleteInDB(note: Note) =
        withContext(dispatcher.io) {
            notesDao.delete(note)
        }

    override suspend fun deleteAllNotesInDB() =
        withContext(dispatcher.io) {
            notesDao.deleteAllNotes()
        }

    override fun getAllNotesInDB(): LiveData<List<Note>> {
        return notesDao.getAllNotes()
    }

    override fun getNoteInDB(noteId: Long): LiveData<Note> {
        return notesDao.getNote(noteId)
    }

}