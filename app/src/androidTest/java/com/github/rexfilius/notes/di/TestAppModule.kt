package com.github.rexfilius.notes.di

import android.content.Context
import androidx.room.Room
import com.github.rexfilius.notes.data.source.local.NotesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Named("notes_test_db")
    fun provideInMemoryDB(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(
            context,
            NotesDatabase::class.java,
        ).allowMainThreadQueries().build()

}