package com.example.scm.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.scm.data.local.TaskDatabase
import com.example.scm.data.model.TaskItem
import com.example.scm.data.repository.TaskRepository
import com.example.scm.utils.NotificationUtils
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TaskRepository

    val tasks: StateFlow<List<TaskItem>>

    private val _selectedTab = MutableStateFlow(0)
    val selectedTab: StateFlow<Int> = _selectedTab

    private val _showAddDialog = MutableStateFlow(false)
    val showAddDialog: StateFlow<Boolean> = _showAddDialog

    private val _selectedTaskForDetail = MutableStateFlow<TaskItem?>(null)
    val selectedTaskForDetail: StateFlow<TaskItem?> = _selectedTaskForDetail

    private var pendingTaskId: String? = null

    init {
        val taskDao = TaskDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(taskDao)
        tasks = repository.allTasks.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        viewModelScope.launch {
            repository.allTasks.collect { taskList ->
                val targetId = pendingTaskId
                if (!targetId.isNullOrBlank()) {
                    val target = taskList.find { it.id == targetId }
                    if (target != null) {
                        _selectedTaskForDetail.value = target
                        pendingTaskId = null
                    }
                }
            }
        }
    }

    fun setTab(index: Int) {
        _selectedTab.value = index
    }

    fun setShowAddDialog(show: Boolean) {
        _showAddDialog.value = show
    }

    fun setSelectedTaskForDetail(task: TaskItem?) {
        _selectedTaskForDetail.value = task
    }

    fun openTaskById(taskId: String) {
        val currentTasks = tasks.value
        val target = currentTasks.find { it.id == taskId }
        if (target != null) {
            _selectedTaskForDetail.value = target
        } else {
            pendingTaskId = taskId
        }
    }

    fun addTask(title: String, description: String, priority: String, category: String, hasReminder: Boolean, reminderTime: String) {
        viewModelScope.launch {
            val timeStr = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
            val newTask = TaskItem(
                title = title,
                description = description,
                priority = priority,
                category = category,
                time = timeStr,
                hasReminder = hasReminder,
                reminderTime = reminderTime
            )
            repository.insertTask(newTask)
            if (hasReminder) {
                NotificationUtils.scheduleTaskReminder(getApplication(), newTask)
            }
            _showAddDialog.value = false
        }
    }

    fun updateTask(task: TaskItem) {
        viewModelScope.launch {
            repository.updateTask(task)
            if (task.hasReminder) {
                NotificationUtils.scheduleTaskReminder(getApplication(), task)
            } else {
                NotificationUtils.cancelTaskReminder(getApplication(), task.id)
            }
            _selectedTaskForDetail.value = null
        }
    }

    fun deleteTask(task: TaskItem) {
        viewModelScope.launch {
            repository.deleteTask(task)
            NotificationUtils.cancelTaskReminder(getApplication(), task.id)
        }
    }

    fun setTaskCompleted(task: TaskItem, isCompleted: Boolean) {
        viewModelScope.launch {
            repository.updateTask(task.copy(isCompleted = isCompleted))
        }
    }

    fun resetDefaultTasks() {
        viewModelScope.launch {
            repository.resetDefaultTasks()
        }
    }
}
