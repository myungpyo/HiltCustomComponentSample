package com.smp.hiltsample.todo.data

import com.smp.hiltsample.auth.UnAuthorizedException
import com.smp.hiltsample.auth.UserAuth
import com.smp.hiltsample.auth.UserAuthSession
import com.smp.hiltsample.todo.domain.ToDo
import com.smp.hiltsample.todo.domain.ToDoRepository
import javax.inject.Inject

class ToDoRepositoryImpl @Inject constructor(
    private val userAuthSession: UserAuthSession,
    private val inMemoryDataSource: ToDoDataSource,
    private val toDoMapper: ToDoMapper,
): ToDoRepository {

    private val userAuth = userAuthSession.currentAuth.value

    override suspend fun getToDoList(): List<ToDo> {
        val authUser = userAuth as? UserAuth.Authenticated ?: throw UnAuthorizedException()
        return inMemoryDataSource.getToDoList(authUser.id).map(toDoMapper::map)
    }

    override suspend fun addToDo(toDo: ToDo): Long {
        val authUser = userAuth as? UserAuth.Authenticated ?: throw UnAuthorizedException()
        val toDoModel = toDoMapper.reverse(toDo)
        return inMemoryDataSource.addToDo(authUser.id, toDoModel)
    }

    override suspend fun deleteToDo(id: Long): Boolean {
        val authUser = userAuth as? UserAuth.Authenticated ?: throw UnAuthorizedException()
        return inMemoryDataSource.deleteToDo(authUser.id, id)
    }
}