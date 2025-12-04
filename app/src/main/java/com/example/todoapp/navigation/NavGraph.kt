package com.example.todoapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.todoapp.ui.login.LoginScreen
import com.example.todoapp.ui.register.RegisterScreen
import com.example.todoapp.ui.home.HomeScreen
import com.example.todoapp.ui.addtask.AddTaskScreen
import com.example.todoapp.ui.edittask.EditTaskScreen

sealed class Screens(val route: String) {
    object Login : Screens("login")
    object Register : Screens("register")
    object Home : Screens("home")
    object AddTask : Screens("add_task")
    object EditTask : Screens("edit_task/{taskId}") {
        fun routeWithId(id: String) = "edit_task/$id"
    }
}

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.Login.route
    ) {

        composable(Screens.Login.route) {
            LoginScreen(navController)
        }

        composable(Screens.Register.route) {
            RegisterScreen(navController)
        }

        composable(Screens.Home.route) {
            HomeScreen(navController)
        }

        composable(Screens.AddTask.route) {
            AddTaskScreen(navController)
        }

        composable(Screens.EditTask.route) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId") ?: ""
            EditTaskScreen(navController, taskId)
        }
    }
}
