package com.cursokotlin.todoapp.addtask.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cursokotlin.todoapp.addtask.domain.AddTaskUseCase
import com.cursokotlin.todoapp.addtask.domain.GetTasksUseCase
import com.cursokotlin.todoapp.addtask.ui.TaskUiState.Success
import com.cursokotlin.todoapp.addtask.ui.model.TaskModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class TaskViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    getTasksUseCase: GetTasksUseCase
):ViewModel() {

    //tiene que consumir el caso de uso
    val uiState: StateFlow<TaskUiState> = getTasksUseCase().map(::Success)
        .catch { TaskUiState.Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TaskUiState.Loading)

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