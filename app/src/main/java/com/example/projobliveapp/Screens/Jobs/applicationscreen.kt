package com.example.projobliveapp.Screens.Jobs

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.example.projobliveapp.DataBase.ApiService
import com.example.projobliveapp.DataBase.JobPost
import com.example.projobliveapp.Navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobApplicationScreen(
    jobid: String,
    apiService: ApiService,
    userEmail: String,
    navController: NavHostController
) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var jobData by remember { mutableStateOf<JobPost?>(null) }
    var userId by remember { mutableStateOf<String?>(null) }
    var resumeExists by remember { mutableStateOf(false) }
    var resumeUrl by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(jobid) {
        if (jobid.isNotEmpty()) {
            isLoading = true
            errorMessage = null
            try {
                jobData = apiService.jobbyid(jobid)
            } catch (e: Exception) {
                errorMessage = "Error fetching job data: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    val totalSteps = 3
    val currentStep = remember { mutableStateOf(1) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    TextButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Text("Cancel", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            )
        },
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
                    onClick = { },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = jobData?.jobTitle ?: "",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${jobData?.Companyname ?: ""} - ${jobData?.jobLocation ?: ""}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )
            TextButton(
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Text("See More Details", style = MaterialTheme.typography.bodySmall, color = Color.Blue)
            }
            LinearProgressIndicator(
                progress = currentStep.value / totalSteps.toFloat(),
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary
            )

            when (currentStep.value) {
                1 -> ResumeScreenContent(userEmail, apiService, navController)
                2 -> CoverLetterScreenContent()
                3 -> ReviewAndSubmitScreenContent(userEmail,navController)
            }

            Spacer(modifier = Modifier.height(16.dp))
            if (currentStep.value < totalSteps) {
                Button(
                    onClick = { currentStep.value++ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Continue")
                }
            } else {
                Button(
                    onClick = {
                        navController.navigate("homeScreen/$userEmail")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Submit Application")
                }
            }

            Spacer(modifier = Modifier.height(360.dp))
            Text(
                text = "Having an issue with the application?",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            TextButton(
                onClick = {
                    navController.navigate(Screen.ContactUsScreen.name)
                }
            ) {
                Text("Tell us more", style = MaterialTheme.typography.bodyMedium, color = Color.Blue)
            }
        }
    }
}

@Composable
fun ResumeScreenContent(userEmail: String, apiService: ApiService, navController: NavHostController) {
    val hasResume = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var userId by remember { mutableStateOf<String?>(null) }
    var resumeExists by remember { mutableStateOf(false) }
    var resumeUrl by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(userEmail) {
        if (userEmail.isNotEmpty()) {
            isLoading = true
            errorMessage = null
            try {
                val userIdResponse = apiService.getuserid(userEmail)
                userId = userIdResponse.userId
                if (!userId.isNullOrBlank()) {
                    val resumeResponse = apiService.checkResumeExists(userId!!)
                    if (resumeResponse.isSuccessful) {
                        resumeResponse.body()?.let { resume ->
                            resumeExists = resume.success
                            resumeUrl = resume.filePath
                        }
                    } else {
                        resumeExists = false
                    }
                } else {
                    errorMessage = "User ID not found"
                }
            } catch (e: Exception) {
                Log.e("API Error", "Error fetching data: ${e.message}", e)
                errorMessage = "Error fetching data: ${e.message}"
                Toast.makeText(context, "Error fetching data: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                isLoading = false
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (resumeExists) {
            Text(
                text = "Resume Found",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color.Green
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Your resume is already uploaded. Would you like to update it?",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    navController.navigate("profileSection/$userEmail")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Update Resume")
            }
        } else {
            Text(
                text = "No Resume Found",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color.Red
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Please upload your resume to continue.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    navController.navigate("profileSection/$userEmail")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Upload Resume")
            }
        }
    }
}


@Composable
fun CoverLetterScreenContent() {
    Text(
        "Employer's Questions",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(8.dp))

    // Sample employer's questions
    val questions = listOf(
        "Why do you think you're a good fit for this role?",
        "What are your strengths and weaknesses?",
        "Where do you see yourself in 5 years?"
    )

    // Iterate over the list of questions to display them
    questions.forEachIndexed { index, question ->
        Text(
            text = question,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(8.dp))

        val answer = remember { mutableStateOf(TextFieldValue()) }
        OutlinedTextField(
            value = answer.value,
            onValueChange = { answer.value = it },
            placeholder = { Text("Write your answer here...") },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(8.dp),
            textStyle = MaterialTheme.typography.bodyLarge,
            maxLines = 10
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun ReviewAndSubmitScreenContent(userEmail: String, navController: NavHostController) {
    Text("Review your application before submitting.", style = MaterialTheme.typography.bodyMedium)
    Spacer(modifier = Modifier.height(8.dp))
    Text("Resume: Uploaded", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
    Text("Cover Letter: Completed", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
}

@Composable
fun JobApplicationScreenPreview(
    navController: NavHostController,
    jobid: String,
    apiService: ApiService,
    userEmail: String
) {
    JobApplicationScreen(
        jobid,
        apiService,
        userEmail,
        navController = navController
    )
}
