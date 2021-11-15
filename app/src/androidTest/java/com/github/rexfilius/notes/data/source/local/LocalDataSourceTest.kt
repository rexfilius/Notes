package com.github.rexfilius.notes.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.MediumTest
import com.github.rexfilius.notes.getOrAwaitValue
import com.github.rexfilius.notes.model.Note
import com.github.rexfilius.notes.util.DispatcherProvider
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
@ExperimentalCoroutinesApi
@MediumTest
class LocalDataSourceTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("notes_test_db")
    lateinit var notesDatabase: NotesDatabase

    private lateinit var localDataSource: LocalDataSource

    @Inject
    @Named("test_dispatcher")
    lateinit var dispatcher: DispatcherProvider

    @Before
    fun setup() {
        hiltRule.inject()

        localDataSource = LocalDataSource(
            notesDao = notesDatabase.notesDao,
            dispatcher = dispatcher
        )
    }

    @After
    fun cleanup() {
        notesDatabase.close()
    }

    @Test
    fun insertNoteToDatabase() = runBlocking {
        val note = Note("Note 1", "Desc 1", 1)
        localDataSource.insertInDB(note)

        val allNotes = localDataSource.getAllNotesInDB().getOrAwaitValue()
        assertThat(allNotes).contains(note)
    }

    @Test
    fun updateNoteInDatabase() = runBlocking {
        val note = Note("Note 1", "Desc 1", 1)
        localDataSource.insertInDB(note)

        note.title = "Note 1b"
        localDataSource.updateInDB(note)

        assertThat(note.title).isEqualTo("Note 1b")
    }

    @Test
    fun deleteNoteInDatabase() = runBlocking {
        val note = Note("Note 1", "Desc 1", 1)
        localDataSource.insertInDB(note)
        localDataSource.deleteInDB(note)

        val allNotes = localDataSource.getAllNotesInDB().getOrAwaitValue()
        assertThat(allNotes).doesNotContain(note)
    }

    @Test
    fun deleteAllNotesInDatabase() = runBlocking {
        val note1 = Note("Note 1", "Desc 1", 1)
        val note2 = Note("Note 2", "Desc 2", 2)

        localDataSource.insertInDB(note1)
        localDataSource.insertInDB(note2)

        localDataSource.deleteAllNotesInDB()

        val allNotes = localDataSource.getAllNotesInDB().getOrAwaitValue()
        assertThat(allNotes).isEmpty()
    }

    @Test
    fun getAllNotesInDatabase(): Unit = runBlocking {
        val note1 = Note("Note 1", "Desc 1", 1)
        val note2 = Note("Note 2", "Desc 2", 2)
        val note3 = Note("Note 3", "Desc 3", 3)

        localDataSource.insertInDB(note1)
        localDataSource.insertInDB(note2)
        localDataSource.insertInDB(note3)

        val allNotes = localDataSource.getAllNotesInDB().getOrAwaitValue()
        assertThat(allNotes).containsExactly(note1, note2, note3)
    }

    @Test
    fun getNoteInDatabase() = runBlocking {
        val note1 = Note("Note 1", "Desc 1", 1)
        localDataSource.insertInDB(note1)

        val note = localDataSource.getNoteInDB(1).getOrAwaitValue()
        assertThat(note1.id).isEqualTo(note.id)
    }

}