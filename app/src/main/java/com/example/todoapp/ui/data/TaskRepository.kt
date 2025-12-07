package com.example.todoapp.ui.data

import com.example.todoapp.ui.model.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class TaskRepository {
    private val db = FirebaseFirestore.getInstance()
    private val auth = Firebase.auth

    private val tasksCollection
        get() = db.collection("tasks")
            .document(auth.currentUser!!.uid)
            .collection("userTasks")

    fun observeTasks(onTasksChanged: (List<Task>) -> Unit) {
        tasksCollection.addSnapshotListener { snapshot, e ->
            if (e != null) {
                onTasksChanged(emptyList())
                return@addSnapshotListener
            }

            val tasks = snapshot?.documents?.map { doc ->
                Task(
                    id = doc.id,
                    title = doc.getString("title") ?: "",
                    description = doc.getString("description") ?: "",
                    completed = doc.getBoolean("completed") ?: false
                )
            } ?: emptyList()

            onTasksChanged(tasks)
        }
    }

    suspend fun addTask(task: Task) {
        tasksCollection.document(task.id).set(task).await()
    }

    suspend fun updateTask(task: Task) {
        tasksCollection.document(task.id).set(task).await()
    }

    suspend fun deleteTask(taskId: String) {
        tasksCollection.document(taskId).delete().await()
    }
}
