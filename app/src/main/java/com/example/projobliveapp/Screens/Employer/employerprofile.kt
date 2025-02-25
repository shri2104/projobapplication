package com.example.projobliveapp.Screens.Employer

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.projobliveapp.DataBase.ApiService
import com.example.projobliveapp.DataBase.CompanyDetails
import com.example.projobliveapp.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyProfileScreen(navController: NavController, apiService: ApiService, email: String?) {
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var companyData by remember { mutableStateOf<CompanyDetails?>(null) }
    val context = LocalContext.current

    LaunchedEffect(email) {
        if (!email.isNullOrEmpty()) {
            isLoading = true
            errorMessage = null
            try {
                val userIdResponse = apiService.getuserid(email)
                val userId = userIdResponse.userId
                if (!userId.isNullOrBlank()) {
                    companyData = apiService.getcomapnyData(userId)
                } else {
                    errorMessage = "User ID not found"
                }
            } catch (e: Exception) {
                errorMessage = "Error fetching data: ${e.message}"
                Toast.makeText(context, "Error fetching data: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                isLoading = false
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Company Profile",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {

                }
            )
        }
    ) { paddingValues ->
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (errorMessage != null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = errorMessage ?: "Unknown error", color = Color.Red)
            }
        } else {
            companyData?.let { data ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = rememberImagePainter(data.userId),
                            contentDescription = "Company Logo",
                            modifier = Modifier
                                .size(64.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(text = data.companyName, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                            Text(text = "${data.industryType} | ${data.companyAddress}", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                        }
                    }

                    Text(text = "About Us", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(text = data.aboutCompany, style = MaterialTheme.typography.bodyMedium)

                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        HighlightCard("Employees", data.companySize.toString())
                        HighlightCard("Founded", data.yearOfEstablishment)
                        HighlightCard("Rating", "${data.companySize} ★")
                    }

                    Text(text = "Contact & Socials", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        ContactItem(Icons.Default.Email, data.companyEmail)
                        ContactItem(Icons.Default.Language, data.companyWebsite)
                        data.socialMediaLinks?.takeIf { it.isNotBlank() }?.let { ContactItem(Icons.Default.Message, it) }
                    }

//                    Text(text = "Open Positions", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
//                    data.jobs.forEach { job ->
//                        JobCard(job.title, job.location, job.salary)
//                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(onClick = { navController.navigate("jobpost") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(8.dp)) {
                        Text(text = "Post a Job", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun HighlightCard(title: String, value: String) {
    Card(
        modifier = Modifier

            .padding(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = value, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            Text(text = title, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }
    }
}

// Contact Item Row
@Composable
fun ContactItem(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = text, modifier = Modifier.size(20.dp), tint = Color.Blue)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
    }
}

// Job Listing Card
@Composable
fun JobCard(position: String, location: String, salary: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = position, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(text = location, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                Text(text = salary, style = MaterialTheme.typography.bodyMedium, color = Color.Green)
            }
            IconButton(onClick = { /* View Details */ }) {
                Icon(Icons.Default.ArrowForward, contentDescription = "View Job")
            }
        }
    }
}
