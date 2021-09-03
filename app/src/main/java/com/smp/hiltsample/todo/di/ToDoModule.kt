package com.smp.hiltsample.todo.di

import com.smp.hiltsample.common.AuthUserComponent
import com.smp.hiltsample.common.AuthUserScope
import com.smp.hiltsample.todo.data.ToDoDataSource
import com.smp.hiltsample.todo.data.ToDoInMemoryDataSource
import com.smp.hiltsample.todo.data.ToDoRepositoryImpl
import com.smp.hiltsample.todo.domain.ToDoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn

@Module
@InstallIn(AuthUserComponent::class)
abstract class ToDoModule {

    @Binds
    internal abstract fun bindToDoInMemoryDataSource(
        toDoInMemoryDataSource: ToDoInMemoryDataSource
    ): ToDoDataSource

    @AuthUserScope
    @Binds
    internal abstract fun bindToDoRepository(
        toDoRepositoryImpl: ToDoRepositoryImpl
    ): ToDoRepository
}