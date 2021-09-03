package com.smp.hiltsample.todo.data

interface ToDoDataSource {
    suspend fun getToDoList(userId: String): List<ToDoModel>
    suspend fun addToDo(userId: String, toDo: ToDoModel): Long
    suspend fun deleteToDo(userId: String, id: Long): Boolean
}