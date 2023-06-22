package com.cursokotlin.todoapp.addtask.ui

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cursokotlin.todoapp.addtask.ui.model.TaskModel
import kotlinx.coroutines.selects.select
import javax.inject.Inject

class TaskViewModel @Inject constructor():ViewModel() {


    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog:LiveData<Boolean> = _showDialog

    //MutableStateList permite que el listado sea mas facil de tratar y trabajar con el
    //Y con esto se permite actulizar la lista del tiron y es mas facil
    private val _tasks = mutableStateListOf<TaskModel>()
    val task:List<TaskModel> = _tasks

    fun onDialogClose() {
        _showDialog.value = false
    }

    fun onTaskCreated(task: String) {
        _showDialog.value = false
        _tasks.add(TaskModel(task = task))
    }

    fun onShowDialogClick() {
        _showDialog.value = true
    }

    fun onCheckBoxSelected(taskModel: TaskModel) {
        val index = _tasks.indexOf(taskModel)
        _tasks[index] = _tasks[index].let{
            //coge el objeto copialo, pero con la diferencia que selected pasa al estado contrario
            it.copy(selected = !it.selected)
        }
    }

    fun onItemRemove(taskModel: TaskModel) {
        val task = _tasks.find { it.id == taskModel.id }
    }
}