package com.github.rexfilius.notes.di

import android.app.Application
import com.github.rexfilius.notes.data.source.Repository

class NotesApplication : Application() {

    val repository: Repository
        get() = ServiceLocator.provideNotesRepository(this)

}