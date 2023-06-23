package com.cursokotlin.todoapp.addtask.data

import com.cursokotlin.todoapp.addtask.ui.model.TaskModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

//Creamos el singlenton y necesitamos el dao para hacer consultas, añadir, eliminar etc
@Singleton
class TaskRepository @Inject constructor(private val taskDao: TaskDao){

    //Como leemos con flow, ya que un flow es una comunicacion continua
    //un .map recorre la lista y esto devuelve unos items
    //Esto hara que solo la capa de taskEntity solo lo vea el data
    val task: Flow<List<TaskModel>> = taskDao.getTask().map { items -> items.map {TaskModel(it.id, it.task, it.selected)}}

    //Recibo el modelo de la vista y creo el objeto entity para guardarlo en BBDD
    suspend fun add(taskModel: TaskModel){
        taskDao.addTask(TaskEntity(taskModel.id, taskModel.task, taskModel.selected))
    }
}