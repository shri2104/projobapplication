package com.example.projobliveapp.Screens.Employer

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projobliveapp.DataBase.ApiService
import com.example.projobliveapp.DataBase.Notification
import com.example.projobliveapp.DataBase.NotificationResponse
import com.google.gson.Gson
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployerNotificationsScreen(apiService: ApiService, employerId: String) {
    var notifications by remember { mutableStateOf<List<Notification>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(employerId) {
        isLoading = true
        try {
            val response = apiService.getNotifications(employerId)
            if (response.isSuccessful) {
                val notificationResponse = response.body()
                if (notificationResponse != null && notificationResponse.success) {
                    notifications = notificationResponse.notifications
                }
            }
        } catch (e: Exception) {
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Notifications") })
        }
    ) { padding ->
        if (isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (notifications.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No new notifications")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(notifications) { notification ->
                    NotificationItem(notification, apiService, onRemove = { removedNotification ->
                        notifications = notifications.filter { it != removedNotification }
                    })
                }
            }
        }
    }
}

@Composable
fun NotificationItem(notification: Notification, apiService: ApiService, onRemove: (Notification) -> Unit) {
    val coroutineScope = rememberCoroutineScope()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(notification.message, fontWeight = FontWeight.Bold)
                Text(
                    text = formatTimestamp(notification.createdAt),
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            IconButton(
                onClick = {
                    notification.id?.let { validId ->
                        coroutineScope.launch {
                            try {
                                val response = apiService.deleteNotification(validId)
                                if (response.isSuccessful) {
                                    val body = response.body()
                                    if (body?.success == true) {
                                        onRemove(notification)
                                    }
                                }
                            } catch (e: Exception) {
                            }
                        }
                    }
                },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Remove Notification",
                    tint = Color.Red
                )
            }
        }
    }
}