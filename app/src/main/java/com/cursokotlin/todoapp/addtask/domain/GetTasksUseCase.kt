package com.cursokotlin.todoapp.addtask.domain

import com.cursokotlin.todoapp.addtask.data.TaskRepository
import com.cursokotlin.todoapp.addtask.ui.model.TaskModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

//Nos devuelve un listado
class GetTasksUseCase @Inject constructor(private val taskRepository:TaskRepository) {
    //El Flow es algo que tiene una conexion de manera continua
    operator fun invoke():Flow<List<TaskModel>> = taskRepository.tasks
}