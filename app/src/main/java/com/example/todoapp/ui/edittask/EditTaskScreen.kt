package com.example.todoapp.ui.edittask

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todoapp.ui.home.HomeViewModel
import com.example.todoapp.ui.model.Task

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskScreen(
    navController: NavController,
    taskId: String,
    viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    // Carregar a tarefa com base no ID
    val tasks by viewModel.tasks.collectAsState()
    val currentTask = tasks.find { it.id == taskId }

    // Estados locais para edição
    var title by remember { mutableStateOf(currentTask?.title ?: "") }
    var description by remember { mutableStateOf(currentTask?.description ?: "") }

    Scaffold(
        topBar = {
            LargeTopAppBar(title = { Text("Editar Tarefa") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descrição") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (currentTask != null) {
                        val updatedTask = currentTask.copy(
                            title = title,
                            description = description
                        )
                        viewModel.updateTask(updatedTask)
                    }
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvar alterações")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.deleteTask(taskId)
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Excluir tarefa")
            }
        }
    }
}
