package com.example.projobliveapp.Screens.profile

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import com.example.projobliveapp.DataBase.ApiService
import com.example.projobliveapp.DataBase.ContactInfo
import com.example.projobliveapp.DataBase.EducationDetails
import com.example.projobliveapp.DataBase.ExperienceDetails
import com.example.projobliveapp.DataBase.JobPreference
import com.example.projobliveapp.DataBase.PersonalData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalInformationScreen(
    navController: NavController,
    userEmail: String,
    apiService: ApiService
) {
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var personalData by remember { mutableStateOf<PersonalData?>(null) }
    var isEditing by remember { mutableStateOf(false) } // Controls edit mode

    val context = LocalContext.current

    LaunchedEffect(userEmail) {
        if (userEmail.isNotEmpty()) {
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

    var editedData by remember { mutableStateOf(personalData) } // Holds edited data

    suspend fun saveUpdatedData() {
        if (personalData != null && editedData != null) {
            isLoading = true
            errorMessage = null
            try {
                val userId = personalData!!.userId
                apiService.updateCandidatepersonaldata(userId, editedData!!)
                personalData = editedData
                isEditing = false
                Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                errorMessage = "Update failed: ${e.message}"
                Toast.makeText(context, "Update failed: ${e.message}", Toast.LENGTH_SHORT).show()
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
                },
                actions = {
                    IconButton(onClick = { isEditing = true }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                }
            )
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
                } else if (personalData != null) {
                    ProfileDetailWithElevation(
                        value = "${personalData?.Firstname} ${personalData?.Lastname}",
                        icon = Icons.Default.Person,
                        onEditClick = { isEditing = true }
                    )
                    ProfileDetailWithElevation(
                        value = personalData?.dateOfBirth ?: "No Date of Birth",
                        icon = Icons.Default.DateRange,
                        onEditClick = { isEditing = true }
                    )
                    ProfileDetailWithElevation(
                        value = personalData?.gender ?: "No Gender",
                        icon = Icons.Default.PersonOutline,
                        onEditClick = { isEditing = true }
                    )
                    ProfileDetailWithElevation(
                        value = personalData?.nationality ?: "No Nationality",
                        icon = Icons.Default.Flag,
                        onEditClick = { isEditing = true }
                    )
                    ProfileDetailWithElevation(
                        value = personalData?.maritalStatus ?: "No Marital Status",
                        icon = Icons.Default.Home,
                        onEditClick = { isEditing = true }
                    )
                    ProfileDetailWithElevation(
                        value = personalData?.languagesKnown?.joinToString(", ") ?: "No Languages Known",
                        icon = Icons.Default.Language,
                        onEditClick = { isEditing = true }
                    )
                } else {
                    Text("No data available")
                }
            }
        }
    )

    val coroutineScope = rememberCoroutineScope()

    if (isEditing && personalData != null) {
        EditPersonalInfoDialog(
            personalData = personalData!!,
            onDismiss = { isEditing = false },
            onSave = { updatedData ->
                editedData = updatedData
                coroutineScope.launch {
                    saveUpdatedData()
                }
            }
        )
    }
}

@Composable
fun EditPersonalInfoDialog(
    personalData: PersonalData,
    onDismiss: () -> Unit,
    onSave: (PersonalData) -> Unit
) {
    var firstName by remember { mutableStateOf(personalData.Firstname) }
    var lastName by remember { mutableStateOf(personalData.Lastname) }
    var dateOfBirth by remember { mutableStateOf(personalData.dateOfBirth) }
    var gender by remember { mutableStateOf(personalData.gender) }
    var nationality by remember { mutableStateOf(personalData.nationality) }
    var maritalStatus by remember { mutableStateOf(personalData.maritalStatus) }
    var languagesKnown by remember { mutableStateOf(personalData.languagesKnown.joinToString(", ")) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Personal Information") },
        text = {
            Column {
                OutlinedTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text("First Name") }
                )
                OutlinedTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text("Last Name") }
                )
                OutlinedTextField(
                    value = dateOfBirth,
                    onValueChange = { dateOfBirth = it },
                    label = { Text("Date of Birth") }
                )
                OutlinedTextField(
                    value = gender,
                    onValueChange = { gender = it },
                    label = { Text("Gender") }
                )
                OutlinedTextField(
                    value = nationality,
                    onValueChange = { nationality = it },
                    label = { Text("Nationality") }
                )
                OutlinedTextField(
                    value = maritalStatus,
                    onValueChange = { maritalStatus = it },
                    label = { Text("Marital Status") }
                )
                OutlinedTextField(
                    value = languagesKnown,
                    onValueChange = { languagesKnown = it },
                    label = { Text("Languages Known (comma-separated)") }
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                onSave(
                    personalData.copy(
                        Firstname = firstName,
                        Lastname = lastName,
                        dateOfBirth = dateOfBirth,
                        gender = gender,
                        nationality = nationality,
                        maritalStatus = maritalStatus,
                        languagesKnown = languagesKnown.split(",").map { it.trim() }
                    )
                )
                onDismiss()
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
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
    var editingEducation by remember { mutableStateOf<EducationDetails?>(null) }
    val context = LocalContext.current

    LaunchedEffect(userEmail) {
        if (userEmail.isNotEmpty()) {
            isLoading = true
            errorMessage = null
            try {
                val userIdResponse = apiService.getuserid(userEmail)
                val userId = userIdResponse.userId
                if (!userId.isNullOrBlank()) {
                    val response = apiService.getCandidateeducationladata(userId)
                    educationData.clear()
                    educationData.addAll(response)
                } else {
                    errorMessage = "User ID not found."
                }
            } catch (e: Exception) {
                errorMessage = "Error fetching education data: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Education Details") }) },
        content = { paddingValues ->
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            } else if (errorMessage != null) {
                Text(text = errorMessage ?: "Error", color = Color.Red, modifier = Modifier.padding(16.dp))
            } else {
                LazyColumn(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
                    itemsIndexed(educationData) { index, education ->
                        Column(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                                .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                                .padding(16.dp)
                        ) {
                            ProfileDetailWithElevation(
                                value = "Academic Background - Record ${index + 1}",
                                icon = Icons.Default.Info
                            )
                            ProfileDetailWithElevation(
                                value = "Degree: ${education.degree}",
                                icon = Icons.Default.Book
                            )
                            ProfileDetailWithElevation(
                                value = "Field of Study: ${education.fieldOfStudy}",
                                icon = Icons.Default.School
                            )
                            ProfileDetailWithElevation(
                                value = "University Name: ${education.universityName}",
                                icon = Icons.Default.AccountBalance
                            )
                            ProfileDetailWithElevation(
                                value = "Year of Passing: ${education.yearOfPassing}",
                                icon = Icons.Default.DateRange
                            )
                            ProfileDetailWithElevation(
                                value = "Percentage/CGPA: ${education.percentageOrCGPA}",
                                icon = Icons.Default.Grade
                            )
                            ProfileDetailWithElevation(
                                value = "Certification: ${education.certificationName ?: "None"}",
                                icon = Icons.Default.Verified
                            )
                            ProfileDetailWithElevation(
                                value = "Issuing Authority: ${education.issuingAuthority ?: "None"}",
                                icon = Icons.Default.Assignment
                            )
                            ProfileDetailWithElevation(
                                value = "Year of Completion: ${education.yearOfCompletion ?: "None"}",
                                icon = Icons.Default.Event
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = { editingEducation = education }) {
                                Text("Edit")
                            }
                        }
                    }
                }
            }
        }
    )

    // Show Edit Dialog if an education record is selected
    editingEducation?.let { education ->
        EditEducationDialog(
            education = education,
            onDismiss = { editingEducation = null },
            onSave = { updatedEducation ->
                isLoading = true
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val response = apiService.updateCandidateEducation(education.id, updatedEducation)
                        if (response.isSuccessful) {
                            val index = educationData.indexOfFirst { it.id == education.id }
                            if (index != -1) {
                                educationData[index] = updatedEducation
                            }
                            editingEducation = null
                        } else {
                            errorMessage = "Failed to update education details"
                        }
                    } catch (e: Exception) {
                        errorMessage = "Update error: ${e.message}"
                    } finally {
                        isLoading = false
                    }
                }
            }
        )
    }
}

@Composable
fun EditEducationDialog(
    education: EducationDetails,
    onDismiss: () -> Unit,
    onSave: (EducationDetails) -> Unit
) {
    var degree by remember { mutableStateOf(education.degree ?: "") }
    var fieldOfStudy by remember { mutableStateOf(education.fieldOfStudy ?: "") }
    var university by remember { mutableStateOf(education.universityName ?: "") }
    var year by remember { mutableStateOf(education.yearOfPassing?.toString() ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Education Details") },
        text = {
            Column {
                OutlinedTextField(value = degree, onValueChange = { degree = it }, label = { Text("Degree") })
                OutlinedTextField(value = fieldOfStudy, onValueChange = { fieldOfStudy = it }, label = { Text("Field of Study") })
                OutlinedTextField(value = university, onValueChange = { university = it }, label = { Text("University") })
                OutlinedTextField(
                    value = year,
                    onValueChange = { year = it },
                    label = { Text("Year of Passing") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                val updatedEducation = education.copy(
                    degree = degree,
                    fieldOfStudy = fieldOfStudy,
                    universityName = university,
                    yearOfPassing = year.toIntOrNull().toString() // Ensure year is properly converted
                )
                onSave(updatedEducation)
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
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
    var isEditing by remember { mutableStateOf(false) }
    var selectedExperience by remember { mutableStateOf<ExperienceDetails?>(null) }

    LaunchedEffect(userEmail) {
        if (userEmail.isNotEmpty()) {
            isLoading = true
            errorMessage = null
            try {
                val userIdResponse = apiService.getuserid(userEmail)
                val userId = userIdResponse.userId
                if (!userId.isNullOrBlank()) {
                    val response = apiService.getCandidateexperienceladata(userId)
                    if (response.isNotEmpty()) {
                        experienceData.clear()
                        experienceData.addAll(response)
                    } else {
                        errorMessage = "No experience data found."
                    }
                } else {
                    errorMessage = "User ID not found."
                }
            } catch (e: Exception) {
                errorMessage = "Error fetching experience data: ${e.message}"
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            } finally {
                isLoading = false
            }
        }
    }

    suspend fun saveUpdatedExperience(updatedExperience: ExperienceDetails) {
        isLoading = true
        errorMessage = null
        try {
            apiService.updateCandidateexperienceladata(updatedExperience.id, updatedExperience)
            val index = experienceData.indexOfFirst { it.id == updatedExperience.id }
            if (index != -1) {
                experienceData[index] = updatedExperience
            }
            isEditing = false
            Toast.makeText(context, "Experience updated successfully", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            errorMessage = "Update failed: ${e.message}"
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        } finally {
            isLoading = false
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
        content = { paddingValues ->
            if (isLoading) {
                CircularProgressIndicator()
            } else if (errorMessage != null) {
                Text(text = errorMessage ?: "Error", color = Color.Red)
            } else {
                LazyColumn(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
                    itemsIndexed(experienceData) { _, experience ->
                        ProfileDetailWithElevation(
                            value = experience.jobTitle ?: "No Job Title",
                            icon = Icons.Default.Work,
                            onEditClick = {
                                selectedExperience = experience
                                isEditing = true
                            }
                        )
                        ProfileDetailWithElevation(
                            value = experience.companyName ?: "No Company Name",
                            icon = Icons.Default.Business,
                            onEditClick = {
                                selectedExperience = experience
                                isEditing = true
                            }
                        )
                        ProfileDetailWithElevation(
                            value = experience.responsibilities ?: "No Responsibilities",
                            icon = Icons.Default.CalendarToday,
                            onEditClick = {
                                selectedExperience = experience
                                isEditing = true
                            }
                        )
                        ProfileDetailWithElevation(
                            value = "${experience.startDate} - ${experience.endDate}",
                            icon = Icons.Default.DateRange,
                            onEditClick = {
                                selectedExperience = experience
                                isEditing = true
                            }
                        )
                    }
                }
            }
        }
    )

    val coroutineScope = rememberCoroutineScope()

    if (isEditing && selectedExperience != null) {
        EditExperienceDialog(
            experience = selectedExperience!!,
            onDismiss = { isEditing = false },
            onSave = { updatedExperience ->
                coroutineScope.launch {
                    saveUpdatedExperience(updatedExperience)
                }
            }
        )
    }
}

@Composable
fun EditExperienceDialog(
    experience: ExperienceDetails,
    onDismiss: () -> Unit,
    onSave: (ExperienceDetails) -> Unit
) {
    var jobTitle by remember { mutableStateOf(experience.jobTitle) }
    var companyName by remember { mutableStateOf(experience.companyName) }
    var responsibilities by remember { mutableStateOf(experience.responsibilities) }
    var startDate by remember { mutableStateOf(experience.startDate) }
    var endDate by remember { mutableStateOf(experience.endDate) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Experience") },
        text = {
            Column {
                OutlinedTextField(
                    value = jobTitle,
                    onValueChange = { jobTitle = it },
                    label = { Text("Job Title") }
                )
                OutlinedTextField(
                    value = companyName,
                    onValueChange = { companyName = it },
                    label = { Text("Company Name") }
                )
                OutlinedTextField(
                    value = responsibilities,
                    onValueChange = { responsibilities = it },
                    label = { Text("Responsibilities") }
                )
                OutlinedTextField(
                    value = startDate,
                    onValueChange = { startDate = it },
                    label = { Text("Start Date") }
                )
                OutlinedTextField(
                    value = endDate,
                    onValueChange = { endDate = it },
                    label = { Text("End Date") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(
                        experience.copy(
                            jobTitle = jobTitle,
                            companyName = companyName,
                            responsibilities = responsibilities,
                            startDate = startDate,
                            endDate = endDate
                        )
                    )
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) { Text("Cancel") }
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
    var isEditing by remember { mutableStateOf(false) }
    var editedData by remember { mutableStateOf(jobPreferenceData.value) }

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

    suspend fun saveUpdatedData() {
        if (jobPreferenceData.value != null && editedData != null) {
            isLoading = true
            errorMessage = null
            try {
                val userId = jobPreferenceData.value!!.userId
                apiService.updateJobPreference(userId, editedData!!)
                jobPreferenceData.value = editedData
                isEditing = false
                Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                errorMessage = "Update failed: ${e.message}"
                Toast.makeText(context, "Update failed: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                isLoading = false
            }
        }
    }

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Job Preferences") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { isEditing = true }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
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

    if (isEditing && jobPreferenceData.value != null) {
        EditJobPreferenceDialog(
            jobPreference = jobPreferenceData.value!!,
            onDismiss = { isEditing = false },
            onSave = { updatedData ->
                editedData = updatedData
                coroutineScope.launch {
                    saveUpdatedData()
                }
            }
        )
    }
}

@Composable
fun EditJobPreferenceDialog(
    jobPreference: JobPreference,
    onDismiss: () -> Unit,
    onSave: (JobPreference) -> Unit
) {
    var locations by remember { mutableStateOf(jobPreference.jobLocations.joinToString(", ")) }
    var skills by remember { mutableStateOf(jobPreference.selectedSkills.joinToString(", ")) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Job Preferences") },
        text = {
            Column {
                OutlinedTextField(
                    value = locations,
                    onValueChange = { locations = it },
                    label = { Text("Job Locations") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = skills,
                    onValueChange = { skills = it },
                    label = { Text("Skills") }
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                val updatedPreference = jobPreference.copy(
                    jobLocations = locations.split(", ").map { it.trim() },
                    selectedSkills = skills.split(", ").map { it.trim() }
                )
                onSave(updatedPreference)
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
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
    var showEditDialog by remember { mutableStateOf(false) }
    var fieldToEdit by remember { mutableStateOf("") }
    var newValue by remember { mutableStateOf("") }

    val context = LocalContext.current

    LaunchedEffect(userEmail) {
        if (userEmail.isNotEmpty()) {
            isLoading = true
            errorMessage = null
            try {
                val userIdResponse = apiService.getuserid(userEmail)
                val userId = userIdResponse.userId
                if (!userId.isNullOrBlank()) {
                    contactInfoData = apiService.getCandidatecontactladata(userId)
                } else {
                    errorMessage = "User ID not found"
                }
            } catch (e: Exception) {
                errorMessage = "Error fetching contact info: ${e.message}"
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            } finally {
                isLoading = false
            }
        }
    }

    fun updateContactInfo(fieldName: String, updatedValue: String) {
        contactInfoData?.let { existingData ->
            val updatedData = existingData.copy(
                email = if (fieldName == "Email") updatedValue else existingData.email,
                phoneNumber = if (fieldName == "Phone Number") updatedValue else existingData.phoneNumber,
                alternatePhoneNumber = if (fieldName == "Alternate Phone") updatedValue else existingData.alternatePhoneNumber,
                currentAddress = if (fieldName == "Current Address") updatedValue else existingData.currentAddress,
                permanentAddress = if (fieldName == "Permanent Address") updatedValue else existingData.permanentAddress,
                city = if (fieldName == "City") updatedValue else existingData.city,
                state = if (fieldName == "State") updatedValue else existingData.state,
                pincode = if (fieldName == "Pincode") updatedValue else existingData.pincode,
                linkedInProfile = if (fieldName == "LinkedIn Profile") updatedValue else existingData.linkedInProfile
            )
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = apiService.updateCandidatecontactladata(existingData.userId, updatedData)
                    if (response.isSuccessful) {
                        withContext(Dispatchers.Main) {
                            contactInfoData = updatedData
                            Toast.makeText(context, "Updated successfully", Toast.LENGTH_SHORT).show()
                            showEditDialog = false
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "Update failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
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
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                when {
                    isLoading -> CircularProgressIndicator()
                    errorMessage != null -> Text(text = errorMessage ?: "Error")
                    contactInfoData != null -> {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            contactInfoData?.let { info ->
                                items(listOf(
                                    "Email" to info.email,
                                    "Phone Number" to info.phoneNumber,
                                    "Alternate Phone" to info.alternatePhoneNumber,
                                    "Current Address" to info.currentAddress,
                                    "Permanent Address" to info.permanentAddress,
                                    "City" to info.city,
                                    "State" to info.state,
                                    "Pincode" to info.pincode,
                                    "LinkedIn Profile" to info.linkedInProfile
                                )) { (label, value) ->
                                    ProfileDetailWithElevation(value = value, icon = Icons.Default.Info) {
                                        fieldToEdit = label
                                        newValue = value
                                        showEditDialog = true
                                    }
                                }
                            }
                        }
                    }
                    else -> Text("No data available")
                }
            }
        }
    )

    if (showEditDialog) {
        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            title = { Text("Edit $fieldToEdit") },
            text = {
                OutlinedTextField(
                    value = newValue,
                    onValueChange = { newValue = it },
                    label = { Text(fieldToEdit) }
                )
            },
            confirmButton = {
                Button(onClick = { updateContactInfo(fieldToEdit, newValue) }) {
                    Text("Save")
                }
            },
            dismissButton = {
                Button(onClick = { showEditDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
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
