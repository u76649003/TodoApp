package com.cursokotlin.todoapp.addtask.ui

import com.cursokotlin.todoapp.addtask.ui.model.TaskModel

sealed interface TaskUiState {
    object Loading:TaskUiState
    data class Error(val throwable: Throwable):TaskUiState
    data class Success(val tasks:List<TaskModel>):TaskUiState
}