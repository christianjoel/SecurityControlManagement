package com.example.scm.utils

import android.R
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.scm.MainActivity
import com.example.scm.data.model.TaskItem
import java.text.SimpleDateFormat
import java.util.*

object NotificationUtils {
    private const val CHANNEL_ID = "task_reminder_channel"

    fun showTaskReminder(context: Context, task: TaskItem) {
        if (!task.hasReminder) return // If user not choose reminder, dont show notification

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Task Reminders",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Daily reminders for active tasks"
            }
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra("TASK_ID", task.id)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            task.id.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_popup_reminder)
            .setContentTitle("Reminder (${task.reminderTime}): ${task.title}")
            .setContentText(task.description.ifBlank { "Tap to view task details" })
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(task.id.hashCode(), notification)
    }

    fun scheduleTaskReminder(context: Context, task: TaskItem) {
        if (!task.hasReminder) {
            cancelTaskReminder(context, task.id)
            return
        }

        val calendar = parseTimeStringToCalendar(task.reminderTime) ?: return
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, TaskReminderReceiver::class.java).apply {
            putExtra("TASK_ID", task.id)
            putExtra("TASK_TITLE", task.title)
            putExtra("TASK_DESC", task.description)
            putExtra("TASK_TIME", task.reminderTime)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            task.id.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        if (calendar.timeInMillis <= System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        try {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        } catch (_: SecurityException) {
            alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        }
    }

    fun cancelTaskReminder(context: Context, taskId: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, TaskReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            taskId.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(taskId.hashCode())
    }

    private fun parseTimeStringToCalendar(timeStr: String): Calendar? {
        try {
            val formats = listOf(
                SimpleDateFormat("hh:mm a", Locale.getDefault()),
                SimpleDateFormat("H:mm", Locale.getDefault()),
                SimpleDateFormat("hh:mm", Locale.getDefault())
            )
            var date: Date? = null
            for (format in formats) {
                format.isLenient = false
                try {
                    date = format.parse(timeStr.trim())
                    if (date != null) break
                } catch (_: Exception) {}
            }
            if (date != null) {
                val targetTime = Calendar.getInstance()
                val parsedTime = Calendar.getInstance().apply { time = date }
                targetTime.set(Calendar.HOUR_OF_DAY, parsedTime.get(Calendar.HOUR_OF_DAY))
                targetTime.set(Calendar.MINUTE, parsedTime.get(Calendar.MINUTE))
                targetTime.set(Calendar.SECOND, 0)
                targetTime.set(Calendar.MILLISECOND, 0)
                return targetTime
            }
        } catch (_: Exception) {}
        return null
    }
}
