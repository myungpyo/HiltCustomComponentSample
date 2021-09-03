package com.smp.hiltsample.todo.domain

data class ToDo(
    val id: Long,
    val contents: String,
    val dueDate: Long
)