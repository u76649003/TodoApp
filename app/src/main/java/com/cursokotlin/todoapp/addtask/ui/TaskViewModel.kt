package com.cursokotlin.todoapp.addtask.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cursokotlin.todoapp.addtask.domain.AddTaskUseCase
import com.cursokotlin.todoapp.addtask.domain.GetTasksUseCase
import com.cursokotlin.todoapp.addtask.ui.TaskUiState.Success
import com.cursokotlin.todoapp.addtask.ui.TaskUiState.Error
import com.cursokotlin.todoapp.addtask.ui.TaskUiState.Loading
import com.cursokotlin.todoapp.addtask.ui.model.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    getTasksUseCase: GetTasksUseCase
):ViewModel() {

    //tiene que consumir el caso de uso
    // el tiempo es por si ha pasado un tiempo el flow se para
    // y ahora la pantalla cuando vuelva te indica, tengo un estado de succes, muestro un listado,
    //Tengo estado Loading muestro un progress bar cargando
    //Tengo un estado de Error, muestro el error
    val uiState: StateFlow<TaskUiState> = getTasksUseCase().map(::Success)
        .catch { Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog:LiveData<Boolean> = _showDialog

    //MutableStateList permite que el listado sea mas facil de tratar y trabajar con el
    //Y con esto se permite actulizar la lista del tiron y es mas facil
    //private val _tasks = mutableStateListOf<TaskModel>()
   // val task:List<TaskModel> = _tasks

    fun onDialogClose() {
        _showDialog.value = false
    }

    fun onTaskCreated(task: String) {
        _showDialog.value = false
        //corrutina
        viewModelScope.launch {
            addTaskUseCase(TaskModel(task = task))
        }
    }

    fun onShowDialogClick() {
        _showDialog.value = true
    }

    fun onCheckBoxSelected(taskModel: TaskModel) {
        //Actualizar check
        //val index = _tasks.indexOf(taskModel)
        //_tasks[index] = _tasks[index].let{
            //coge el objeto copialo, pero con la diferencia que selected pasa al estado contrario
        //    it.copy(selected = !it.selected)
      //  }
    }

    fun onItemRemove(taskModel: TaskModel) {
        // borrar item
       // val task = _tasks.find { it.id == taskModel.id }
    }
}