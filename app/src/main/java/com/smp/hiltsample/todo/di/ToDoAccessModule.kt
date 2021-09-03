package com.smp.hiltsample.todo.di

import com.smp.hiltsample.common.AuthUserComponent
import com.smp.hiltsample.common.AuthUserComponentManager
import com.smp.hiltsample.common.Bridged
import com.smp.hiltsample.todo.domain.ToDoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(AuthUserComponent::class)
@EntryPoint
internal interface ToDoEntryPoint {
    fun todoRepository(): ToDoRepository
}

@Module
@InstallIn(SingletonComponent::class)
internal object ToDoEntryBridge {
    @Bridged
    @Provides
    internal fun provideToDoRepository(
        authUserComponentManager: AuthUserComponentManager
    ): ToDoRepository {
        return EntryPoints
            .get(authUserComponentManager, ToDoEntryPoint::class.java)
            .todoRepository()
    }
}

