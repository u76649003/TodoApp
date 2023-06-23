package com.cursokotlin.todoapp.addtask.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.cursokotlin.todoapp.addtask.ui.model.TaskModel

//Pantalla Principal
@Composable
fun TaskScreen(taskViewModel: TaskViewModel) {
    val showDialog: Boolean by taskViewModel.showDialog.observeAsState(initial = false)
    //Creamos el ciclo de vida para el state flow
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    //Nos creamos un estado, creandonos un estado permanente de la app
    val uiState by produceState<TaskUiState>(
        initialValue = TaskUiState.Loading,
        key1 = lifecycle,
        key2 = taskViewModel
    ) {
        //mientra este empezado, siempre este consumiendo datos
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED){
            //Aqui coletear los flows
            //Hay que hacer un collect para recuperar los valores
            taskViewModel.uiState.collect{
                //value es el uiState, se este actualizando constantemente
                value = it
            }
        }
    }

    when(uiState){
        is TaskUiState.Error -> {}
        TaskUiState.Loading -> {}
        is TaskUiState.Success -> {
            Box(modifier = Modifier.fillMaxSize()) {
                AddTasksDialog(
                    show = showDialog,
                    onDismiss = { taskViewModel.onDialogClose() },
                    onTaskAdded = { taskViewModel.onTaskCreated(it) })
                FabDialog(
                    Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp), taskViewModel
                )
                TaskList((uiState as TaskUiState.Success).tasks, taskViewModel)
            }
        }
    }




}

@Composable
fun TaskList(tasks: List<TaskModel>, taskViewModel: TaskViewModel) {
    //se va ir llamando cada vez que modifiquemos la lista
    // val myTasks: List<TaskModel> = taskViewModel.task no hace falta el del viewModel, siempre los valores de la BBDD
    LazyColumn {
        //el parametro key ayuda a optimizar el recyclerView y o los LazyColumn
        items(tasks, key = { it.id }) { task ->
            ItemTask(taskModel = task, taskViewModel = taskViewModel)
        }
    }
}

@Composable
fun ItemTask(taskModel: TaskModel, taskViewModel: TaskViewModel) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .pointerInput(Unit) {
                //Aqui dentro controlamos distintos tipos de click
                // Un metodo que controla el doble click, el pulsado,etc
                //onDoubleTap doble click, onLongPress pulsado, onPress pulsar
                detectTapGestures(onLongPress = {
                    taskViewModel.onItemRemove(taskModel)
                })
            },
        elevation = 8.dp
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = taskModel.task, modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp)
            )
            Checkbox(
                checked = taskModel.selected,
                onCheckedChange = { taskViewModel.onCheckBoxSelected(taskModel) })
        }
    }

}

@Composable
fun FabDialog(modifier: Modifier, taskViewModel: TaskViewModel) {
    //Boton de abajo a la derecha
    FloatingActionButton(onClick = { //dialogo
        taskViewModel.onShowDialogClick()
    }, modifier = modifier) {
        Icon(Icons.Filled.Add, contentDescription = "")
    }
}

@Composable
fun AddTasksDialog(show: Boolean, onDismiss: () -> Unit, onTaskAdded: (String) -> Unit) {
    var myTask by remember { mutableStateOf("") }
    if (show) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Text(
                    "Añade tu tarea",
                    fontSize = 18.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(16.dp))
                TextField(value = myTask, onValueChange = { myTask = it })
                Spacer(modifier = Modifier.size(16.dp))
                Button(onClick = {
                    //Mandar tarea
                    onTaskAdded(myTask)
                    //Limpiar caja de texto
                    myTask = ""
                }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Añadir tarea")
                }
            }
        }
    }

}
