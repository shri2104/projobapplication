package com.example.projobliveapp.connection

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun NetworkObserver(context: Context) {
    val coroutineScope = rememberCoroutineScope()
    val isConnected = remember { mutableStateOf(true) }

    DisposableEffect(context) {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                NetworkUtils.checkNetworkAvailability(context!!)
                coroutineScope.launch {
                    NetworkUtils.isConnected.collectLatest { status ->
                        isConnected.value = status
                    }
                }
            }
        }
        val intentFilter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
        context.registerReceiver(receiver, intentFilter)

        onDispose {
            context.unregisterReceiver(receiver)
        }
    }

    if (!isConnected.value) {
        AlertDialog(
            onDismissRequest = {  },
            title = { Text("No Internet Connection") },
            text = { Text("Please check your internet connection. The app will not work without an active connection.") },
            confirmButton = {
                Button(onClick = { coroutineScope.launch {
                    NetworkUtils.checkNetworkAvailability(context)
                } }) {
                    Text("Retry")
                }
            }
        )
    }
}
