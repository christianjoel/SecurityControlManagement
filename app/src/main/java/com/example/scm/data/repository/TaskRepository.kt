package com.example.scm.data.repository

import com.example.scm.data.local.TaskDao
import com.example.scm.data.local.TaskEntity
import com.example.scm.data.model.TaskItem
import com.example.scm.data.model.toDomain
import com.example.scm.data.model.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepository(private val taskDao: TaskDao) {
    val allTasks: Flow<List<TaskItem>> = taskDao.getAllTasks().map { entities ->
        entities.map { it.toDomain() }
    }

    suspend fun insertTask(task: TaskItem) {
        taskDao.insertTask(task.toEntity())
    }

    suspend fun updateTask(task: TaskItem) {
        taskDao.updateTask(task.toEntity())
    }

    suspend fun deleteTask(task: TaskItem) {
        taskDao.deleteTask(task.toEntity())
    }

    suspend fun resetDefaultTasks() {
        taskDao.deleteAllTasks()
        val initialTasks = listOf(
            TaskEntity(title = "Grocery & Supplies", description = "Pick up weekly grocery essentials", priority = "Normal", category = "Personal", time = "07:30 AM"),
            TaskEntity(title = "Daily Team Standup", description = "Sync on daily blockers and sprint progress", priority = "Medium", category = "Routine", time = "02:00 PM")
        )
        taskDao.insertTasks(initialTasks)
    }
}
