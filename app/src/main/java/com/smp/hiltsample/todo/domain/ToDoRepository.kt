package com.smp.hiltsample.todo.domain

interface ToDoRepository {
    suspend fun getToDoList(): List<ToDo>
    suspend fun addToDo(toDo: ToDo): Long
    suspend fun deleteToDo(id: Long): Boolean
}