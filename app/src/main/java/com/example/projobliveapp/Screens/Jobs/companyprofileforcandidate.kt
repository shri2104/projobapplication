package com.example.projobliveapp.Screens.Jobs
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projobliveapp.DataBase.ApiService
import com.example.projobliveapp.DataBase.CompanyDetails
import com.example.projobliveapp.DataBase.FollowRequest
import com.example.projobliveapp.DataBase.JobPost
import com.example.projobliveapp.Screens.Employer.CompanyLogo
import com.example.projobliveapp.Screens.Employer.ContactItem
import com.example.projobliveapp.Screens.Employer.HighlightCard
import com.example.projobliveapp.Screens.Employer.JobCard1
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyProfileScreenforCandidate(
    navController: NavController,
    apiService: ApiService,
    employerId: String,
    userId: String // Pass the logged-in user's ID
) {
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var companyData by remember { mutableStateOf<CompanyDetails?>(null) }
    var jobList by remember { mutableStateOf<List<JobPost>>(emptyList()) }
    var isFollowing by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Fetch company data
    LaunchedEffect(employerId) {
        isLoading = true
        errorMessage = null
        try {
            companyData = apiService.getcomapnyData(employerId)
        } catch (e: Exception) {
            errorMessage = "Error fetching company data: ${e.message}"
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        } finally {
            isLoading = false
        }
    }

    // Fetch jobs by employerId
    LaunchedEffect(employerId) {
        isLoading = true
        errorMessage = null
        try {
            val response = apiService.getJobByemployerId(employerId)
            jobList = response.take(3) // Fetch top 3 jobs
            Log.d("PostedJobs", "Successfully fetched jobs: ${response.size}")
        } catch (e: Exception) {
            errorMessage = "Failed to load jobs: ${e.localizedMessage}"
            Log.e("PostedJobs", "Exception: ${e.message}")
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        } finally {
            isLoading = false
        }
    }
    LaunchedEffect(userId, employerId) {
        try {
            val response = apiService.checkFollowStatus(userId, employerId)
            isFollowing = response.following
        } catch (e: Exception) {
            Log.e("FollowCheck", "Error checking follow status: ${e.message}")
        }
    }

    // Function to toggle follow status
    suspend fun toggleFollow() {
        try {
            val response = apiService.toggleFollow(FollowRequest(userId, employerId))
            if (response.isSuccessful) {
                response.body()?.let {
                    isFollowing = it.following
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Failed to update follow status", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Log.e("FollowToggle", "Error toggling follow status: ${e.message}")
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Company Profile", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        when {
            isLoading -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            errorMessage != null -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = errorMessage ?: "Unknown error", color = Color.Red)
            }
            else -> companyData?.let { data ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            CompanyLogo(companyId = data.userId, apiService = apiService)
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(text = data.companyName, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                                Text(
                                    text = "${data.industryType} | ${data.companyAddress}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray
                                )
                            }
                        }
                    }

                    // Follow Button
                    item {
                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    try {
                                        val response = apiService.toggleFollow(FollowRequest(userId, employerId))
                                        if (response.isSuccessful) {
                                            isFollowing = response.body()?.following ?: false
                                            Toast.makeText(context, response.body()?.message ?: "", Toast.LENGTH_SHORT).show()
                                        }
                                    } catch (e: Exception) {
                                        Log.e("Follow", "Error: ${e.message}")
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isFollowing) Color.Gray else MaterialTheme.colorScheme.primary
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(if (isFollowing) "Following" else "Follow", fontWeight = FontWeight.Bold)
                        }

                    }

                    item { Text("About Us", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold) }
                    item { Text(data.aboutCompany, style = MaterialTheme.typography.bodyMedium) }

                    item {
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                            HighlightCard("Employees", data.companySize.toString())
                            HighlightCard("Founded", data.yearOfEstablishment)
                            HighlightCard("Rating", "4.2+★")
                        }
                    }

                    item { Text("Contact & Socials", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold) }
                    item {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            ContactItem(Icons.Default.Email, data.companyEmail)
                            ContactItem(Icons.Default.Language, data.companyWebsite)
                            data.socialMediaLinks?.takeIf { it.isNotBlank() }?.let { ContactItem(Icons.Default.Message, it) }
                        }
                    }

                    item { Text("Open Positions", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold) }
                    items(jobList) { job ->
                        JobCard1(job.jobTitle, job.country)
                    }
                }
            }
        }
    }
}
