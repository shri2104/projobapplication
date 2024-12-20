package com.example.projobliveapp.Screens.Menu

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack


import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.text.BasicText
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.projobliveapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpAndSupportPage(navController: NavController) {
   
    val appBarColor = Color.White

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Help and Support") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back Button")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = appBarColor,
                    titleContentColor = Color.Black
                )
            )
        },

        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(1.dp)
                    .background(appBarColor),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Need help? Reach us via email:",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )

                // Email clickable text
                ClickableEmail(
                    email = "support@projob.co.in"
                )
            }
        }
    )
}

@Composable
fun ClickableEmail(email: String) {
    val context = LocalContext.current
    Text(
        text = email,
        style = TextStyle(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary),
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:$email")
                }
                context.startActivity(intent)
            }
    )
}

@Preview(showBackground = true)
@Composable
fun HelpAndSupportPagePreview() {
    HelpAndSupportPage(navController = NavController(LocalContext.current))
}