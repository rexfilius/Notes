package com.github.rexfilius.notes.ui.addeditnote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.github.rexfilius.notes.MainCoroutineRule
import com.github.rexfilius.notes.data.repository.FakeNotesRepository
import com.github.rexfilius.notes.getOrAwaitValueTest
import com.github.rexfilius.notes.model.Note
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class AddEditNoteViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var notesRepository: FakeNotesRepository
    private lateinit var addEditNoteViewModel: AddEditNoteViewModel

    @Before
    fun setup() {
        notesRepository = FakeNotesRepository()
        addEditNoteViewModel = AddEditNoteViewModel(
            savedStateHandle = null,
            repository = notesRepository
        )
    }

    @Test
    fun `insert note`() {
        val note = Note("Note Z", "Desc Z", 11)
        addEditNoteViewModel.insertNote(note)

        val allNotes = notesRepository.getAllNotesInDB().getOrAwaitValueTest()
        assertThat(allNotes).contains(note)
    }
}