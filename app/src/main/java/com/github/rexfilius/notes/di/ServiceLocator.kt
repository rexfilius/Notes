package com.github.rexfilius.notes.di

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Room
import com.github.rexfilius.notes.data.source.DataSource
import com.github.rexfilius.notes.data.source.NotesRepository
import com.github.rexfilius.notes.data.source.Repository
import com.github.rexfilius.notes.data.source.local.LocalDataSource
import com.github.rexfilius.notes.data.source.local.NotesDatabase

object ServiceLocator {

    private val lock = Any()

    private var database: NotesDatabase? = null

    /**
     * Mark the setter for tasksRepository as @VisibleForTesting. This annotation is a
     * way to express that the reason the setter is public is because of testing.
     */
    @Volatile
    var repository: Repository? = null
        @VisibleForTesting set

    /**
     * - Since the ServiceLocator is a singleton, it has the possibility of being
     * accidentally shared between tests. To help avoid this, create a method that
     * properly resets the ServiceLocator state between tests.
     * - NOTE:: One of the downsides of using a service locator is that it is a shared
     * singleton. In addition to needing to reset the state of the service locator when
     * the test finishes, you cannot run tests in parallel. This doesn't happen when you
     * use dependency injection, which is one of the reasons to prefer constructor
     * dependency injection when you can use it.
     */
    @VisibleForTesting
    fun resetRepository() {
        synchronized(lock) {
            // Clear all data to avoid test pollution.
            database?.apply {
                clearAllTables()
                close()
            }
            database = null
            repository = null
        }
    }

    fun provideNotesRepository(context: Context): Repository {
        synchronized(lock) {
            return repository ?: createNotesRepository(context)
        }
    }

    private fun createNotesRepository(context: Context): Repository {
        val newRepo = NotesRepository(createLocalDataSource(context))
        repository = newRepo
        return newRepo
    }

    private fun createLocalDataSource(context: Context): DataSource {
        val database = database ?: createDatabase(context)
        return LocalDataSource(database.notesDao)
    }

    private fun createDatabase(context: Context): NotesDatabase {
        val result = Room.databaseBuilder(
            context.applicationContext,
            NotesDatabase::class.java,
            "notes_database"
        ).build()
        database = result
        return result
    }

}