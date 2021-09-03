package com.smp.hiltsample.todo.data

object MockToDoData {
    val BASE_DUE_DATE = System.currentTimeMillis()
    val MOCK_TODO_DATA = mapOf(
        "test1" to mutableListOf(
            ToDoModel(
                id = 1,
                contents = "Test1's ToDo #1",
                dueDate = BASE_DUE_DATE
            ),
            ToDoModel(
                id = 2,
                contents = "Test1's ToDo #1",
                dueDate = BASE_DUE_DATE
            ),
            ToDoModel(
                id = 3,
                contents = "Test1's ToDo #1",
                dueDate = BASE_DUE_DATE
            ),
            ToDoModel(
                id = 4,
                contents = "Test1's ToDo #1",
                dueDate = BASE_DUE_DATE
            ),
            ToDoModel(
                id = 5,
                contents = "Test1's ToDo #1",
                dueDate = BASE_DUE_DATE
            ),
        ),
        "test2" to mutableListOf(
            ToDoModel(
                id = 1,
                contents = "Test2's ToDo #1",
                dueDate = BASE_DUE_DATE
            ),
            ToDoModel(
                id = 2,
                contents = "Test2's ToDo #1",
                dueDate = BASE_DUE_DATE
            ),
            ToDoModel(
                id = 3,
                contents = "Test2's ToDo #1",
                dueDate = BASE_DUE_DATE
            ),
        )
    )
}