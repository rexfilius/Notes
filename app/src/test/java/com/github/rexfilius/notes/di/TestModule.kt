package com.github.rexfilius.notes.di

import com.github.rexfilius.notes.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import javax.inject.Named

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
object TestModule {

    @Provides
    @Named("tests_dispatcher")
    fun provideDispatchers(): DispatcherProvider =
        object : DispatcherProvider {
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