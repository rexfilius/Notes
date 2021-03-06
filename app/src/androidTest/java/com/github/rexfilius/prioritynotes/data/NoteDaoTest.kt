package com.github.rexfilius.prioritynotes.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.github.rexfilius.prioritynotes.getOrAwaitValue
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

    private lateinit var noteDatabase: NoteDatabase
    private lateinit var noteDao: NoteDao

    @Before
    fun createDatabase() {
        noteDatabase =
            Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                NoteDatabase::class.java
            ).allowMainThreadQueries()
                .build()
        noteDao = noteDatabase.noteDao
    }

    @After
    fun closeDatabase() {
        noteDatabase.close()
    }

    @Test
    fun insertNote() = runBlockingTest {
        val note = Note("Note 1", "Desc 1", 1, 1)
        noteDao.insert(note)

        val allNotes = noteDao.getAllNotes().getOrAwaitValue()

        assertThat(allNotes).contains(note)
    }

    @Test
    fun deleteNote() = runBlockingTest {
        val note = Note("Note 1", "Desc 1", 1, 1)
        noteDao.insert(note)
        noteDao.delete(note)

        val allNotes = noteDao.getAllNotes().getOrAwaitValue()
        assertThat(allNotes).doesNotContain(note)
    }

    @Test
    fun getAllNotes() = runBlockingTest {
        val note1 = Note("Note 1", "Desc 1", 1, 1)
        val note2 = Note("Note 2", "Desc 2", 2, 2)
        val note3 = Note("Note 3", "Desc 3", 3, 3)

        noteDao.insert(note1)
        noteDao.insert(note2)
        noteDao.insert(note3)

        val allNotes = noteDao.getAllNotes().getOrAwaitValue()

        assertThat(allNotes).containsExactly(note1, note2, note3)
    }
}