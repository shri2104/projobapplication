package com.example.projobliveapp.Screens.profile

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.platform.LocalContext
import com.example.projobliveapp.DataBase.ApiService
import com.example.projobliveapp.DataBase.ContactInfo
import com.example.projobliveapp.DataBase.EducationDetails
import com.example.projobliveapp.DataBase.ExperienceDetails
import com.example.projobliveapp.DataBase.PersonalData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalInformationScreen(
    navController: NavController,
    userEmail: String,
    apiService: ApiService,) {
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var personalData by remember { mutableStateOf<PersonalData?>(null) }

    val context = LocalContext.current

    LaunchedEffect(userEmail) {
        if (!userEmail.isNullOrEmpty()) {
            isLoading = true
            errorMessage = null
            try {
                // Step 1: Fetch userId
                val userIdResponse = apiService.getuserid(userEmail)
                val userId = userIdResponse.userId

                if (!userId.isNullOrBlank()) {
                    // Step 2: Fetch personal data using userId
                    personalData = apiService.getCandidatepersonaldata(userId)
                } else {
                    errorMessage = "User ID not found"
                }
            } catch (e: Exception) {
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
                title = { Text("Personal Information") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
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
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isLoading) {
                    CircularProgressIndicator()
                } else if (errorMessage != null) {
                    Text(text = errorMessage ?: "Error",)
                } else if (personalData != null) {
                    ProfileDetailWithElevation(
                        value = "${personalData?.Firstname} ${personalData?.Lastname}",
                        icon = Icons.Default.Person,
                        onEditClick = { /* Handle Edit */ }
                    )
                    ProfileDetailWithElevation(
                        value = personalData?.dateOfBirth ?: "No Date of Birth",
                        icon = Icons.Default.DateRange,
                        onEditClick = { /* Handle Edit */ }
                    )
                    ProfileDetailWithElevation(
                        value = personalData?.gender ?: "No Gender",
                        icon = Icons.Default.PersonOutline,
                        onEditClick = { /* Handle Edit */ }
                    )
                    ProfileDetailWithElevation(
                        value = personalData?.nationality ?: "No Nationality",
                        icon = Icons.Default.Flag,
                        onEditClick = { /* Handle Edit */ }
                    )
                    ProfileDetailWithElevation(
                        value = personalData?.maritalStatus ?: "No Marital Status",
                        icon = Icons.Default.Home,
                        onEditClick = { /* Handle Edit */ }
                    )
                    ProfileDetailWithElevation(
                        value = personalData?.languagesKnown?.joinToString(", ") ?: "No Languages Known",
                        icon = Icons.Default.Language,
                        onEditClick = { /* Handle Edit */ }
                    )
                } else {
                    Text("No data available")
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EducationDetailsScreen(
    navController: NavController,
    userEmail: String,
    apiService: ApiService
) {
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var educationData by remember { mutableStateOf<EducationDetails?>(null) }

    val context = LocalContext.current

    LaunchedEffect(userEmail) {
        if (!userEmail.isNullOrEmpty()) {
            isLoading = true
            errorMessage = null
            try {
                // Fetch userId and education data
                val userIdResponse = apiService.getuserid(userEmail)
                val userId = userIdResponse.userId

                if (!userId.isNullOrBlank()) {
                    educationData = apiService.getCandidateeducationladata(userId)
                } else {
                    errorMessage = "User ID not found"
                }
            } catch (e: Exception) {
                errorMessage = "Error fetching education data: ${e.message}"
                Toast.makeText(context, "Error fetching education data: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                isLoading = false
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Education Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
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
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isLoading) {
                    CircularProgressIndicator()
                } else if (errorMessage != null) {
                    Text(text = errorMessage ?: "Error")
                } else if (educationData != null) {
                    ProfileDetailWithElevation(
                        value = educationData?.degree ?: "No Degree",
                        icon = Icons.Default.Book,
                        onEditClick = { /* Handle Edit */ }
                    )
                    ProfileDetailWithElevation(
                        value = educationData?.fieldOfStudy ?: "No Field of Study",
                        icon = Icons.Default.School,
                        onEditClick = { /* Handle Edit */ }
                    )
                    ProfileDetailWithElevation(
                        value = educationData?.universityName ?: "No University Name",
                        icon = Icons.Default.AccountBalance,
                        onEditClick = { /* Handle Edit */ }
                    )
                    ProfileDetailWithElevation(
                        value = educationData?.yearOfPassing.toString(),
                        icon = Icons.Default.DateRange,
                        onEditClick = { /* Handle Edit */ }
                    )
                    ProfileDetailWithElevation(
                        value = educationData?.percentageOrCGPA ?: "No Percentage/CGPA",
                        icon = Icons.Default.Grade,
                        onEditClick = { /* Handle Edit */ }
                    )
                    ProfileDetailWithElevation(
                        value = educationData?.certificationName ?: "No Certification",
                        icon = Icons.Default.CreditCard,
                        onEditClick = { /* Handle Edit */ }
                    )
                } else {
                    Text("No data available")
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExperienceDetailsScreen(
    navController: NavController,
    userEmail: String,
    apiService: ApiService
) {
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var experienceData by remember { mutableStateOf<ExperienceDetails?>(null) }

    val context = LocalContext.current

    LaunchedEffect(userEmail) {
        if (!userEmail.isNullOrEmpty()) {
            isLoading = true
            errorMessage = null
            try {
                // Fetch userId and experience data
                val userIdResponse = apiService.getuserid(userEmail)
                val userId = userIdResponse.userId

                if (!userId.isNullOrBlank()) {
                    experienceData = apiService.getCandidateexperienceladata(userId)
                } else {
                    errorMessage = "User ID not found"
                }
            } catch (e: Exception) {
                errorMessage = "Error fetching experience data: ${e.message}"
                Toast.makeText(context, "Error fetching experience data: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                isLoading = false
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Experience Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
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
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isLoading) {
                    CircularProgressIndicator()
                } else if (errorMessage != null) {
                    Text(text = errorMessage ?: "Error")
                } else if (experienceData != null) {
                    ProfileDetailWithElevation(
                        value = experienceData?.jobTitle ?: "No Job Title",
                        icon = Icons.Default.Work,
                        onEditClick = { /* Handle Edit */ }
                    )
                    ProfileDetailWithElevation(
                        value = experienceData?.companyName ?: "No Company Name",
                        icon = Icons.Default.Business,
                        onEditClick = { /* Handle Edit */ }
                    )
                    ProfileDetailWithElevation(
                        value = experienceData?.experience ?: "No Experience",
                        icon = Icons.Default.CalendarToday,
                        onEditClick = { /* Handle Edit */ }
                    )
                    ProfileDetailWithElevation(
                        value = experienceData?.startDate ?: "No Start Date",
                        icon = Icons.Default.DateRange,
                        onEditClick = { /* Handle Edit */ }
                    )
                    ProfileDetailWithElevation(
                        value = experienceData?.endDate ?: "No End Date",
                        icon = Icons.Default.DateRange,
                        onEditClick = { /* Handle Edit */ }
                    )
                } else {
                    Text("No data available")
                }
            }
        }
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactInfoScreen(
    navController: NavController,
    userEmail: String,
    apiService: ApiService
) {
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var contactInfoData by remember { mutableStateOf<ContactInfo?>(null) }

    val context = LocalContext.current

    LaunchedEffect(userEmail) {
        if (!userEmail.isNullOrEmpty()) {
            isLoading = true
            errorMessage = null
            try {
                // Fetch userId and contact information data
                val userIdResponse = apiService.getuserid(userEmail)
                val userId = userIdResponse.userId

                if (!userId.isNullOrBlank()) {
                    contactInfoData = apiService.getCandidatecontactladata(userId)
                } else {
                    errorMessage = "User ID not found"
                }
            } catch (e: Exception) {
                errorMessage = "Error fetching contact info: ${e.message}"
                Toast.makeText(context, "Error fetching contact info: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                isLoading = false
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Contact Information") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
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
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isLoading) {
                    CircularProgressIndicator()
                } else if (errorMessage != null) {
                    Text(text = errorMessage ?: "Error")
                } else if (contactInfoData != null) {
                    ProfileDetailWithElevation(
                        value = contactInfoData?.email ?: "No Email",
                        icon = Icons.Default.Email,
                        onEditClick = { /* Handle Edit */ }
                    )
                    ProfileDetailWithElevation(
                        value = contactInfoData?.phoneNumber ?: "No Phone Number",
                        icon = Icons.Default.Phone,
                        onEditClick = { /* Handle Edit */ }
                    )
                    ProfileDetailWithElevation(
                        value = contactInfoData?.alternatePhoneNumber ?: "No Alternate Phone Number",
                        icon = Icons.Default.Phone,
                        onEditClick = { /* Handle Edit */ }
                    )
                    ProfileDetailWithElevation(
                        value = contactInfoData?.currentAddress ?: "No Current Address",
                        icon = Icons.Default.Home,
                        onEditClick = { /* Handle Edit */ }
                    )
                    ProfileDetailWithElevation(
                        value = contactInfoData?.permanentAddress ?: "No Permanent Address",
                        icon = Icons.Default.Home,
                        onEditClick = { /* Handle Edit */ }
                    )
                    ProfileDetailWithElevation(
                        value = contactInfoData?.linkedInProfile ?: "No LinkedIn Profile",
                        icon = Icons.Default.Link,
                        onEditClick = { /* Handle Edit */ }
                    )
                    ProfileDetailWithElevation(
                        value = contactInfoData?.portfolioWebsite ?: "No Portfolio Website",
                        icon = Icons.Default.Web,
                        onEditClick = { /* Handle Edit */ }
                    )
                } else {
                    Text("No data available")
                }
            }
        }
    )
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

