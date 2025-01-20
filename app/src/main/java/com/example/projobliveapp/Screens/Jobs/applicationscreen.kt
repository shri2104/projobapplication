package com.example.projobliveapp.Screens.Jobs

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.projobliveapp.Navigation.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobApplicationScreen(
    navController: NavHostController,
    jobTitle: String,
    companyName: String,
    location: String

) {
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
                text = jobTitle,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "$companyName - $location",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )
            TextButton(
                onClick = {
                    navController.navigate("jobDetailsScreen")
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
                1 -> ResumeScreenContent()
                2 -> CoverLetterScreenContent()
                3 -> ReviewAndSubmitScreenContent()
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
                        navController.navigate("applicationSubmittedScreen")
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
fun ResumeScreenContent() {
    val hasResume = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (hasResume.value) {
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
                    Toast.makeText(context, "Resume Update Clicked", Toast.LENGTH_SHORT).show()
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
                    coroutineScope.launch {
                        // Simulate the upload
                        Toast.makeText(context, "Resume Uploaded", Toast.LENGTH_SHORT).show()
                        hasResume.value = true
                    }
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
fun ReviewAndSubmitScreenContent() {
    Text("Review your application before submitting.", style = MaterialTheme.typography.bodyMedium)
    Spacer(modifier = Modifier.height(8.dp))
    Text("Resume: Uploaded", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
    Text("Cover Letter: Completed", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
}

@Composable
fun JobApplicationScreenPreview(navController: NavHostController) {
    JobApplicationScreen(
        navController = navController,
        jobTitle = "Junior Content Creator",
        companyName = "Swiggy",
        location = "New Delhi, India"
    )
}
