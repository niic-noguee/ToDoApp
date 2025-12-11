@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.todoapp.ui.edittask

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todoapp.ui.home.HomeViewModel

@Composable
fun EditTaskScreen(
    navController: NavController,
    taskId: String,
    viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val tasks by viewModel.tasks.collectAsState()
    val task = tasks.find { it.id == taskId }

    var title by remember { mutableStateOf(task?.title ?: "") }
    var description by remember { mutableStateOf(task?.description ?: "") }

    // Estado para mostrar ou não o diálogo
    var showDeleteDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar Tarefa") },

                navigationIcon = {
                    IconButton(onClick = { navController.navigate("home") }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                },

                actions = {
                    IconButton(
                        onClick = { showDeleteDialog = true } // Exibir diálogo
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Deletar Tarefa",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descrição") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    if (task != null) {
                        viewModel.updateTask(
                            task.copy(
                                title = title,
                                description = description
                            )
                        )
                    }
                    navController.navigate("home")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvar Alterações")
            }
        }
    }

    // ➤ DIÁLOGO DE CONFIRMAÇÃO
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },

            title = { Text("Excluir tarefa?") },
            text = { Text("Tem certeza de que deseja excluir esta tarefa? Essa ação não pode ser desfeita.") },

            confirmButton = {
                TextButton(
                    onClick = {
                        if (task != null) {
                            viewModel.deleteTask(task.id)
                        }
                        showDeleteDialog = false
                        navController.navigate("home")
                    }
                ) {
                    Text("Deletar", color = MaterialTheme.colorScheme.error)
                }
            },

            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}
