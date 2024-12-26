package com.example.projobliveapp.Screens.profile

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projobliveapp.DataBase.ApiService
import com.example.projobliveapp.DataBase.JobApplication

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(
    navController: NavController,
    userEmail: String?,
    apiService: ApiService
) {
    var jobData by remember { mutableStateOf<JobApplication?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current
    LaunchedEffect(userEmail) {
        if (!userEmail.isNullOrEmpty()) {
            isLoading = true
            try {
                jobData = apiService.getProfileDataByEmail(userEmail)
            } catch (e: Exception) {
                Log.e("API Error", "Error fetching job application: ${e.message}", e)
                Toast.makeText(context, "Error fetching job application: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                isLoading = false
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile", style = MaterialTheme.typography.titleLarge) }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                jobData?.let { job ->
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        item {
                            Text(
                                text = "Profile Details",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                ),
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                        }

                        item {
                            ProfileCard(job)
                        }
                    }
                } ?: run {
                    Text(
                        text = "No job application data available.",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileCard(job: JobApplication) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {
            Text(
                text = "Name: ${job.firstName} ${job.lastName}",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            ProfileDetail("Email", job.email)
            ProfileDetail("Phone Number", job.phoneNumber)
            ProfileDetail("Location", job.location)
            job.resume?.let { ProfileDetail("Resume", it) }
            ProfileDetail("Skills", job.skills)
            job.about?.let { ProfileDetail("About", it) }
            job.workExperience?.let { ProfileDetail("Work Experience", it) }
            job.jobCity?.let { ProfileDetail("Job City", it) }
            job.roleLooking?.let { ProfileDetail("Role Looking For", it) }
        }
    }
}

@Composable
fun ProfileDetail(label: String, value: String?) {
    if (!value.isNullOrEmpty()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "$label:",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
