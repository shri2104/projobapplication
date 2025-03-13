package com.example.projobliveapp.Screens.profile

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import com.example.projobliveapp.DataBase.ApiService
import com.example.projobliveapp.DataBase.ContactInfo
import com.example.projobliveapp.DataBase.EducationDetails
import com.example.projobliveapp.DataBase.ExperienceDetails
import com.example.projobliveapp.DataBase.JobPreference
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
                val userIdResponse = apiService.getuserid(userEmail)
                val userId = userIdResponse.userId

                if (!userId.isNullOrBlank()) {
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
    val educationData = remember { mutableStateListOf<EducationDetails>() }
    val context = LocalContext.current

    LaunchedEffect(userEmail) {
        if (userEmail.isNotEmpty()) {
            isLoading = true
            errorMessage = null
            try {
                // Fetch userId and education data
                val userIdResponse = apiService.getuserid(userEmail)
                val userId = userIdResponse.userId
                if (userId != null) {
                    if (userId.isNotBlank()) {
                        val response = apiService.getCandidateeducationladata(userId)
                        if (response.isNotEmpty()) {
                            educationData.clear()
                            educationData.addAll(response)
                            Log.d("EducationDetails", "Fetched ${response.size} education records.")
                        } else {
                            errorMessage = "No education data found."
                            Log.d("EducationDetails", "No education data found for user: $userId")
                        }
                    } else {
                        errorMessage = "User ID not found."
                    }
                }
            } catch (e: Exception) {
                errorMessage = "Error fetching education data: ${e.message}"
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                Log.e("EducationDetails", "Error: ${e.message}", e)
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
            BottomAppBar(modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = { navController.navigate("homeScreen/$userEmail") }, modifier = Modifier.weight(1f)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Home, contentDescription = "Home")
                        Text("Home", style = MaterialTheme.typography.titleSmall)
                    }
                }
                IconButton(onClick = { }, modifier = Modifier.weight(1f)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Groups, contentDescription = "Internships")
                        Text("Internships", style = MaterialTheme.typography.titleSmall)
                    }
                }
                IconButton(onClick = { navController.navigate("AvailableJobs/$userEmail") }, modifier = Modifier.weight(1f)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Work, contentDescription = "Jobs")
                        Text("Jobs", style = MaterialTheme.typography.titleSmall)
                    }
                }
                IconButton(onClick = { }, modifier = Modifier.weight(1f)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Message, contentDescription = "Messages")
                        Text("Messages", style = MaterialTheme.typography.titleSmall)
                    }
                }
            }
        },
        content = { paddingValues ->
            if (isLoading) {
                CircularProgressIndicator()
            } else if (errorMessage != null) {
                Text(text = errorMessage ?: "Error", color = Color.Red)
            } else {
                LazyColumn(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
                    itemsIndexed(educationData) { index, education ->
                        // Record Title without Icon and in Bold
                        ProfileDetailWithElevation(
                            value = "Academic Background - Record ${index + 1}",
                            icon = null
                        )
                        // Other Details with Icon and Edit Option
                        ProfileDetailWithElevation(
                            value = education.degree ?: "No Degree",
                            icon = Icons.Default.Book,
                            onEditClick = { }
                        )
                        ProfileDetailWithElevation(
                            value = education.fieldOfStudy ?: "No Field of Study",
                            icon = Icons.Default.School,
                            onEditClick = { }
                        )
                        ProfileDetailWithElevation(
                            value = education.universityName ?: "No University Name",
                            icon = Icons.Default.AccountBalance,
                            onEditClick = { }
                        )
                        ProfileDetailWithElevation(
                            value = education.yearOfPassing?.toString() ?: "No Year of Passing",
                            icon = Icons.Default.DateRange,
                            onEditClick = { }
                        )
                        ProfileDetailWithElevation(
                            value = education.percentageOrCGPA ?: "No Percentage/CGPA",
                            icon = Icons.Default.Grade,
                            onEditClick = { }
                        )
                        ProfileDetailWithElevation(
                            value = education.certificationName ?: "No Certification",
                            icon = Icons.Default.CreditCard,
                            onEditClick = { }
                        )
                    }
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
    val experienceData = remember { mutableStateListOf<ExperienceDetails>() }
    val context = LocalContext.current

    LaunchedEffect(userEmail) {
        if (userEmail.isNotEmpty()) {
            isLoading = true
            errorMessage = null
            try {
                val userIdResponse = apiService.getuserid(userEmail)
                val userId = userIdResponse.userId
                if (userId != null) {
                    if (userId.isNotBlank()) {
                        val response = apiService.getCandidateexperienceladata(userId)
                        if (response.isNotEmpty()) {
                            experienceData.clear()
                            experienceData.addAll(response)
                            Log.d("ExperienceDetails", "Fetched ${response.size} experience records.")
                        } else {
                            errorMessage = "No experience data found."
                            Log.d("ExperienceDetails", "No experience data found for user: $userId")
                        }
                    } else {
                        errorMessage = "User ID not found."
                    }
                }
            } catch (e: Exception) {
                errorMessage = "Error fetching experience data: ${e.message}"
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                Log.e("ExperienceDetails", "Error: ${e.message}", e)
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
            BottomAppBar(modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = { navController.navigate("homeScreen/$userEmail") }, modifier = Modifier.weight(1f)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Home, contentDescription = "Home")
                        Text("Home", style = MaterialTheme.typography.titleSmall)
                    }
                }
                IconButton(onClick = { }, modifier = Modifier.weight(1f)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Groups, contentDescription = "Internships")
                        Text("Internships", style = MaterialTheme.typography.titleSmall)
                    }
                }
                IconButton(onClick = { navController.navigate("AvailableJobs/$userEmail") }, modifier = Modifier.weight(1f)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Work, contentDescription = "Jobs")
                        Text("Jobs", style = MaterialTheme.typography.titleSmall)
                    }
                }
                IconButton(onClick = { }, modifier = Modifier.weight(1f)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Message, contentDescription = "Messages")
                        Text("Messages", style = MaterialTheme.typography.titleSmall)
                    }
                }
            }
        },
        content = { paddingValues ->
            if (isLoading) {
                CircularProgressIndicator()
            } else if (errorMessage != null) {
                Text(text = errorMessage ?: "Error", color = Color.Red)
            } else {
                LazyColumn(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
                    itemsIndexed(experienceData) { index, experience ->
                        ProfileDetailWithElevation(
                            value = "Experience Background - Record ${index + 1}",
                            icon = null
                        )
                        ProfileDetailWithElevation(
                            value = experience.jobTitle ?: "No Job Title",
                            icon = Icons.Default.Work,
                            onEditClick = { }
                        )
                        ProfileDetailWithElevation(
                            value = experience.companyName ?: "No Company Name",
                            icon = Icons.Default.Business,
                            onEditClick = { }
                        )
                        ProfileDetailWithElevation(
                            value = experience.responsibilities ?: "No Responsibilities",
                            icon = Icons.Default.CalendarToday,
                            onEditClick = { }
                        )
                        ProfileDetailWithElevation(
                            value = experience.startDate ?: "No Start Date",
                            icon = Icons.Default.DateRange,
                            onEditClick = { }
                        )
                        ProfileDetailWithElevation(
                            value = experience.endDate ?: "No End Date",
                            icon = Icons.Default.DateRange,
                            onEditClick = { }
                        )
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobPreferences(
    navController: NavController,
    userEmail: String,
    apiService: ApiService
) {
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val jobPreferenceData = remember { mutableStateOf<JobPreference?>(null) }
    val context = LocalContext.current

    LaunchedEffect(userEmail) {
        if (userEmail.isNotEmpty()) {
            isLoading = true
            errorMessage = null
            try {
                val userIdResponse = apiService.getuserid(userEmail)
                val userId = userIdResponse.userId
                if (userId != null && userId.isNotBlank()) {
                    val response = apiService.getJobPreference(userId)
                    if (response.isSuccessful) {
                        jobPreferenceData.value = response.body()
                    } else {
                        errorMessage = "Failed to fetch job preferences."
                    }
                } else {
                    errorMessage = "User ID not found."
                }
            } catch (e: Exception) {
                errorMessage = "Error fetching job preferences: ${e.message}"
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            } finally {
                isLoading = false
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Job Preferences") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = { paddingValues ->
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(paddingValues))
            } else if (errorMessage != null) {
                Text(text = errorMessage ?: "Error", color = Color.Red)
            } else {
                jobPreferenceData.value?.let { preference ->
                    LazyColumn(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
                        item {
                            ProfileDetailWithElevation(
                                value = "Job Locations:",
                                onEditClick = null
                            )
                        }
                        items(preference.jobLocations) { location ->
                            ProfileDetailWithElevation(
                                value = location,
                                icon = Icons.Default.LocationOn
                            )
                        }
                        item {
                            ProfileDetailWithElevation(
                                value = "Selected Skills:",
                                onEditClick = null
                            )
                        }
                        items(preference.selectedSkills) { skill ->
                            ProfileDetailWithElevation(
                                value = skill,
                                icon = Icons.Default.Build
                            )
                        }
                    }
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
                    onClick = { navController.navigate("AvailableJobs/$userEmail") },
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
                        onEditClick = null
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
                        value = listOf(
                            contactInfoData?.currentAddress,
                            contactInfoData?.permanentAddress,
                            contactInfoData?.roadName,
                            contactInfoData?.areaName,
                            contactInfoData?.city,
                            contactInfoData?.state,
                            contactInfoData?.pincode
                        ).filterNotNull().joinToString(", "),
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
fun ProfileDetailWithElevation(value: String, icon: ImageVector? = null, onEditClick: (() -> Unit)? = null) {
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
            // Only display the icon if it's not null
            icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold  // Bold text for academic background
                ),
                modifier = Modifier.weight(1f)
            )
            // Show the edit icon only if onEditClick is not null
            onEditClick?.let {
                IconButton(onClick = it) {
                    Icon(
                        imageVector = Icons.Default.Create,
                        contentDescription = "Edit",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}
