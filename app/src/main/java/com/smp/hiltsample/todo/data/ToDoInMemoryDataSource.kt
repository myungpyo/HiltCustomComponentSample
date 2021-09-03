package com.smp.hiltsample.todo.data

import com.smp.hiltsample.auth.UserAuthSession
import com.smp.hiltsample.common.KeyValueStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class ToDoInMemoryDataSource @Inject constructor(
    private val keyValueStore: KeyValueStore,
    private val userAuthSession: UserAuthSession
): ToDoDataSource {

    private var seq: Long = 0

    override suspend fun getToDoList(userId: String): List<ToDoModel> {
        delay(500) // emulation
        return loadToDoList(userId).toList().sortedBy { it.dueDate }
    }

    override suspend fun addToDo(userId: String, toDo: ToDoModel): Long {
        delay(200) // emulation
        val id = seq++
        loadToDoList(userId).add(toDo.copy(id = id))
        return id
    }

    override suspend fun deleteToDo(userId: String, id: Long): Boolean {
        delay(200) // emulation
        return loadToDoList(userId).removeAll { it.id == id }
    }

    private suspend fun loadToDoList(userId: String): MutableList<ToDoModel> {
        return keyValueStore.getOrPutIfAbsent(
            key = userId,
            value = mutableListOf()
        )
    }
}