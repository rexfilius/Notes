package com.github.rexfilius.notes.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.github.rexfilius.notes.data.source.local.NotesDao
import com.github.rexfilius.notes.data.source.local.NotesDatabase
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

// @SmallTest annotation is optional
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class NoteDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var notesDatabase: NotesDatabase
    private lateinit var notesDao: NotesDao

    @Before
    fun createDatabase() {
        notesDatabase =
            Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                NotesDatabase::class.java
            ).allowMainThreadQueries()
                .build()
        notesDao = notesDatabase.notesDao
    }

    @After
    fun closeDatabase() {
        notesDatabase.close()
    }

    @Test
    fun insertNote() = runBlockingTest {
        val note = Note("Note 1", "Desc 1", 1)
        notesDao.insert(note)

        val allNotes = notesDao.getAllNotes().getOrAwaitValue()

        assertThat(allNotes).contains(note)
    }

    @Test
    fun deleteNote() = runBlockingTest {
        val note = Note("Note 1", "Desc 1", 1)
        notesDao.insert(note)
        notesDao.delete(note)

        val allNotes = notesDao.getAllNotes().getOrAwaitValue()
        assertThat(allNotes).doesNotContain(note)
    }

    @Test
    fun getAllNotes() = runBlockingTest {
        val note1 = Note("Note 1", "Desc 1", 1)
        val note2 = Note("Note 2", "Desc 2", 2)
        val note3 = Note("Note 3", "Desc 3", 3)

        notesDao.insert(note1)
        notesDao.insert(note2)
        notesDao.insert(note3)

        val allNotes = notesDao.getAllNotes().getOrAwaitValue()

        assertThat(allNotes).containsExactly(note1, note2, note3)
    }
}