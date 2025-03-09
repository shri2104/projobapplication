package com.example.projobliveapp.Screens.profile

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.projobliveapp.DataBase.PersonalData

import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.InputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileSection(apiService: ApiService, userEmail: String, navController: NavHostController) {
    var personalData by remember { mutableStateOf<PersonalData?>(null) }
    var userId by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var resumeUri by remember { mutableStateOf<Uri?>(null) }
    var uploadInProgress by remember { mutableStateOf(false) }
    var uploadSuccess by remember { mutableStateOf<Boolean?>(null) }
    var resumeExists by remember { mutableStateOf(false) }
    var resumeUrl by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val resumeLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        resumeUri = uri
    }

    LaunchedEffect(userEmail) {
        if (userEmail.isNotEmpty()) {
            isLoading = true
            errorMessage = null
            try {
                val userIdResponse = apiService.getuserid(userEmail)
                userId = userIdResponse.userId

                if (!userId.isNullOrBlank()) {
                    personalData = apiService.getCandidatepersonaldata(userId!!)

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

    suspend fun uploadResume(context: Context) {
        if (resumeUri != null && userId != null) {
            uploadInProgress = true
            try {
                val userIdRequestBody = userId!!.toRequestBody("text/plain".toMediaTypeOrNull())
                val inputStream = context.contentResolver.openInputStream(resumeUri!!)
                val requestBody = inputStream?.let {
                    RequestBody.create("application/pdf".toMediaTypeOrNull(), it.readBytes())
                }
                if (requestBody != null) {
                    val resumePart = MultipartBody.Part.createFormData("resume", "resume.pdf", requestBody)
                    val response = apiService.uploadResume(resumePart, userIdRequestBody)

                    if (response.isSuccessful) {
                        uploadSuccess = true
                        resumeExists = true
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
        } else {
            uploadSuccess = false
        }
    }
    suspend fun updateResume(context: Context) {
        if (resumeUri != null && userId != null) {
            uploadInProgress = true
            try {
                val userIdRequestBody = userId!!.toRequestBody("text/plain".toMediaTypeOrNull())
                val inputStream = context.contentResolver.openInputStream(resumeUri!!)
                val requestBody = inputStream?.use {
                    it.readBytes().toRequestBody("application/pdf".toMediaTypeOrNull())
                }

                if (requestBody != null) {
                    val resumePart = MultipartBody.Part.createFormData("resume", "updated_resume.pdf", requestBody)
                    val response = apiService.updateResume(resumePart, userIdRequestBody)

                    if (response.isSuccessful) {
                        uploadSuccess = true
                        resumeExists = true
                        Toast.makeText(context, "Resume updated successfully!", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.e("UpdateResume", "Error: ${response.code()} - ${response.message()}")
                        uploadSuccess = false
                    }
                } else {
                    uploadSuccess = false
                }
            } catch (e: Exception) {
                Log.e("UpdateResume", "Exception: ${e.localizedMessage}")
                uploadSuccess = false
            } finally {
                uploadInProgress = false
            }
        } else {
            uploadSuccess = false
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
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF5CB85C))
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

                when {
                    isLoading -> CircularProgressIndicator(color = Color(0xFF5CB85C))
                    errorMessage != null -> Text(errorMessage!!, color = Color.Red)
                    else -> {
                        Text(
                            text = "Welcome, ${personalData?.Firstname ?: "User"}",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF388E3C)
                        )

                        if (resumeExists) {
                            Text(
                                text = "Your resume is already uploaded. You can download or update it below.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                            )

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Button(
                                    onClick = { navController.navigate("showresume/$userEmail") },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(4.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF388E3C))
                                ) {
                                    Text(text = "Download Resume", color = Color.White)
                                }

                                Button(
                                    onClick = { resumeLauncher.launch("application/pdf") },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(4.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5CB85C))
                                ) {
                                    Text(text = "Update Resume", color = Color.White)
                                }
                            }

                            if (resumeUri != null) {
                                Button(
                                    onClick = { scope.launch { updateResume(context) } },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF388E3C)),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(text = if (uploadInProgress) "Updating..." else "Submit Updated Resume", color = Color.White)
                                }
                            }
                        } else {
                            if (resumeUri == null) {
                                Button(
                                    onClick = { resumeLauncher.launch("application/pdf") },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5CB85C)),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(text = "Select Resume", color = Color.White)
                                }
                            } else {
                                Button(
                                    onClick = { scope.launch { uploadResume(context) } },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF388E3C)),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(text = if (uploadInProgress) "Uploading..." else "Submit Resume", color = Color.White)
                                }
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
            }
        }
    )
}
