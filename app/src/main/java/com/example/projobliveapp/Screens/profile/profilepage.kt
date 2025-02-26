package com.example.projobliveapp.Screens.profile

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.projobliveapp.DataBase.ApiService
import com.example.projobliveapp.DataBase.JobApplication
import com.example.projobliveapp.DataBase.PersonalData
import com.example.projobliveapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(
    navController: NavController,
    userEmail: String?,
    apiService: ApiService
) {
    var personalData by remember { mutableStateOf<PersonalData?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val screenBackgroundColor = MaterialTheme.colorScheme.background // Set screen background to surface variant color

    LaunchedEffect(userEmail) {
        if (!userEmail.isNullOrEmpty()) {
            isLoading = true
            errorMessage = null
            try {
                val userIdResponse = apiService.getuserid(userEmail)
                val userId = userIdResponse.userId

                if (!userId.isNullOrBlank()) {
                    personalData = apiService.getCandidatepersonaldata(userId)
                } else {
                    errorMessage = "User ID not found"
                }
            } catch (e: Exception) {
                Log.e("API Error", "Error fetching personal data: ${e.message}", e)
                errorMessage = "Error fetching personal data: ${e.message}"
                Toast.makeText(context, "Error fetching personal data: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                isLoading = false
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.projob_logo1_12fc55031a756ac453bf),
                            contentDescription = "Job Logo",
                            modifier = Modifier
                                .size(90.dp)
                                .padding(end = 8.dp)
                        )
                    }
                },
                actions = { ProfileDropdownMenu() }
            )
        },
                bottomBar = {
            BottomAppBar(
                modifier = Modifier.fillMaxWidth(),
            ) {
                IconButton(
                    onClick = { navController.navigate("homeScreen/$userEmail")},
                    modifier = Modifier.weight(1f)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Home, contentDescription = "Home",)
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
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(screenBackgroundColor),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                errorMessage?.let {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                } ?: run {
                    personalData?.let { data ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Profile",
                                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray),
                                textAlign = TextAlign.Center
                            )
                            Box(
                                contentAlignment = Alignment.BottomEnd
                            ) {
                                ProfileImage(profileImageUrl = data.Lastname) // Assuming 'resume' is the profile image URL
                                IconButton(
                                    onClick = { },
                                    modifier = Modifier
                                        .size(30.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.surface)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Edit Profile Image",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "${data.Firstname} ${data.Lastname}",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(16.dp))
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 16.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                            ) {
                                item {
                                    ProfileCard(navController,userEmail) // Display additional profile data if necessary
                                }
                            }
                        }
                    } ?: run {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(
                                text = "No personal data available.",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileImage(profileImageUrl: String?) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(100.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "Default Profile Icon",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape),
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}




@Composable
fun ProfileCard(navController: NavController,userEmail: String?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileDetailw(
                value = "Personal Information",
                onEditClick = {navController.navigate("candidatepersonalinfo/$userEmail")}
            )
            ProfileDetailw(
                value = "Educational Information",
                onEditClick = {navController.navigate("candidateeducationinfo/$userEmail") }
            )
            ProfileDetailw(
                value = "Experience Information",
                onEditClick = { navController.navigate("candidateexperienceinfo/$userEmail")}
            )
            ProfileDetailw(
                value = "Contact Information",
                onEditClick = {navController.navigate("candidatecontactinfo/$userEmail") }
            )

        }
    }
}


@Composable
fun ProfileDetailw(value: String, onEditClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .shadow(elevation = 4.dp, shape = MaterialTheme.shapes.medium),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface),
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = onEditClick) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Forward",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun ProfileDropdownMenu() {
    var expanded by remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Menu")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Settings") },
                onClick = {  }
            )
            DropdownMenuItem(
                text = { Text("Logout") },
                onClick = {  }
            )
        }
    }
}
