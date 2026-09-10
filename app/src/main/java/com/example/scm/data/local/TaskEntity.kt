package com.example.scm.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val priority: String, // High, Medium, Normal
    val category: String, // Work, Personal, Routine
    val time: String,
    val isCompleted: Boolean = false,
    val notes: String = "",
    val photoUri: String? = null,
    val hasReminder: Boolean = false,
    val reminderTime: String = "09:00 AM"
)
