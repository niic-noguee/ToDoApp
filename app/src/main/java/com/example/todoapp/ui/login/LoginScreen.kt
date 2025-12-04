package com.example.todoapp.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun LoginScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Tela de Login")
        Button(onClick = {
            navController.navigate("home")
        }) {
            Text("Entrar (temporário)")
        }
    }
}
