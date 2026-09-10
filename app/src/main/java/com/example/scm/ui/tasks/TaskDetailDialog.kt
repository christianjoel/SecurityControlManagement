package com.example.scm.ui.tasks

import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import com.example.scm.data.model.TaskItem
import com.example.scm.utils.ImageUtils
import com.example.scm.utils.NotificationUtils
import java.io.File
import java.io.FileOutputStream
import java.util.*

@Composable
fun TaskDetailDialog(
    task: TaskItem,
    onDismiss: () -> Unit,
    onSave: (TaskItem) -> Unit
) {
    val context = LocalContext.current
    var notes by remember { mutableStateOf(task.notes) }
    var isCompleted by remember { mutableStateOf(task.isCompleted) }
    var photoUri by remember { mutableStateOf(task.photoUri) }
    var hasReminder by remember { mutableStateOf(task.hasReminder) }
    var reminderTime by remember { mutableStateOf(task.reminderTime.ifBlank { "09:00 AM" }) }
    var showZoomDialog by remember { mutableStateOf(false) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { sourceUri ->
            try {
                val inputStream = context.contentResolver.openInputStream(sourceUri)
                val file = File(context.filesDir, "task_img_${System.currentTimeMillis()}.jpg")
                val outputStream = FileOutputStream(file)
                inputStream?.copyTo(outputStream)
                inputStream?.close()
                outputStream.close()
                photoUri = file.absolutePath
            } catch (_: Exception) {
                photoUri = sourceUri.toString()
            }
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        bitmap?.let {
            try {
                val file = File(context.filesDir, "task_cam_${System.currentTimeMillis()}.jpg")
                val outputStream = FileOutputStream(file)
                it.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
                outputStream.close()
                photoUri = file.absolutePath
            } catch (_: Exception) {}
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column {
                Text(text = task.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "${task.category} • ${task.time}", fontSize = 12.sp, color = Color.Gray)
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (task.description.isNotBlank()) {
                    Text(text = task.description, fontSize = 14.sp, color = Color.DarkGray)
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked = isCompleted,
                        onCheckedChange = { isCompleted = it }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = if (isCompleted) "Completed" else "Mark as Completed", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Switch(
                        checked = hasReminder,
                        onCheckedChange = { hasReminder = it }
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = "Enable Daily Reminder", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                }

                if (hasReminder) {
                    OutlinedButton(
                        onClick = {
                            val cal = Calendar.getInstance()
                            TimePickerDialog(
                                context,
                                { _, hourOfDay, minute ->
                                    val formatted = String.format(Locale.getDefault(), "%02d:%02d %s", if (hourOfDay % 12 == 0) 12 else hourOfDay % 12, minute, if (hourOfDay >= 12) "PM" else "AM")
                                    reminderTime = formatted
                                },
                                cal.get(Calendar.HOUR_OF_DAY),
                                cal.get(Calendar.MINUTE),
                                false
                            ).show()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("⏰ Reminder Time: $reminderTime (Tap to Change)")
                    }
                }

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Task Notes / Details") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )

                Text("Attached Photo Copy / Receipt", fontWeight = FontWeight.Bold, fontSize = 14.sp)

                val uriStr = photoUri
                if (!uriStr.isNullOrBlank()) {
                    val thumbnailBitmap = remember(uriStr) { ImageUtils.loadBitmap(context, uriStr) }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.LightGray.copy(alpha = 0.3f))
                            .clickable { showZoomDialog = true }
                    ) {
                        if (thumbnailBitmap != null) {
                            Image(
                                bitmap = thumbnailBitmap.asImageBitmap(),
                                contentDescription = "Photo Thumbnail",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text("Preview unavailable", fontSize = 12.sp, color = Color.Gray)
                            }
                        }
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(8.dp)
                                .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(4.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text("Tap to Zoom", color = Color.White, fontSize = 10.sp)
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { cameraLauncher.launch(null) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(Icons.Default.Create, contentDescription = "Camera", modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Camera", fontSize = 12.sp)
                    }

                    Button(
                        onClick = { galleryLauncher.launch("image/*") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Gallery", modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Gallery", fontSize = 12.sp)
                    }
                }

                OutlinedButton(
                    onClick = {
                        val shareText = buildString {
                            append("📋 Task: ${task.title}\n")
                            if (task.description.isNotBlank()) append("Description: ${task.description}\n")
                            append("Priority: ${task.priority}\n")
                            append("Category: ${task.category}\n")
                            append("Time: ${task.time}\n")
                            if (hasReminder) append("⏰ Reminder: $reminderTime\n")
                            append("Status: ${if (isCompleted) "Completed" else "Pending"}\n")
                            if (notes.isNotBlank()) append("📝 Notes: $notes\n")
                        }

                        val currentPhotoUri = photoUri
                        val intent = if (!currentPhotoUri.isNullOrBlank() && File(currentPhotoUri).exists()) {
                            val file = File(currentPhotoUri)
                            val imageUri = FileProvider.getUriForFile(
                                context,
                                "${context.packageName}.fileprovider",
                                file
                            )
                            Intent(Intent.ACTION_SEND).apply {
                                type = "image/*"
                                putExtra(Intent.EXTRA_STREAM, imageUri)
                                putExtra(Intent.EXTRA_TEXT, shareText)
                                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            }
                        } else {
                            Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, shareText)
                            }
                        }
                        context.startActivity(Intent.createChooser(intent, "Share Task Details"))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Default.Share, contentDescription = "Share", modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Share Task Details & Image")
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val updated = task.copy(
                        notes = notes,
                        isCompleted = isCompleted,
                        photoUri = photoUri,
                        hasReminder = hasReminder,
                        reminderTime = reminderTime
                    )
                    if (hasReminder) {
                        NotificationUtils.scheduleTaskReminder(context, updated)
                    }
                    onSave(updated)
                }
            ) {
                Text("Save Changes")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )

    val currentUri = photoUri
    if (showZoomDialog && !currentUri.isNullOrBlank()) {
        FullScreenZoomableImageViewer(
            photoUri = currentUri,
            onDismiss = { showZoomDialog = false }
        )
    }
}
