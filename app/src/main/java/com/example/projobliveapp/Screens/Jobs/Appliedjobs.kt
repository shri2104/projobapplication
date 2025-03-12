package com.example.projobliveapp.Screens.Jobs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.projobliveapp.DataBase.ApiService
import com.example.projobliveapp.DataBase.jobapplications
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Search
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
import com.example.projobliveapp.DataBase.Job
import com.example.projobliveapp.DataBase.JobPost
import com.example.projobliveapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun Appliedjobs(apiService: ApiService, navController: NavHostController, userEmail: String){
    val jobList = remember { mutableStateListOf<JobPost>() }
    val appliedjobs = remember { mutableStateListOf<jobapplications>() }
    val context = LocalContext.current
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(true) {
        Log.d("JobList", "Fetching jobs from API...")
        try {
            val filteredJobs = apiService.getAllJobs()
            if (filteredJobs.isNotEmpty()) {
                jobList.clear()
                jobList.addAll(filteredJobs)
                Log.d("JobList", "Successfully fetched ${filteredJobs.size} jobs")
            } else {
                Log.d("JobList", "No jobs found in the response")
            }
        } catch (e: Exception) {
            Log.e("JobList", "Error fetching jobs: ${e.message}", e)
            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(userEmail) {
        if (userEmail.isNotBlank()) {
            loading = true
            error = null
            try {
                val userIdResponse = apiService.getuserid(userEmail)
                val userId = userIdResponse.userId

                if (!userId.isNullOrBlank()) {
                    val response = apiService.getappliedjobids(userId)
                    if (response.isNotEmpty()) {
                        appliedjobs.clear()
                        appliedjobs.addAll(response)
                        Log.d("JobList", "Successfully fetched ${response.size} applied jobs")
                    } else {
                        Log.d("JobList", "No applied jobs found for user")
                    }
                } else {
                    error = "User ID not found"
                }
            } catch (e: Exception) {
                error = "Failed to fetch user data: ${e.message}"
                Log.e("JobList", error, e)
            } finally {
                loading = false
            }
        } else {
            error = "Please enter a valid email"
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
                    onClick = {navController.navigate("AvailableInterns/$userEmail")  },
                    modifier = Modifier.weight(1f)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Groups, contentDescription = "Internships")
                        Text(text = "Internships", style = MaterialTheme.typography.titleSmall)
                    }
                }

                IconButton(
                    onClick = { navController.navigate("AvailableJobs/$userEmail") },
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
            appliedjobs=appliedjobs,
            navController = navController,
            userEmail = userEmail,
            apiService = apiService,
            modifier = Modifier.padding(paddingValues),
            internorjob="Job(s)",
            Appliedjobsection=true
        )
    }
}

