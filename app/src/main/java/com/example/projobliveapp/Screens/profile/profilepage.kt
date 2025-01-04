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
import com.example.projobliveapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(
    navController: NavController,
    userEmail: String?,
    apiService: ApiService
) {
    var jobData by remember { mutableStateOf<JobApplication?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val screenBackgroundColor = MaterialTheme.colorScheme.background // Set screen background to surface variant color

    LaunchedEffect(userEmail) {
        if (!userEmail.isNullOrEmpty()) {
            isLoading = true
            try {
                jobData = apiService.getProfileDataByEmail(userEmail)
            } catch (e: Exception) {
                Log.e("API Error", "Error fetching job application: ${e.message}", e)
                Toast.makeText(context, "Error fetching job application: ${e.message}", Toast.LENGTH_SHORT).show()
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
                                .size(80.dp)
                                .padding(end = 8.dp)
                        )
                    }
                },
                actions = { ProfileDropdownMenu() }
            )
        }
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
                jobData?.let { job ->
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
                            ProfileImage(profileImageUrl = job.resume)
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
                            text = "${job.firstName} ${job.lastName}",
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
                                ProfileCard(job)
                            }
                        }
                    }
                } ?: run {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "No job application data available.",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun ProfileImage(profileImageUrl: String?) {
    if (!profileImageUrl.isNullOrEmpty()) {
        Image(
            painter = rememberImagePainter(data = profileImageUrl),
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )
    } else {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Default Profile Icon",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape),
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}

@Composable
fun ProfileCard(job: JobApplication) {
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
            ProfileDetailWithElevation(
                value = job.firstName.takeIf { it.isNotBlank() } ?: "First Name",
                icon = Icons.Default.Person,
                onEditClick = { }
            )
            ProfileDetailWithElevation(
                value = job.lastName.takeIf { it.isNotBlank() } ?: "Last Name",
                icon = Icons.Default.Person,
                onEditClick = { }
            )
            ProfileDetailWithElevation(
                value = job.email.takeIf { it.isNotBlank() } ?: "Email",
                icon = Icons.Default.Email,
                onEditClick = { }
            )
            ProfileDetailWithElevation(
                value = job.phoneNumber.takeIf { it.isNotBlank() } ?: "Phone Number",
                icon = Icons.Default.Phone,
                onEditClick = { }
            )
            ProfileDetailWithElevation(
                value = job.location.takeIf { it.isNotBlank() } ?: "Location",
                icon = Icons.Default.Place,
                onEditClick = { }
            )
            ProfileDetailWithElevation(
                value = job.skills.takeIf { it.isNotBlank() } ?: "Skills",
                icon = Icons.Default.Build,
                onEditClick = { }
            )
            ProfileDetailWithElevation(
                value = job.about.takeIf { it?.isNotBlank() == true } ?: "About",
                icon = Icons.Default.Info,
                onEditClick = { }
            )
            ProfileDetailWithElevation(
                value = job.workExperience.takeIf { it?.isNotBlank() == true } ?: "Work Experience",
                icon = Icons.Default.Work,
                onEditClick = { }
            )
            ProfileDetailWithElevation(
                value = job.jobCity.takeIf { it?.isNotBlank() == true } ?: "Preferred Job City",
                icon = Icons.Default.LocationCity,
                onEditClick = { }
            )
            ProfileDetailWithElevation(
                value = job.roleLooking.takeIf { it?.isNotBlank() == true } ?: "Role Looking For",
                icon = Icons.Default.Search,
                onEditClick = { }
            )
        }
    }
}

@Composable
fun ProfileDetailWithElevation(value: String, icon: ImageVector, onEditClick: () -> Unit) {
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
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface),
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = onEditClick) {
                Icon(
                    imageVector = Icons.Default.Create,
                    contentDescription = "Edit",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
@Composable
fun ProfileDetailWithoutLabel(value: String, icon: ImageVector, onEditClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(MaterialTheme.colorScheme.surface, shape = MaterialTheme.shapes.medium)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface),
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = onEditClick) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit",
                tint = MaterialTheme.colorScheme.primary
            )
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
