package com.github.rexfilius.notes.data.repository.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.github.rexfilius.notes.data.source.local.NotesDao
import com.github.rexfilius.notes.data.source.local.NotesDatabase
import com.github.rexfilius.notes.getOrAwaitValue
import com.github.rexfilius.notes.model.Note
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

// @SmallTest annotation is optional
@ExperimentalCoroutinesApi
@HiltAndroidTest
@SmallTest
class NoteDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("notes_test_db")
    lateinit var notesDatabase: NotesDatabase

    private lateinit var notesDao: NotesDao

    @Before
    fun setup() {
        hiltRule.inject()
        notesDao = notesDatabase.notesDao
    }

    @After
    fun tearDown() {
        notesDatabase.close()
    }

    @Test
    fun insert() = runBlockingTest {
        val note = Note("Note 1", "Desc 1", 1)
        notesDao.insert(note)

        val allNotes = notesDao.getAllNotes().getOrAwaitValue()
        assertThat(allNotes).contains(note)
    }

    @Test
    fun update() = runBlockingTest {
        val note = Note("Note 1", "Desc 1", 1)
        notesDao.insert(note)

        note.description = "Desc 2"
        notesDao.update(note)  // if this line is removed, the test still passes

        assertThat(note.description).isEqualTo("Desc 2")
    }

    @Test
    fun delete() = runBlockingTest {
        val note = Note("Note 1", "Desc 1", 1)
        notesDao.insert(note)
        notesDao.delete(note)

        val allNotes = notesDao.getAllNotes().getOrAwaitValue()
        assertThat(allNotes).doesNotContain(note)
    }

    @Test
    fun deleteAllNotes() = runBlockingTest {
        val note1 = Note("Note 1", "Desc 1", 1)
        val note2 = Note("Note 2", "Desc 2", 2)

        notesDao.insert(note1)
        notesDao.insert(note2)

        notesDao.deleteAllNotes()

        val allNotes = notesDao.getAllNotes().getOrAwaitValue()
        assertThat(allNotes).isEmpty()
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

    @Test
    fun getNote() = runBlockingTest {
        val note1 = Note("Note 1", "Desc 1", 1)
        notesDao.insert(note1)

        val note = notesDao.getNote(1).getOrAwaitValue()
        assertThat(note1.id).isEqualTo(note.id)
    }
}