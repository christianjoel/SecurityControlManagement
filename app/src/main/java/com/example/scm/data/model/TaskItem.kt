package com.example.scm.data.model

import com.example.scm.data.local.TaskEntity
import java.util.UUID

data class TaskItem(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val priority: String, // High, Medium, Normal
    val category: String, // Work, Personal, Routine
    val time: String,
    var isCompleted: Boolean = false,
    var notes: String = "",
    var photoUri: String? = null,
    var hasReminder: Boolean = false,
    var reminderTime: String = "09:00 AM"
)

fun TaskEntity.toDomain() = TaskItem(
    id = id,
    title = title,
    description = description,
    priority = priority,
    category = category,
    time = time,
    isCompleted = isCompleted,
    notes = notes,
    photoUri = photoUri,
    hasReminder = hasReminder,
    reminderTime = reminderTime
)

fun TaskItem.toEntity() = TaskEntity(
    id = id,
    title = title,
    description = description,
    priority = priority,
    category = category,
    time = time,
    isCompleted = isCompleted,
    notes = notes,
    photoUri = photoUri,
    hasReminder = hasReminder,
    reminderTime = reminderTime
)
