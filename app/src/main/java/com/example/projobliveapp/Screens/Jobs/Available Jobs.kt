package com.example.projobliveapp.Screens.Jobs

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.projobliveapp.DataBase.ApiService
import com.example.projobliveapp.DataBase.ExperienceDetails
import com.example.projobliveapp.DataBase.JobPost
import com.example.projobliveapp.DataBase.JobViewModel
import com.example.projobliveapp.DataBase.SaveJob
import com.example.projobliveapp.DataBase.jobapplications
import com.example.projobliveapp.R
import com.example.projobliveapp.Screens.Employer.CompanyLogo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun InternshipList(apiService: ApiService, navController: NavHostController, userEmail: String,jobViewModel: JobViewModel) {
    val internshipList = remember { mutableStateListOf<JobPost>() }
    val context = LocalContext.current
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    val appliedjobs = remember { mutableStateListOf<jobapplications>() }
    LaunchedEffect(true) {
        Log.d("JobList", "Fetching jobs from API...")
        try {
            val response = apiService.getAllJobs()
            if (response.isSuccessful) {
                val jobs = response.body()
                if (!jobs.isNullOrEmpty()) {
                    val filteredJobs = jobs.filter { it.contractType == "Internship" }
                    internshipList.clear()
                    internshipList.addAll(filteredJobs)
                    Log.d("JobList", "Successfully fetched ${filteredJobs.size} jobs")
                } else {
                    Log.d("JobList", "No jobs found in the response")
                }
            } else {
                Log.e("JobList", "API call failed: ${response.errorBody()?.string()}")
                Toast.makeText(context, "Error: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
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
                    onClick = {  },
                    modifier = Modifier.weight(1f)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Groups, contentDescription = "Internships")
                        Text(text = "Internships", style = MaterialTheme.typography.titleSmall)
                    }
                }
                IconButton(
                    onClick = { navController.navigate("AvailableJobs/$userEmail")},
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
            jobs = internshipList,
            navController = navController,
            userEmail = userEmail,
            apiService = apiService,
            modifier = Modifier.padding(paddingValues),
            internorjob = "Internships(s)",
            appliedjobs = appliedjobs,
            Appliedjobsection = false,
            jobViewModel = jobViewModel,
        )
    }
}

@Composable
fun JobList(apiService: ApiService, navController: NavHostController, userEmail: String,jobViewModel: JobViewModel) {
    val jobList = remember { mutableStateListOf<JobPost>() }
    val appliedjobs = remember { mutableStateListOf<jobapplications>() }
    val context = LocalContext.current
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(true) {
        Log.d("JobList", "Fetching jobs from API...")
        try {
            val response = apiService.getAllJobs()
            if (response.isSuccessful) {
                val jobs = response.body()
                if (!jobs.isNullOrEmpty()) {
                    val filteredJobs = jobs.filter { it.contractType == "Job" }
                    jobList.clear()
                    jobList.addAll(filteredJobs)
                    Log.d("JobList", "Successfully fetched ${filteredJobs.size} jobs")
                } else {
                    Log.d("JobList", "No jobs found in the response")
                }
            } else {
                Log.e("JobList", "API call failed: ${response.errorBody()?.string()}")
                Toast.makeText(context, "Error: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
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
            appliedjobs = appliedjobs,
            navController = navController,
            userEmail = userEmail,
            apiService = apiService,
            modifier = Modifier.padding(paddingValues),
            internorjob = "Job(s)",
            Appliedjobsection = false,
            jobViewModel = jobViewModel
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobListScreen(
    jobs: List<JobPost>,
    appliedjobs: List<jobapplications>,
    navController: NavHostController,
    userEmail: String,
    apiService: ApiService,
    modifier: Modifier = Modifier,
    internorjob: String,
    Appliedjobsection:Boolean,
    jobViewModel: JobViewModel
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("All") }
    var isSearchBarVisible by remember { mutableStateOf(false) }
    var isFilterVisible by remember { mutableStateOf(false) } // Controls filter visibility
    var expanded by remember { mutableStateOf(false) }
    val totalJobs = jobs.size
    val filterOptions = listOf("All", "Job Title", "Company Name", "Location")
    Column {
        TopAppBar(
            title = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.projob_logo1_12fc55031a756ac453bf),
                        contentDescription = "App Logo",
                        modifier = Modifier.size(90.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            },
            actions = {
                IconButton(onClick = { isSearchBarVisible = !isSearchBarVisible }) {
                    Icon(Icons.Default.Search, contentDescription = "Toggle Search")
                }
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(Icons.Filled.AccountCircle, contentDescription = "Profile Icon", modifier = Modifier.size(24.dp))
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        onClick = {
                            navController.navigate("homeScreen/$userEmail")
                            expanded = false
                        },
                        text = { Text("Home") }
                    )
                    DropdownMenuItem(
                        onClick = {
                            navController.navigate("profileSection/$userEmail")
                            expanded = false
                        },
                        text = { Text("Profile") }
                    )
                }
            }
        )

        Scaffold { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                if(!Appliedjobsection){
                    Text(
                        text = "$totalJobs Available $internorjob",
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                if (isSearchBarVisible) {
                    Card(
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                OutlinedTextField(
                                    value = searchQuery,
                                    onValueChange = { searchQuery = it },
                                    label = { Text("Search Jobs") },
                                    modifier = Modifier.weight(1f),
                                    singleLine = true,
                                    colors = TextFieldDefaults.colors(
                                        unfocusedIndicatorColor = Color.Transparent,
                                        focusedIndicatorColor = Color.Transparent,
                                        cursorColor = Color.Blue,
                                        focusedContainerColor = Color.White,
                                        unfocusedContainerColor = Color.White
                                    ),
                                    trailingIcon = {
                                        Row {
                                            IconButton(onClick = {
                                                searchQuery = ""
                                                isFilterVisible = false // Hide filters when clearing search
                                            }) {
                                                Icon(Icons.Default.Clear, contentDescription = "Clear search")
                                            }
                                            IconButton(onClick = { isFilterVisible = !isFilterVisible }) {
                                                Icon(Icons.Default.FilterList, contentDescription = "Toggle Filters")
                                            }
                                        }
                                    }
                                )
                            }

                            if (isFilterVisible) { // Show filter options in LazyRow
                                LazyRow(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    items(filterOptions) { filter ->
                                        Button(
                                            onClick = { selectedFilter = filter },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = if (selectedFilter == filter) Color.Blue else Color.LightGray,
                                                contentColor = Color.White
                                            )
                                        ) {
                                            Text(filter)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                val filteredJobs = if (searchQuery.isNotEmpty()) {
                    when (selectedFilter) {
                        "Job Title" -> jobs.filter { it.jobTitle.contains(searchQuery, ignoreCase = true) }
                        "Company Name" -> jobs.filter { it.Companyname.contains(searchQuery, ignoreCase = true) }
                        "Location" -> jobs.filter { it.jobLocation.any { location -> location.contains(searchQuery, ignoreCase = true) } }
                        else -> jobs.filter {
                            it.jobTitle.contains(searchQuery, ignoreCase = true) ||
                                    it.Companyname.contains(searchQuery, ignoreCase = true) ||
                                    it.jobLocation.any { location -> location.contains(searchQuery, ignoreCase = true) }
                        }
                    }
                } else {
                    jobs
                }
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 80.dp)
                ) {
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
                            val isApplied = appliedjobs.any { it.jobid == job.jobid }
                            JobCard(
                                job = job,
                                isApplied = isApplied,
                                navController = navController,
                                apiService = apiService,
                                userEmail = userEmail,
                                Appliedjobsection = Appliedjobsection,
                                viewModel = jobViewModel,
                            )
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun JobCard(
    job: JobPost,
    isApplied: Boolean,
    navController: NavHostController,
    apiService: ApiService,
    userEmail: String,
    Appliedjobsection: Boolean,
    viewModel: JobViewModel
) {
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    var userId by remember { mutableStateOf<String?>(null) }

    if (Appliedjobsection && !isApplied) return

    LaunchedEffect(userEmail) {
        if (userEmail.isNotEmpty()) {
            isLoading = true
            errorMessage = null
            try {
                val userIdResponse = apiService.getuserid(userEmail)
                userId = userIdResponse.userId
                if (!userId.isNullOrBlank()) {
                    Log.d("UserIDFetch", "Fetched userId: $userId")
                } else {
                    errorMessage = "User ID not found."
                }
            } catch (e: Exception) {
                errorMessage = "Error fetching user ID: ${e.message}"
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                Log.e("UserIDFetch", "Error: ${e.message}", e)
            } finally {
                isLoading = false
            }
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(10.dp)
            .clickable { Log.d("JobClick", "Clicked on job: ${job.jobTitle}") },
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .background(Color(0xFFE0F7FA), RoundedCornerShape(8.dp))
                        .padding(4.dp)
                ) {
                    Text(
                        text = "Actively hiring",
                        color = Color(0xFF00796B),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                CompanyLogo(
                    companyId = job.Employerid,
                    apiService = apiService,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = job.jobTitle,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = job.Companyname,
                fontSize = 18.sp,
                color = Color.Gray,
                modifier = Modifier
                    .padding(bottom = 6.dp)
                    .clickable {
                        navController.navigate("CompanyProfileScreenforcandidates/${job.Employerid}/${userId}")
                        Log.d("CompanyClick", "Navigating to company profile of ${job.Companyname}")
                    }
            )
            Text(
                text = job.jobLocation.joinToString(),
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "₹ ${job.minSalary} - ${job.maxSalary} / month",
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "${job.contractType} Months",
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp)) // Slightly larger spacer to create separation

            Spacer(modifier = Modifier.weight(1f))

            Row(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = {
                        navController.navigate("jobDetailScreen/${job.jobid}/${userEmail}/${job.Employerid}/${isApplied}")
                        viewModel.addJobToRecentlyViewed(job)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 4.dp)
                ) {
                    Text("View", color = Color.White)
                }

                Button(
                    onClick = {
                        if (!isApplied) {
                            navController.navigate("Applicationscreen/${job.jobid}/${userEmail}/${job.Employerid}")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = if (isApplied) Color.Gray else Color(0xFFE6F7FF)),
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 4.dp)
                ) {
                    Text(
                        text = if (isApplied) "Applied" else "Apply",
                        color = if (isApplied) Color.White else Color.Blue,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 8.dp)
                )
            }

            errorMessage?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 8.dp)
                )
            }
        }
    }
}
