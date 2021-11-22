package com.github.rexfilius.notes.data.repository

import com.github.rexfilius.notes.MainCoroutineRule
import com.github.rexfilius.notes.data.source.local.FakeLocalDataSource
import com.github.rexfilius.notes.getOrAwaitValueTest
import com.github.rexfilius.notes.model.Note
import com.github.rexfilius.notes.util.DispatcherProvider
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named

/**
 * Test did not run
 */
@ExperimentalCoroutinesApi
class NotesRepositoryTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Inject
    @Named("tests_dispatcher")
    lateinit var dispatcher: DispatcherProvider

    private lateinit var notesLocalDataSource: FakeLocalDataSource
    private lateinit var notesRepository: NotesRepository

    private val note1 = Note("Title 1", "Desc 1", 1)
    private val note2 = Note("Title 2", "Desc 2", 2)
    private val note3 = Note("Title 3", "Desc 3", 3)

    private val localNoteSource = listOf(note1, note2, note3)

    @Before
    fun setup() {
        notesLocalDataSource = FakeLocalDataSource(localNoteSource.toMutableList())

        notesRepository = NotesRepository(
            localDataSource = notesLocalDataSource,
            dispatcher = dispatcher
        )
    }

    @Test
    fun `insert note to database`() =
        mainCoroutineRule.runBlockingTest {
            val note = Note("Title X", "Desc X", 10)
            notesRepository.insertInDB(note)

            val all = notesRepository.getAllNotesInDB().getOrAwaitValueTest()
            assertThat(all).contains(note)
        }

}