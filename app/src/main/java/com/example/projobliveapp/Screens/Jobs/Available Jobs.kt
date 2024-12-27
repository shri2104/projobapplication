package com.example.projobliveapp.Screens.Jobs

import android.widget.Toast
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.projobliveapp.DataBase.ApiService
import com.example.projobliveapp.DataBase.Job
import com.example.projobliveapp.R

@Composable
fun JobList(apiService: ApiService, navController: NavHostController) {
    val jobList = remember { mutableStateListOf<Job>() }
    val context = LocalContext.current

    LaunchedEffect(true) {
        try {
            val response = apiService.getAllJobs()
            if (response.isNotEmpty()) {
                jobList.clear()
                jobList.addAll(response)
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    JobListScreen(jobs = jobList)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobListScreen(jobs: List<Job>) {
    var isSearchMode by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val totalJobs = jobs.size // Calculate the total number of jobs

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.projob_logo1_12fc55031a756ac453bf), // Replace with your app logo resource
                            contentDescription = "App Logo",
                            modifier = Modifier.size(86.dp) // Adjust size as needed
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isSearchMode) {
                    TextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Search jobs...") },
                        modifier = Modifier.weight(1f)
                    )
                } else {
                    Text(
                        text = "$totalJobs Available Jobs", // Dynamic text for total jobs
                        style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.weight(1f),
                        fontSize = 20.sp
                    )
                }

                IconButton(onClick = { isSearchMode = !isSearchMode }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = if (isSearchMode) "Close Search" else "Open Search"
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                val filteredJobs = if (isSearchMode) {
                    jobs.filter {
                        it.title.contains(searchQuery, ignoreCase = true) ||
                                it.company.contains(searchQuery, ignoreCase = true) ||
                                it.location.contains(searchQuery, ignoreCase = true)
                    }
                } else {
                    jobs
                }

                if (filteredJobs.isEmpty()) {
                    item {
                        Text(
                            text = "No jobs found.",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    items(filteredJobs) { job ->
                        JobCard(job = job)
                    }
                }
            }
        }
    }
}
@Composable
fun JobCard(job: Job) {
    var isFavorite by remember { mutableStateOf(false) }
    Card(
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = job.title,
                style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = Color.Blue,
                fontSize = 20.sp
            )
            Text(
                text = job.company,
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
            )

            // Add location icon and location text
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",
                    modifier = Modifier.size(20.dp),
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = job.location,
                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = { isFavorite = !isFavorite }
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        tint = if (isFavorite) Color.Red else Color.Gray,
                        contentDescription = if (isFavorite) "Remove from Favorites" else "Add to Favorites"
                    )
                }

                Button(
                    onClick = {
                    }
                ) {
                    Text("View and Apply")
                }
            }
        }
    }
}

