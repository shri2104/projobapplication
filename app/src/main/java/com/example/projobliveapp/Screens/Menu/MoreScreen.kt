package com.example.projobliveapp.Screens.Menu

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Lock
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MorePage(navController: NavHostController) {
    val appBarColor = Color.White

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("More") },
                navigationIcon = {
                    IconButton(onClick = {navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Menu")
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
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(Modifier.height(30.dp))
                SafetyTermsAndPrivacy()
            }
        }
    )
}

@Composable
fun SafetyTermsAndPrivacy() {
    val context = LocalContext.current

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.Security, contentDescription = "Safety Tips Icon", modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(16.dp))
            ClickableText(
                text = AnnotatedString("Safety Tips"),
                onClick = {
                    Toast.makeText(context, "Clicked Safety Tips", Toast.LENGTH_SHORT).show()
                },
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
            )
        }
        Spacer(Modifier.height(5.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.Description, contentDescription = "Terms and Conditions Icon", modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(16.dp))
            ClickableText(
                text = AnnotatedString("Terms and Conditions"),
                onClick = {
                    Toast.makeText(context, "Terms and Conditions", Toast.LENGTH_SHORT).show()
                },
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
            )
        }

       Spacer(Modifier.height(5.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.Lock, contentDescription = "Privacy Policy", modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(16.dp))
            ClickableText(
                text = AnnotatedString("Privacy Policy"),
                onClick = {
                    Toast.makeText(context, "Clicked Privacy Policy", Toast.LENGTH_SHORT).show()
                },
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
            )
        }
        Spacer(Modifier.height(5.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.Info, contentDescription = "Privacy Policy Icon", modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(16.dp))
            ClickableText(
                text = AnnotatedString("About"),
                onClick = {
                    Toast.makeText(context, "About", Toast.LENGTH_SHORT).show()
                },
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
            )
        }
    }
}

