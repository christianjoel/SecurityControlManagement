package com.example.scm.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.scm.data.model.TaskItem

class TaskReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val id = intent.getStringExtra("TASK_ID") ?: return
        val title = intent.getStringExtra("TASK_TITLE") ?: "Task"
        val desc = intent.getStringExtra("TASK_DESC") ?: ""
        val time = intent.getStringExtra("TASK_TIME") ?: "09:00 AM"

        val task = TaskItem(
            id = id,
            title = title,
            description = desc,
            priority = "Normal",
            category = "Routine",
            time = time,
            hasReminder = true,
            reminderTime = time
        )
        NotificationUtils.showTaskReminder(context, task)
    }
}
