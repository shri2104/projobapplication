package com.example.projobliveapp.Screens.Jobs

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size

import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.projobliveapp.DataBase.ApiService
import com.example.projobliveapp.DataBase.Job
import com.example.projobliveapp.DataBase.SaveJob
import com.example.projobliveapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun JobList(apiService: ApiService, navController: NavHostController, userEmail: String) {
    val jobList = remember { mutableStateListOf<Job>() }
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    // Fetching job data
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

    Scaffold(
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.fillMaxWidth(),
            ) {
                IconButton(
                    onClick = { navController.navigate("homeScreen/$userEmail") },
                    modifier = Modifier.weight(1f)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Home, contentDescription = "Home")
                        Text(text = "Home", style = MaterialTheme.typography.titleSmall)
                    }
                }
                IconButton(
                    onClick = {  },
                    modifier = Modifier.weight(1f)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Groups, contentDescription = "Internships")
                        Text(text = "Internships", style = MaterialTheme.typography.titleSmall)
                    }
                }
                IconButton(
                    onClick = { },
                    modifier = Modifier.weight(1f)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Work, contentDescription = "Jobs")
                        Text(text = "Jobs", style = MaterialTheme.typography.titleSmall)
                    }
                }
                IconButton(
                    onClick = { },
                    modifier = Modifier.weight(1f)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Message, contentDescription = "Messages")
                        Text(text = "Messages", style = MaterialTheme.typography.titleSmall)
                    }
                }
            }
        }
    ) { paddingValues ->
        JobListScreen(
            jobs = jobList,
            expanded = expanded,
            onExpandChanged = { expanded = it },
            navController = navController,
            userEmail = userEmail,
            apiService = apiService,
            modifier = Modifier.padding(paddingValues) // Ensure the padding is applied
        )
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobListScreen(
    jobs: List<Job>,
    expanded: Boolean,
    onExpandChanged: (Boolean) -> Unit,
    navController: NavHostController,
    userEmail: String,
    apiService: ApiService,
    modifier: Modifier
) {
    var isSearchMode by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val totalJobs = jobs.size
    val interactionSource = remember { MutableInteractionSource() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.projob_logo1_12fc55031a756ac453bf),
                            contentDescription = "App Logo",
                            modifier = Modifier.size(86.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { onExpandChanged(!expanded) }) {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = "Profile Icon",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { onExpandChanged(false) }
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                navController.navigate("homeScreen/$userEmail")
                                onExpandChanged(false)
                            },
                            text = { Text("Home") }
                        )
                        DropdownMenuItem(
                            onClick = {
                                navController.navigate("profileSection/$userEmail")
                                onExpandChanged(false)
                            },
                            text = { Text("Profile") }
                        )
                        DropdownMenuItem(
                            onClick = {
                                navController.navigate("SavedJobs/$userEmail")
                                onExpandChanged(false)
                            },
                            text = { Text("Saved Jobs") }
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
            Text(
                text = "$totalJobs Available Jobs",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                val filteredJobs = if (isSearchMode) {
                    jobs.filter {
                        it.jobTitle.contains(searchQuery, ignoreCase = true) ||
                                it.company.contains(searchQuery, ignoreCase = true) ||
                                it.jobLocation.contains(searchQuery, ignoreCase = true)
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
                        JobCard(
                            job = job,
                            navController = navController,
                            apiService = apiService,
                            userEmail = userEmail
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun JobCard(
    job: Job,
    navController: NavHostController,
    apiService: ApiService,
    userEmail: String
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var isFavorite by remember { mutableStateOf(false) }

    LaunchedEffect(job._id) {
        try {
            val response = apiService.isJobInFavorites(userEmail, job._id)
            if (response.isSuccessful) {
                isFavorite = response.body()?.success ?: false
            } else {
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    Card(
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = job.jobTitle,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = Color.Blue,
                fontSize = 20.sp
            )
            Text(text = job.company, style = MaterialTheme.typography.bodyMedium)

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",
                    modifier = Modifier.size(20.dp),
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = job.jobLocation, style = MaterialTheme.typography.bodyMedium)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            val newFavoriteState = !isFavorite
                            try {
                                // Prepare the SaveJob object
                                val saveJob = SaveJob(userEmail, listOf(job._id))

                                // Log to check the structure of SaveJob
                                Log.d("SaveJob", "Saving job: $saveJob")

                                // Make the API call
                                val response = if (newFavoriteState) {
                                    apiService.addJobToFavorite(saveJob)
                                } else {
                                    apiService.deleteFavorite(saveJob)
                                }

                                // Handle the response
                                withContext(Dispatchers.Main) {
                                    if (response.isSuccessful) {
                                        isFavorite = newFavoriteState
                                        Toast.makeText(
                                            context,
                                            if (isFavorite) "Added to Favorites" else "Removed from Favorites",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Error: ${response.message()}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            } catch (e: Exception) {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        tint = if (isFavorite) Color.Red else Color.Gray,
                        contentDescription = if (isFavorite) "Remove from Favorites" else "Add to Favorites"
                    )
                }

                Button(
                    onClick = {
                        navController.navigate(
                            "jobDetailScreen/${job.jobTitle}/${job.jobDescription}/${job.jobLocation}/${job.company}/${job.minSalary}/${job.maxSalary}/${job.createdAt}/${job.minExperience}/${job.maxExperience}/${job.keySkills}/${job.createdBy}/${job.createdByEmp}/${job.shortlisted}/${job.applications}/${job.updatedAt}/${userEmail}"
                        )
                    }
                ) {
                    Text("View and Apply")
                }
            }
        }
    }
}



