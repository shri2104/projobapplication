import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.sp
import com.example.projobliveapp.DataBase.ApiService
import com.example.projobliveapp.DataBase.CompanyDetails
import com.example.projobliveapp.DataBase.JobPost
import com.example.projobliveapp.DataBase.JobViewModel
import com.example.projobliveapp.Screens.Employer.CompanyLogo
import kotlinx.coroutines.delay
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UserGreetingScreen(email: String, apiService: ApiService) {
    var userName by remember { mutableStateOf<String?>(null) }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(email) {
        if (email.isNotBlank()) {
            loading = true
            error = null
            try {
                val userIdResponse = apiService.getuserid(email)
                val userId = userIdResponse.userId

                if (!userId.isNullOrBlank()) {
                    val userData = apiService.getCandidatepersonaldata(userId)
                    userName = userData.Firstname
                } else {
                    error = "User ID not found"
                }
            } catch (e: Exception) {
                error = "Failed to fetch user data: ${e.message}"
            } finally {
                loading = false
            }
        } else {
            error = "Please enter a valid email"
        }
    }

    Surface(modifier = Modifier.fillMaxWidth()) {
        when {
            loading -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }
            }
            error != null -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(text = error ?: "Unknown error")
                }
            }
            userName != null -> {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Hi, $userName! 👋",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Your next opportunity is just a click away!",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun JobAppSlidingMenuScreen(
    navController: NavHostController,
    userEmail: String,
    apiservice: ApiService,
    jobViewModel: JobViewModel
) {
    var isMenuVisible by remember { mutableStateOf(false) }
    val menuWidth by animateFloatAsState(
        targetValue = if (isMenuVisible) 0.5f else 0f,  // Set to half screen
        label = "MenuWidthAnimation"
    )
    Box(modifier = Modifier.fillMaxSize()) {
        if (!isMenuVisible) {
            MainJobScreenContent(
                onMenuClick = { isMenuVisible = true },
                navController = navController,
                userEmail = userEmail,
                apiservice = apiservice,
                jobViewModel
            )
        }
        if (isMenuVisible) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(menuWidth)
                    .align(Alignment.CenterStart)
                    .background(
                        Color.White,
                        RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)
                    )
            ) {
                JobAppMenuContent(
                    onCloseMenu = { isMenuVisible = false },
                    navController = navController,
                    userEmail = userEmail,
                    apiservice = apiservice
                )
            }
        }
    }
}
@Composable
fun HowItWorksSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "How It Works?",
            style = MaterialTheme.typography.headlineMedium, // Increased text size
            modifier = Modifier.padding(bottom = 12.dp)
        )

        val steps = listOf(
            Step(
                title = "Create Your Profile",
                description = "Sign up and complete your profile with your skills, experience, and preferences to get matched with the best job opportunities.",
                icon = Icons.Default.PersonAdd
            ),
            Step(
                title = "Browse Job Listings",
                description = "Explore thousands of job listings from top companies, filter based on your preferences, and find the best opportunities for your career.",
                icon = Icons.Default.Search
            ),
            Step(
                title = "Apply & Get Hired",
                description = "Submit applications easily, track your job applications, and connect with recruiters to land your dream job.",
                icon = Icons.Default.Work
            )
        )

        steps.forEach { step ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp), // Increased padding for better spacing
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = step.icon,
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp) // Slightly larger icon
                        .padding(end = 16.dp),
                    tint = LocalContentColor.current
                )
                Column {
                    Text(
                        text = step.title,
                        style = MaterialTheme.typography.titleMedium, // Larger text size for title
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp)) // Adjusted spacing
                    Text(
                        text = step.description,
                        style = MaterialTheme.typography.bodyMedium // Increased text size for better readability
                    )
                }
            }
        }
    }
}


@Composable
fun HowItWorksSectionforemployers() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "How It Works?",
            style = MaterialTheme.typography.headlineMedium, // Increased text size
            modifier = Modifier.padding(bottom = 12.dp)
        )

        val steps = listOf(
            Step(
                title = "Register an account to start",
                description = "To register as a recruiter, visit the recruitment platform’s website, select the recruiter sign-up option, fill in the required details, verify your email, and complete your profile to start recruiting.",
                icon = Icons.Default.PersonAdd
            ),
            Step(
                title = "Explore over thousands of resumes",
                description = "Explore over thousands of resumes to find the perfect candidates for your job openings, leveraging a vast database to match skill sets and experiences with your requirements.",
                icon = Icons.Default.Lock
            ),
            Step(
                title = "Find the most suitable candidate",
                description = "Explore thousands of resumes to discover ideal candidates for your vacancies, tapping into a broad pool of talents and experiences tailored to your needs.",
                icon = Icons.Default.List
            )
        )

        steps.forEach { step ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp), // Increased padding for better spacing
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = step.icon,
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp) // Slightly larger icon
                        .padding(end = 16.dp),
                    tint = LocalContentColor.current
                )
                Column {
                    Text(
                        text = step.title,
                        style = MaterialTheme.typography.titleMedium, // Larger text size for title
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp)) // Adjusted spacing
                    Text(
                        text = step.description,
                        style = MaterialTheme.typography.bodyMedium // Increased text size for better readability
                    )
                }
            }
        }
    }
}

data class Step(
    val title: String,
    val description: String,
    val icon: ImageVector
)

@Composable
fun ActiveJobsInCitiesSection(apiService: ApiService) {
    var jobPosts by remember { mutableStateOf<List<JobPost>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        try {
            val response = apiService.getAllJobs()
            if (response.isSuccessful) {
                jobPosts = response.body() ?: emptyList()
            } else {
                errorMessage = "Failed to fetch jobs: ${response.message()}"
            }
        } catch (e: Exception) {
            errorMessage = "Error: ${e.message}"
            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        } finally {
            isLoading = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Active Jobs in Cities",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp),
            fontSize = 20.sp
        )

        when {
            isLoading -> {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            !errorMessage.isNullOrEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = errorMessage.orEmpty(), color = Color.Red, modifier = Modifier.padding(8.dp))
                }
            }

            else -> {
                // Extract unique job locations properly
                val uniqueCities = jobPosts.flatMap { it.jobLocation }.distinct().take(10)

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(uniqueCities) { city ->
                        CityCard(city = City(name = city, icon = Icons.Default.LocationCity))
                    }
                }
            }
        }
    }
}




data class City(
    val name: String,
    val icon: ImageVector
)

@Composable
fun CityCard(city: City) {
    Card(
        modifier = Modifier
            .size(160.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable {  }
            .shadow(6.dp, shape = RoundedCornerShape(16.dp)),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFFFFF3E0), Color(0xFFFFE0B2)) // Soft Orange Gradient
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(12.dp)
            ) {
                Icon(
                    imageVector = city.icon,
                    contentDescription = city.name,
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color.White.copy(alpha = 0.3f), shape = CircleShape)
                        .padding(8.dp),
                    tint = Color(0xFF6D4C41) // Soft Brown Tint for Icon
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = city.name,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF6D4C41) // Soft Brown for Readability
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun ContactDetailsSection() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Contact Details",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "ProJob",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "Raheja Platinum, off Andheri – Kurla Road, Sag Baug, Marol, Andheri East, Mumbai, Maharashtra 400059",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(modifier = Modifier.padding(vertical = 8.dp)) {
            Icon(imageVector = Icons.Default.Phone, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
            Text(text = "+91 88502 31081", style = MaterialTheme.typography.bodyLarge)
        }
        Row(modifier = Modifier.padding(vertical = 8.dp)) {
            Icon(imageVector = Icons.Default.Email, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
            Text(text = "info@projob.co.in", style = MaterialTheme.typography.bodyLarge)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Visit Our Website",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "Explore all the features of our platform to help you find the best candidates and advertise your job openings to millions of users.",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.projob.co.in"))
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Visit Our Website")
        }
    }
}

@Composable
fun SearchBarSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Search Jobs by Title or Location") },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = null)
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {  },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Search")
        }
    }
}

@Composable
fun JobForYou(apiService: ApiService, userEmail: String, navController: NavHostController) {
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var userId by remember { mutableStateOf<String?>(null) }
    val recommendedJobs = remember { mutableStateListOf<JobPost>() }
    val appliedJobs = remember { mutableStateMapOf<String, Boolean>() }
    val context = LocalContext.current

    LaunchedEffect(userEmail) {
        Log.d("JobForYou", "Fetching recommended jobs for user: $userEmail")
        if (userEmail.isNotEmpty()) {
            isLoading = true
            errorMessage = null
            try {
                val userIdResponse = apiService.getuserid(userEmail)
                userId = userIdResponse.userId

                if (!userId.isNullOrBlank()) {
                    Log.d("UserIDFetch", "Fetched userId: $userId")

                    val jobResponse = apiService.getRecommendedJobs(userId!!)
                    if (jobResponse.isSuccessful) {
                        val jobs = jobResponse.body()
                        if (!jobs.isNullOrEmpty()) {
                            recommendedJobs.clear()
                            recommendedJobs.addAll(jobs)
                            Log.d("JobList", "Fetched ${jobs.size} recommended jobs")

                            jobs.forEach { job ->
                                try {
                                    val isApplied = apiService.checkApplication(job.jobid, userId!!)
                                    appliedJobs[job.jobid] = isApplied
                                } catch (e: Exception) {
                                    Log.e("JobApplicationCheck", "Error checking application: ${e.message}")
                                    appliedJobs[job.jobid] = false
                                }
                            }
                        } else {
                            errorMessage = "No recommended jobs found."
                        }
                    } else {
                        errorMessage = "Failed to fetch jobs."
                    }
                } else {
                    errorMessage = "User ID not found."
                }
            } catch (e: Exception) {
                errorMessage = "Error fetching jobs: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
//            .padding(16.dp)
            .background(Color(0xFFE3F2FD), shape = RoundedCornerShape(16.dp))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color(0xFFE3F2FD), shape = RoundedCornerShape(8.dp))
//                    .padding(16.dp)
            ) {
                Text(
                    text = "Recommended Jobs",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color(0xFF1E88E5)
                )
                Text(
                    text = "As per your preferences",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }


            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else if (!errorMessage.isNullOrEmpty()) {
                Text(text = errorMessage!!, color = Color.Red)
            } else if (recommendedJobs.isEmpty()) {
                Text(text = "No jobs found matching your preferences.", color = Color.Gray)
            } else {
                LazyRow(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(recommendedJobs) { job ->
                        val isApplied = appliedJobs[job.jobid] ?: false
                        userId?.let {
                            JobCard(
                                job = job,
                                navController = navController,
                                userEmail = userEmail,
                                onViewClick = {
                                    Log.d("JobView", "Viewing job: ${job.jobTitle}")
                                    Toast.makeText(
                                        context,
                                        "Viewing ${job.jobTitle}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                apiService = apiService,
                                isapplied = isApplied,
                                userId = it,
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                StepIndicator(steps = listOf("Apply", "Interview", "Selection"), currentStep = 1)
            }
        }
    }
}
@Composable
fun StepIndicator1(steps: List<String>, currentStep: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        steps.forEachIndexed { index, step ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(if (index == currentStep) Color(0xFF2196F3) else Color.Gray)
                )
            }
        }
    }
}

@Composable
fun StepIndicator(steps: List<String>, currentStep: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        steps.forEachIndexed { index, step ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(if (index <= currentStep) Color(0xFF2196F3) else Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = (index + 1).toString(),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = step,
                    fontSize = 12.sp,
                    color = if (index <= currentStep) Color(0xFF2196F3) else Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            if (index < steps.size - 1) {
                Spacer(
                    modifier = Modifier
                        .height(2.dp)
                        .width(40.dp)
                        .background(if (index < currentStep) Color(0xFF2196F3) else Color.Gray)
                )
            }
        }
    }
}

@Composable
fun JobCard(
    job: JobPost,
    navController: NavHostController,
    userEmail: String,
    apiService: ApiService,
    onViewClick: () -> Unit,
    isapplied: Boolean,
    userId: String
) {
    Log.d("JobCard", "Rendering job: ${job.jobTitle}")

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
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
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

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = {
                        navController.navigate("jobDetailScreen/${job.jobid}/${userEmail}/${job.Employerid}/${isapplied}")
                    }
                ) {
                    Text(
                        text = "View Details",
                        color = Color.Blue,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}



@Composable
fun RecentlyViewedJobsSection(
    apiService: ApiService,
    navController: NavHostController,
    jobViewModel: JobViewModel,
    userEmail: String
) {
    val recentJobs by jobViewModel.recentJobs.observeAsState(emptyList())
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var userId by remember { mutableStateOf<String?>(null) }
    val recommendedJobs = remember { mutableStateListOf<JobPost>() }
    val appliedJobs = remember { mutableStateMapOf<String, Boolean>() } // Store application status
    val context = LocalContext.current

    LaunchedEffect(userEmail) {
        Log.d("JobForYou", "Fetching recommended jobs for user: $userEmail")
        if (userEmail.isNotEmpty()) {
            isLoading = true
            errorMessage = null
            try {
                val userIdResponse = apiService.getuserid(userEmail)
                userId = userIdResponse.userId

                if (!userId.isNullOrBlank()) {
                    Log.d("UserIDFetch", "Fetched userId: $userId")

                    val jobResponse = apiService.getRecommendedJobs(userId!!)
                    if (jobResponse.isSuccessful) {
                        val jobs = jobResponse.body()
                        if (!jobs.isNullOrEmpty()) {
                            recommendedJobs.clear()
                            recommendedJobs.addAll(jobs)
                            Log.d("JobList", "Fetched ${jobs.size} recommended jobs")

                            // Check application status for each job
                            jobs.forEach { job ->
                                try {
                                    val isApplied = apiService.checkApplication(job.jobid, userId!!)
                                    appliedJobs[job.jobid] = isApplied
                                } catch (e: Exception) {
                                    Log.e("JobApplicationCheck", "Error checking application: ${e.message}")
                                    appliedJobs[job.jobid] = false
                                }
                            }
                        } else {
                            errorMessage = "No recommended jobs found."
                        }
                    } else {
                        errorMessage = "Failed to fetch jobs."
                    }
                } else {
                    errorMessage = "User ID not found."
                }
            } catch (e: Exception) {
                errorMessage = "Error fetching jobs: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }
    if (recentJobs.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Recently Viewed Jobs",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(recentJobs) { job ->
                    val isApplied = appliedJobs[job.jobid] ?: false
                    Card(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(8.dp)
                            .clickable { navController.navigate("jobDetailScreen/${job.jobid}/${userEmail}/${job.Employerid}/${isApplied}")},
                        elevation = CardDefaults.cardElevation(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(25.dp)
                        ) {
                            Text(
                                text = job.jobTitle,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                            Text(
                                text = job.Companyname,
                                color = Color.Gray,
                                fontSize = 16.sp
                            )
                            Text(
                                text = "Location: ${job.jobLocation}",
                                fontSize = 14.sp
                            )
                            Text(
                                text = "Salary: ${job.minSalary} - ${job.maxSalary}",
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun TrustedByCompaniesSection(apiService: ApiService, userEmail: String) {
    val companyList = remember { mutableStateListOf<CompanyDetails>() }
    val context = LocalContext.current
    val scrollState = rememberLazyListState()

    LaunchedEffect(Unit) {
        try {
            val response = apiService.getallcompanies()
            if (response.success) {
                val companies = response.companies
                if (companies.isNotEmpty()) {
                    companyList.clear()
                    companyList.addAll(companies)
                }
            } else {
                Log.e("CompanyList", "API returned failure status")
                Toast.makeText(context, "Failed to load companies", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Log.e("CompanyList", "Error fetching companies: ${e.message}", e)
            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
    LaunchedEffect(companyList.size) {
        if (companyList.isNotEmpty()) {
            while (true) {
                delay(1500)

                val nextIndex = scrollState.firstVisibleItemIndex + 1
                scrollState.animateScrollToItem(nextIndex)

                // Reset after 5 loops
                if (nextIndex >= companyList.size * 5) {
                    scrollState.scrollToItem(0)
                }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Trusted by Companies",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (companyList.isEmpty()) {
            Text(text = "No companies available", fontSize = 16.sp)
        } else {
            LazyRow(
                state = scrollState,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Duplicate the list for seamless looping
                items(List(5) { companyList }.flatten()) { company ->
                    CompanyLogo(
                        companyId = company.userId,
                        apiService = apiService,
                    )
                }
            }
        }
    }
}




@Composable
fun BrowseByCategorySection() {
    val categories = listOf(
        "Development", "Management", "Health & Care",
        "Finance", "Design", "Education",
        "Marketing", "Engineering", "Sales"
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Browse by Category",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight=FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp),
            fontSize = 20.sp
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(categories) { category ->
                CategoryCard(category = category) {
                    println("Clicked on $category category")

                }
            }
        }
    }
}

@Composable
fun CategoryCard(category: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(120.dp)
            .height(150.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFBBDEFB)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = category,
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}

