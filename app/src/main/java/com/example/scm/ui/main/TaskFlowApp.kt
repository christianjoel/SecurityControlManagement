package com.example.scm.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.scm.ui.history.HistoryScreen
import com.example.scm.ui.profile.ProfileScreen
import com.example.scm.ui.tasks.AddTaskDialog
import com.example.scm.ui.tasks.TaskDetailDialog
import com.example.scm.ui.tasks.TaskListScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskFlowApp(viewModel: MainViewModel = viewModel()) {
    val selectedTab by viewModel.selectedTab.collectAsState()
    val tasks by viewModel.tasks.collectAsState()
    val showAddDialog by viewModel.showAddDialog.collectAsState()
    val selectedTaskForDetail by viewModel.selectedTaskForDetail.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("My Personal App", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        Text("Personal Task & Activity Manager", fontSize = 12.sp, color = Color.Gray)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        },
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.CheckCircle, contentDescription = "Tasks") },
                    label = { Text("Tasks") },
                    selected = selectedTab == 0,
                    onClick = { viewModel.setTab(0) }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.List, contentDescription = "History") },
                    label = { Text("History") },
                    selected = selectedTab == 1,
                    onClick = { viewModel.setTab(1) }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text("Profile") },
                    selected = selectedTab == 2,
                    onClick = { viewModel.setTab(2) }
                )
            }
        },
        floatingActionButton = {
            if (selectedTab == 0) {
                FloatingActionButton(
                    onClick = { viewModel.setShowAddDialog(true) },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Task")
                }
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (selectedTab) {
                0 -> TaskListScreen(
                    tasks = tasks,
                    onTaskChecked = { task, checked ->
                        viewModel.setTaskCompleted(task, checked)
                    },
                    onTaskDelete = { task ->
                        viewModel.deleteTask(task)
                    },
                    onTaskClick = { task ->
                        viewModel.setSelectedTaskForDetail(task)
                    }
                )
                1 -> HistoryScreen(
                    tasks = tasks,
                    onTaskDelete = { task ->
                        viewModel.deleteTask(task)
                    }
                )
                2 -> ProfileScreen(onResetTasks = { viewModel.resetDefaultTasks() })
            }
        }

        if (showAddDialog) {
            AddTaskDialog(
                onDismiss = { viewModel.setShowAddDialog(false) },
                onTaskCreated = { title, desc, priority, category, hasReminder, reminderTime ->
                    viewModel.addTask(title, desc, priority, category, hasReminder, reminderTime)
                }
            )
        }

        val currentTaskForDetail = selectedTaskForDetail
        if (currentTaskForDetail != null) {
            TaskDetailDialog(
                task = currentTaskForDetail,
                onDismiss = { viewModel.setSelectedTaskForDetail(null) },
                onSave = { updatedTask ->
                    viewModel.updateTask(updatedTask)
                }
            )
        }
    }
}
