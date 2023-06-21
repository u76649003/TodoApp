package com.cursokotlin.todoapp.addtask.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

//Pantalla Principal
@Composable
fun TaskScreen(taskViewModel: TaskViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        AddTasksDialog(show=true, onDismiss = {}, onTaskAdded = {})
        FabDialog(
            Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)

        )
    }
}

@Composable
fun FabDialog(modifier: Modifier) {
    //Boton de abajo a la derecha
    FloatingActionButton(onClick = { //dialogo

    }, modifier = modifier) {
        Icon(Icons.Filled.Add, contentDescription = "")
    }
}

@Composable
fun AddTasksDialog(show: Boolean, onDismiss: () -> Unit, onTaskAdded:(String) -> Unit) {
    var myTask by remember { mutableStateOf("") }
    if (show) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)) {
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
                }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Añadir tarea")
                }
            }
        }
    }

}
