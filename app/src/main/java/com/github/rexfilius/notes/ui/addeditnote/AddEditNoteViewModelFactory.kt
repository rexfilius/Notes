package com.github.rexfilius.notes.ui.addeditnote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.rexfilius.notes.data.source.Repository

@Suppress("UNCHECKED_CAST")
class AddEditNoteViewModelFactory(
    private val repository: Repository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (AddEditNoteViewModel(repository) as T)

}