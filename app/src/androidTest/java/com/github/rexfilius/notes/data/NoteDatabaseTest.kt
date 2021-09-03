package com.github.rexfilius.notes.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.github.rexfilius.notes.data.local.NoteDao
import com.github.rexfilius.notes.data.local.NoteDatabase
import com.github.rexfilius.notes.model.Note
import com.github.rexfilius.notes.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.Throws

/**
 * This NoteDatabaseTest is similar to [NoteDaoTest] and the only test function in this class
 * is the same as the function - [NoteDaoTest.insertNote]. I decided to leave this class
 * because it has a slightly different implementation of the Room database.
 */

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class NoteDatabaseTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var noteDao: NoteDao
    private lateinit var noteDatabase: NoteDatabase

    /**
     * In this function that creates a database, an in-memory database is used because the
     * information stored here disappears when the process is killed. And the function -
     * 'allowMainThreadQueries() is for testing purposes
     */
    @Before
    fun createDatabase() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        noteDatabase =
            Room.inMemoryDatabaseBuilder(context, NoteDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        noteDao = noteDatabase.noteDao
    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        noteDatabase.close()
    }

    @Test
    fun insertAndConfirmNoteInDatabase() = runBlockingTest {
        val note = Note("Note 1", "Desc 1", 1, 1)
        noteDao.insert(note)

        val notes = noteDao.getAllNotes().getOrAwaitValue()
        assertThat(notes).contains(note)
    }
}