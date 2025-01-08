package com.example.projobliveapp.Screens.Employer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.navigation.NavHostController
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.Image

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*

import androidx.compose.material3.*

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployerDashboardScreen() {
    val jobList = listOf(
        Job(id = "1", title = "Software Developer", status = "Open", postedDate = "2025-01-08"),
        Job(id = "2", title = "Data Scientist", status = "Closed", postedDate = "2025-01-05"),
        Job(id = "3", title = "UX Designer", status = "Open", postedDate = "2025-01-03")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Employer Dashboard") },
                actions = {
                    IconButton(onClick = { /* Navigate to Profile */ }) {
                        Icon(Icons.Default.Person, contentDescription = "Profile")
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Button(
                    onClick = { /* Navigate to Post Job Screen */ },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                ) {
                    Text("Post New Job")
                }
                Button(
                    onClick = {  },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                ) {
                    Text("Manage Existing Jobs")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text("Job Listings", style = MaterialTheme.typography.headlineSmall)
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(jobList) { job ->
                        JobItem(job = job)
                    }
                }
            }
        }
    )
}

@Composable
fun JobItem(job: Job) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .clickable(onClick = { }),

    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = job.title, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Status: ${job.status}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Posted on: ${job.postedDate}")
        }
    }
}

data class Job(
    val id: String,
    val title: String,
    val status: String,
    val postedDate: String
)
