package com.example.todoapp.ui.edittask

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun EditTaskScreen(navController: NavController, taskId: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Editar Tarefa ID: $taskId")
    }
}
