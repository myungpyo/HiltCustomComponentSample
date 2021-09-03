package com.smp.hiltsample.todo.data

import com.smp.hiltsample.common.AuthUserScope
import com.smp.hiltsample.todo.domain.ToDo
import javax.inject.Inject

@AuthUserScope
class ToDoMapper @Inject constructor() {

    fun map(todo: ToDoModel): ToDo {
        return ToDo(
            id = todo.id,
            dueDate = todo.dueDate,
            contents = todo.contents
        )
    }

    fun reverse(todo: ToDo): ToDoModel {
        return ToDoModel(
            id = todo.id,
            dueDate = todo.dueDate,
            contents = todo.contents
        )
    }
}