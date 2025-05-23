package com.example.projobliveapp.Screens.Employer

import android.Manifest
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.projobliveapp.DataBase.ApiService
import com.example.projobliveapp.DataBase.JobPost
import com.example.projobliveapp.DataBase.PersonalData
import com.example.projobliveapp.DataBase.jobapplications
import com.example.projobliveapp.R
import downloadResume
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun postedJobs(
    navController: NavHostController,
    employerid: String,
    apiService: ApiService,
    userEmail: String
) {
    // Fetch Job by Employer ID
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var jobList by remember { mutableStateOf<List<JobPost>>(emptyList()) }

    // Fetch Job by Employer ID
    LaunchedEffect(employerid) {
        isLoading = true
        errorMessage = null
        try {
            val response = apiService.getJobByemployerId(employerid)
            if (response.isNotEmpty()) {
                jobList = response
                Log.d("PostedJobs", "Successfully fetched jobs: ${response.size}")
            } else {
                errorMessage = "No jobs found for this employer."
                Log.e("PostedJobs", errorMessage ?: "Unknown error")
            }
        } catch (e: Exception) {
            errorMessage = "Failed to load jobs: ${e.localizedMessage}"
            Log.e("PostedJobs", "Exception: ${e.message}")
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        } finally {
            isLoading = false
        }
    }


    Scaffold(
        bottomBar = {
            BoxWithConstraints {
                val density = LocalDensity.current
                val textSize = with(density) { (maxWidth / 30).toSp() }
                BottomAppBar(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    IconButton(
                        onClick = { navController.navigate("EmployerHomeScreen/$userEmail") },
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Home, contentDescription = "Home")
                            Text(text = "Home", style = MaterialTheme.typography.labelSmall.copy(fontSize = textSize))
                        }
                    }
                    IconButton(
                        onClick = { },
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Groups, contentDescription = "Application")
                            Text(text = "Application", style = MaterialTheme.typography.labelSmall.copy(fontSize = textSize))
                        }
                    }
                    IconButton(
                        onClick = { navController.navigate("postedjobs/${employerid}/${userEmail}") },
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Work, contentDescription = "Jobs")
                            Text(text = "Jobs",style = MaterialTheme.typography.labelSmall.copy(fontSize = textSize))
                        }
                    }
                    IconButton(
                        onClick = { },
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Message, contentDescription = "Messages")
                            Text(text = "Messages",style = MaterialTheme.typography.labelSmall.copy(fontSize = textSize))
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        poosetdscreen(
            jobs = jobList,
            navController = navController,
            userEmail = userEmail,
            apiService = apiService,
            modifier = Modifier.padding(paddingValues)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun poosetdscreen(
    jobs: List<JobPost>,
    navController: NavHostController,
    userEmail: String,
    apiService: ApiService,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("All") }
    var isSearchBarVisible by remember { mutableStateOf(false) }
    var isFilterVisible by remember { mutableStateOf(false) } // Controls filter visibility
    var expanded by remember { mutableStateOf(false) }
    val totalJobs = jobs.size
    val filterOptions = listOf("All", "Job Title", "Location")
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
                Text(
                    text = "$totalJobs Available Jobs",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(16.dp)
                )
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
}
@Composable
fun formatTimestamp(timestamp: String?): String {
    if (timestamp.isNullOrEmpty()) return "N/A"
    return try {
        val parsedDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).parse(timestamp)
        val formattedDate = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()).format(parsedDate!!)
        formattedDate
    } catch (e: Exception) {
        "Invalid Date"
    }
}

@Composable
fun JobCard(
    job: JobPost,
    navController: NavHostController,
    apiService: ApiService,
    userEmail: String
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var isFavorite by remember { mutableStateOf(false) }

    Card(
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = job.jobTitle,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue,
                    fontSize = 20.sp
                )
                Text(
                    text = job.Companyname,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = formatTimestamp(job.createdAt ?: "N/A"),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Job Location
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",
                    modifier = Modifier.size(20.dp),
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = job.jobLocation.toString(),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Salary Information
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.AttachMoney,
                    contentDescription = "Salary",
                    modifier = Modifier.size(20.dp),
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${job.minSalary} - ${job.maxSalary} per annum",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
            Spacer(modifier = Modifier.height(4.dp))

            Button(
                onClick = {
                    Log.d("JobCard", "Navigating to application screen for job ${job.jobid}")
                    navController.navigate("CandidateApplications/${job.jobid}/${userEmail}/${job.Employerid}")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("View Applications", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CandidateApplications(
    apiService: ApiService,
    userEmail: String,
    jobid: String,
    employerid: String,
    navController: NavController
) {
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var jobList by remember { mutableStateOf<List<jobapplications>>(emptyList()) }
    var candidates by remember { mutableStateOf<List<PersonalData>>(emptyList()) }
    val context = LocalContext.current

    LaunchedEffect(jobid) {
        isLoading = true
        errorMessage = null
        try {
            val response = apiService.getapplieduserids(jobid)
            if (response.isNotEmpty()) {
                jobList = response
                val candidateData = response.mapNotNull { job ->
                    try {
                        apiService.getCandidatepersonaldata(job.userid)
                    } catch (e: Exception) {
                        null
                    }
                }
                candidates = candidateData
            } else {
                errorMessage = "No applications received yet."
            }
        } catch (e: Exception) {
            errorMessage = "Failed to load applications: ${e.localizedMessage}"
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Received Applications") })
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.fillMaxWidth(),
            ) {
                IconButton(
                    onClick = { navController.navigate("EmployerHomeScreen/$userEmail") },
                    modifier = Modifier.weight(1f)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Home, contentDescription = "Home")
                        Text(text = "Home", style = MaterialTheme.typography.titleSmall)
                    }
                }
                IconButton(
                    onClick = { },
                    modifier = Modifier.weight(1f)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Groups, contentDescription = "Applications")
                        Text(text = "Applications", style = MaterialTheme.typography.titleSmall)
                    }
                }
                IconButton(
                    onClick = { navController.navigate("postedjobs/$employerid/$userEmail") },
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
        Box(modifier = Modifier.padding(paddingValues)) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (errorMessage != null) {
                Text(
                    text = errorMessage ?: "Error occurred",
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(candidates) { candidate ->
                        CandidateCard(candidate, apiService, context,navController)
                    }
                }
            }
        }
    }
}


@Composable
fun CandidateCard(
    candidate: PersonalData,
    apiService: ApiService,
    context: Context,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(4.dp, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "First Name: ${candidate.Firstname}", style = MaterialTheme.typography.headlineSmall)
            Text(text = "Last Name: ${candidate.Lastname}", style = MaterialTheme.typography.titleSmall)
            Text(text = "Gender: ${candidate.gender}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Nationality: ${candidate.nationality}", style = MaterialTheme.typography.titleSmall)

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {navController.navigate("candidateresume/${candidate.userId}")
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
            ) {
                Text("Download Resume")
            }
        }
    }
}

@Composable
fun downloadresume(apiService: ApiService, userId: String) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var message by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }


    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
        ActivityCompat.requestPermissions(
            context as android.app.Activity,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            1
        )
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (userId.isNotEmpty()) {
                    coroutineScope.launch {
                        isLoading = true
                        downloadResume(context, apiService, userId) { success, resultMessage ->
                            message = resultMessage
                            Toast.makeText(context, resultMessage, Toast.LENGTH_LONG).show()
                            isLoading = false
                        }
                    }
                } else {
                    Toast.makeText(context, "Please enter a User ID", Toast.LENGTH_SHORT).show()
                }
            },
            enabled = userId.isNotEmpty()
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            } else {
                Text("Download Resume")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        errorMessage?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyMedium)
        }

        if (message.isNotEmpty()) {
            Text(text = message, style = MaterialTheme.typography.bodyMedium)
        }
    }
}