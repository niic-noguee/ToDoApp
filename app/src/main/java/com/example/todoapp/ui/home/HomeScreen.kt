@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.todoapp.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todoapp.ui.model.Task
import com.example.todoapp.navigation.Screens

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val tasks by viewModel.tasks.collectAsState()

    val pending = tasks.filter { !it.completed }
    val done = tasks.filter { it.completed }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Minhas Tarefas") },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screens.Profile.route)
                    }) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Perfil"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screens.AddTask.route)
            }) {
                Text("+")
            }
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(12.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            item {
                Text("Pendentes", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
            }

            items(pending) { task ->
                TaskItem(
                    task = task,
                    onToggle = { checked ->
                        viewModel.updateTask(task.copy(completed = checked))
                    },
                    onClick = {
                        navController.navigate(Screens.EditTask.routeWithId(task.id))
                    }
                )
            }

            if (done.isNotEmpty()) {
                item {
                    Spacer(Modifier.height(24.dp))
                    Text("Concluídas", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                }

                items(done) { task ->
                    TaskItem(
                        task = task,
                        onToggle = { checked ->
                            viewModel.updateTask(task.copy(completed = checked))
                        },
                        onClick = {
                            navController.navigate(Screens.EditTask.routeWithId(task.id))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    onToggle: (Boolean) -> Unit,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(task.title, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(task.description, style = MaterialTheme.typography.bodyMedium)
            }

            Checkbox(
                checked = task.completed,
                onCheckedChange = onToggle
            )
        }
    }
}
