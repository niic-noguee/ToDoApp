package com.example.todoapp.ui.model

data class Task(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val completed: Boolean = false
)
