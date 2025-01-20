package com.example.projobliveapp.Screens.profile

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.projobliveapp.DataBase.ApiService
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.InputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileSection(apiService: ApiService, userEmail: String, navController: NavHostController) {
    var userFirstName by remember { mutableStateOf("") }
    var resumeUri by remember { mutableStateOf<Uri?>(null) }
    var uploadInProgress by remember { mutableStateOf(false) }
    var uploadSuccess by remember { mutableStateOf<Boolean?>(null) }
    val resumeLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        resumeUri = uri
    }
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val profileData = apiService.getProfileDataByEmail(userEmail)
                userFirstName = profileData.firstName
            } catch (e: Exception) {
                userFirstName = "User"
            }
        }
    }
    fun getInputStreamFromUri(context: Context, uri: Uri): InputStream? {
        return try {
            context.contentResolver.openInputStream(uri)
        } catch (e: Exception) {
            null
        }
    }
    suspend fun uploadResume(context: Context) {

        if (resumeUri != null) {
            uploadInProgress = true
            try {
                val emailPart = MultipartBody.Part.createFormData("email", userEmail)
                val inputStream = getInputStreamFromUri(context = context, uri = resumeUri!!)
                val requestBody = inputStream?.let {
                    RequestBody.create("application/pdf".toMediaTypeOrNull(), it.readBytes())
                }
                if (requestBody != null) {
                    val resumePart = MultipartBody.Part.createFormData("resume", "resume.pdf", requestBody)
                    val response = apiService.uploadResume(emailPart, resumePart)
                    if (response.isSuccessful) {
                        uploadSuccess = true
                    } else {
                        Log.e("UploadResume", "Error: ${response.code()} - ${response.message()}")
                        uploadSuccess = false
                    }
                } else {
                    uploadSuccess = false
                }
            } catch (e: Exception) {
                Log.e("UploadResume", "Exception: ${e.localizedMessage}")
                uploadSuccess = false
            } finally {
                uploadInProgress = false
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Profile",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF5CB85C)
                )
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Display user name
                Text(
                    text = "Welcome, ${if (userFirstName.isNotEmpty()) userFirstName else "Loading..."}",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF388E3C)
                )
                // Upload instruction
                Text(
                    text = "Upload your resume to enhance your profile. Employers can view it to learn more about you.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                // Resume upload button
                Button(
                    onClick = { resumeLauncher.launch("application/pdf") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5CB85C)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Upload Resume", color = Color.White)
                }
                // Uploaded resume status
                Text(
                    text = if (resumeUri != null) "Resume Uploaded: ${resumeUri?.lastPathSegment}" else "No Resume Uploaded",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                val context = LocalContext.current
                if (resumeUri != null) {
                    Button(
                        onClick = {
                            scope.launch { uploadResume(context) }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF388E3C)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = if (uploadInProgress) "Uploading..." else "Submit Resume", color = Color.White)
                    }
                }
                if (uploadSuccess != null) {
                    Text(
                        text = if (uploadSuccess == true) "Resume uploaded successfully!" else "Upload failed. Please try again.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (uploadSuccess == true) Color.Green else Color.Red
                    )
                }
            }
        }
    )

}
