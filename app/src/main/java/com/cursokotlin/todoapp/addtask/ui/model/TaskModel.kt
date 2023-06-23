package com.cursokotlin.todoapp.addtask.ui.model

//System.currentTimeMillis().hashCode() nos da un int con la informacion del currentTimeMillis
data class TaskModel(val id:Int = System.currentTimeMillis().hashCode(), val task:String, var selected:Boolean = false)