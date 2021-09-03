package com.smp.hiltsample.todo.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smp.hiltsample.common.Bridged
import com.smp.hiltsample.common.UiData
import com.smp.hiltsample.todo.domain.ToDo
import com.smp.hiltsample.todo.domain.ToDoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoViewModel @Inject constructor(
    @Bridged
    private val toDoRepository: ToDoRepository
) : ViewModel() {

    private val _toDoList = MutableStateFlow<UiData<List<ToDo>>>(UiData.Success(emptyList()))
    val toDoList = _toDoList.asStateFlow()

    fun loadToDoList() = viewModelScope.launch {
        runCatching {
            Log.d("SMP", "loadToDoList")
            _toDoList.tryEmit(UiData.Loading)
            toDoRepository.getToDoList()
        }.onSuccess { toDoList ->
            Log.d("SMP", "loadToDoList - SUCCESS")
            _toDoList.tryEmit(UiData.Success(toDoList))
        }.onFailure { throwable ->
            Log.d("SMP", "loadToDoList - FAIL : $throwable")
            _toDoList.tryEmit(UiData.Fail(throwable))
        }
    }

    fun addToDo(contents: String) = viewModelScope.launch {
        runCatching {
            toDoRepository.addToDo(ToDo(-1, contents, System.currentTimeMillis()))
        }.onSuccess {
            loadToDoList()
        }.onFailure { throwable ->
            Log.d("SMP", "addToDo - FAIL : $throwable")
        }
    }

    fun deleteToDo(id: Long) = viewModelScope.launch {
        runCatching {
            toDoRepository.deleteToDo(id)
        }.onSuccess {
            loadToDoList()
        }.onFailure { throwable ->
            Log.d("SMP", "deleteToDo - FAIL : $throwable")
        }
    }

}