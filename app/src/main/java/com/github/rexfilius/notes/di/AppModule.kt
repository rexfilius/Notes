package com.github.rexfilius.notes.di

import android.content.Context
import androidx.room.Room
import com.github.rexfilius.notes.data.source.local.DataSource
import com.github.rexfilius.notes.data.repository.NotesRepository
import com.github.rexfilius.notes.data.repository.Repository
import com.github.rexfilius.notes.data.source.local.LocalDataSource
import com.github.rexfilius.notes.data.source.local.NotesDao
import com.github.rexfilius.notes.data.source.local.NotesDatabase
import com.github.rexfilius.notes.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNotesDao(
        database: NotesDatabase
    ): NotesDao = database.notesDao

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NotesDatabase {
        return Room.databaseBuilder(
            context,
            NotesDatabase::class.java,
            "notes_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDispatchers(): DispatcherProvider =
        object : DispatcherProvider {
            override val main: CoroutineDispatcher
                get() = Dispatchers.Main
            override val io: CoroutineDispatcher
                get() = Dispatchers.IO
            override val default: CoroutineDispatcher
                get() = Dispatchers.Default
            override val unconfined: CoroutineDispatcher
                get() = Dispatchers.Unconfined
        }

    @Provides
    @Singleton
    fun provideLocalDataSource(
        notesDao: NotesDao,
        dispatcher: DispatcherProvider
    ): DataSource = LocalDataSource(notesDao, dispatcher)

    @Provides
    @Singleton
    fun provideNotesRepository(
        dataSource: DataSource,
        dispatcher: DispatcherProvider
    ): Repository = NotesRepository(dataSource, dispatcher)

}
