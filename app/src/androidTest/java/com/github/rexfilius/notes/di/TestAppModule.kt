package com.github.rexfilius.notes.di

import android.content.Context
import androidx.room.Room
import com.github.rexfilius.notes.data.source.local.NotesDatabase
import com.github.rexfilius.notes.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import javax.inject.Named

@ExperimentalCoroutinesApi
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

    @Provides
    @Named("test_dispatcher")
    fun provideDispatchers(): DispatcherProvider =
        object: DispatcherProvider {
            override val main: CoroutineDispatcher
                get() = TestCoroutineDispatcher()
            override val io: CoroutineDispatcher
                get() = TestCoroutineDispatcher()
            override val default: CoroutineDispatcher
                get() = TestCoroutineDispatcher()
            override val unconfined: CoroutineDispatcher
                get() = TestCoroutineDispatcher()
        }

}