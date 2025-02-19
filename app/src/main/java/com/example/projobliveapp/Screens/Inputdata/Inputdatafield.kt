package com.example.projobliveapp.Screens.Inputdata

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.launch


import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.projobliveapp.DataBase.ApiService
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projobliveapp.DataBase.ContactInfo
import com.example.projobliveapp.DataBase.EducationDetails
import com.example.projobliveapp.DataBase.ExperienceDetails
import com.example.projobliveapp.DataBase.PersonalData

import com.example.projobliveapp.R
import com.example.projobliveapp.Screens.Menu.HelpAndSupportPage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun JobApplicationForm(
    navController: NavController,
    apiService: ApiService,
    userId: String,
    onComplete: () -> Unit
) {
    val navHostController = rememberNavController()


    NavHost(navController = navHostController, startDestination = "personalDetails") {
        composable("personalDetails") {
            PersonalDetailsScreen(userId, apiService) { navHostController.navigate("educationDetails") }
        }
        composable("educationDetails") {
            EducationDetailsScreen(userId, apiService) { navHostController.navigate("experienceDetails") }
        }
        composable("experienceDetails") {
            ExperienceDetailsScreen(userId, apiService) { navHostController.navigate("contactDetails") }
        }
        composable("contactDetails") {
            ContactDetailsScreen(userId, apiService) {
                onComplete()
            }
        }

    }
}



@Composable
fun StepIndicator(isFilled: Boolean) {
    val color = if (isFilled) Color.Blue else Color.LightGray
    Box(
        modifier = Modifier
            .size(12.dp)
            .background(color = color, shape = CircleShape)
    )
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalDetailsScreen(userId: String, apiService: ApiService, onNext: () -> Unit) {

    var Firstname by remember { mutableStateOf("") }
    var Lastname by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    val Gender = remember { mutableStateOf("Male") }
    val Nationality = remember { mutableStateOf("Indian") }
    val MaritalStatus = remember { mutableStateOf("Single") }
    val languagesKnown = remember { mutableStateOf(listOf("English")) }

    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.projob_logo1_12fc55031a756ac453bf),
                            contentDescription = "App Logo",
                            modifier = Modifier.size(96.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back Button")
                    }
                },

                )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Personal Details",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Blue,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .align(Alignment.Center),
                            color = Color.Gray,
                            thickness = 2.dp
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            stepIndicator(true)
                            stepIndicator(false)
                            stepIndicator(false)
                            stepIndicator(false)
                        }
                    }
                }
            }
            item {
                Text(
                    text = "First Name*",
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    TextField(
                        value = Firstname,
                        onValueChange = { Firstname = it },
                        placeholder = { Text(text = "e.g., John Doe") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            cursorColor = Color.Blue,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        )
                    )
                }
            }
            item {
                Text(
                    text = "Last Name*",
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    TextField(
                        value = Lastname,
                        onValueChange = { Lastname = it },
                        placeholder = { Text(text = "e.g., John Doe") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            cursorColor = Color.Blue,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        )
                    )
                }
            }
            item {
                Text(
                    text = "Date OF Birth*",
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    TextField(
                        value = dob,
                        onValueChange = { dob = it },
                        placeholder = { Text(text = "YYYY/MM/DD") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            cursorColor = Color.Blue,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        )
                    )
                }
            }
            item {
                Text(
                    text = "Gender*",
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                GenderSelector(selectedGender = Gender)
            }
            item {
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = "Country*",
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
               Nationality(selectedCountry = Nationality)
            }
            item {
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = "Marital Status*",
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                MaritalStatusSelector(selectedStatus = MaritalStatus)
            }
            item {
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = "Languages Known*",
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                LanguageSelector(selectedLanguages = languagesKnown)
            }
            item {
                Button(onClick = {
                    val PersonalData = PersonalData(
                        userId=userId,
                        Firstname = Firstname,
                        Lastname = Lastname,
                        dateOfBirth = dob,
                        gender = Gender.value,
                        nationality = Nationality.value,
                        maritalStatus = MaritalStatus.value,
                        languagesKnown = languagesKnown.value
                    )
                    coroutineScope.launch(Dispatchers.IO) {
                        try {
                            apiService.Candidatepersonaldata(PersonalData)
                        }
                        catch (e: Exception) {
                        }
                    }
                    onNext()

                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(Color.Blue)) {
                    Text(text = "Next", color = Color.White)
                }

            }

        }

    }
}
@Composable
fun stepIndicator(isFilled: Boolean) {
    val color = if (isFilled) Color.Blue else Color.LightGray
    Box(
        modifier = Modifier
            .size(12.dp)
            .background(color = color, shape = CircleShape)
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageSelector(selectedLanguages: MutableState<List<String>>) {
    var expanded by remember { mutableStateOf(false) }
    val languages = listOf("English", "Hindi", "Spanish", "French", "German", "Chinese", "Japanese", "Russian", "Arabic", "Portuguese")

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = selectedLanguages.value.joinToString(", "),
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown Arrow",
                        modifier = Modifier.clickable { expanded = !expanded }
                    )
                },
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.Blue,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
        }

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            languages.forEach { language ->
                val isSelected = language in selectedLanguages.value
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = isSelected,
                                onCheckedChange = { checked ->
                                    selectedLanguages.value = if (checked) {
                                        selectedLanguages.value + language
                                    } else {
                                        selectedLanguages.value - language
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(language)
                        }
                    },
                    onClick = {
                        selectedLanguages.value = if (isSelected) {
                            selectedLanguages.value - language
                        } else {
                            selectedLanguages.value + language
                        }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaritalStatusSelector(selectedStatus: MutableState<String>) {
    var expanded by remember { mutableStateOf(false) }
    val statuses = listOf("Single", "Married",)

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = selectedStatus.value,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown Arrow",
                        modifier = Modifier.clickable { expanded = !expanded }
                    )
                },
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.Blue,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
        }

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            statuses.forEach { status ->
                DropdownMenuItem(
                    text = { Text(status) },
                    onClick = {
                        selectedStatus.value = status
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Nationality(selectedCountry: MutableState<String>) {
    var expanded by remember { mutableStateOf(false) }
    val countries = listOf(
        "American", "Indian", "Canadian", "British", "Australian",
        "German", "French", "Italian", "Spanish", "Japanese",
        "Chinese", "Brazilian", "Mexican", "Russian", "South African",
        "Korean", "Dutch", "Swiss", "Swedish", "Norwegian"
    )


    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = selectedCountry.value,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown Arrow",
                        modifier = Modifier.clickable { expanded = !expanded }
                    )
                },
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.Blue,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
        }

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            countries.forEach { country ->
                DropdownMenuItem(
                    text = { Text(country) },
                    onClick = {
                        selectedCountry.value = country
                        expanded = false
                    }
                )
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenderSelector(selectedGender: MutableState<String>) {
    var expanded by remember { mutableStateOf(false) }
    val genders = listOf("Male", "Female", "Non-Binary", "Prefer not to say", "Other")

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = selectedGender.value,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown Arrow",
                        modifier = Modifier.clickable { expanded = !expanded }
                    )
                },
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.Blue,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
        }

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            genders.forEach { gender ->
                DropdownMenuItem(
                    text = { Text(gender) },
                    onClick = {
                        selectedGender.value = gender
                        expanded = false
                    }
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EducationDetailsScreen(
    userId: String, apiService: ApiService, onNext: () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var educationRecords by remember { mutableStateOf(listOf(EducationRecord("", "", "", "", "", ""))) }
    val educationLevels = listOf("Uneducated", "10th Pass", "12th Pass", "Diploma", "Degree", "Master’s")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Image(
                            painter = painterResource(id = R.drawable.projob_logo1_12fc55031a756ac453bf),
                            contentDescription = "App Logo",
                            modifier = Modifier.size(96.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { /* Handle Back Navigation */ }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back Button")
                    }
                },
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Education Details",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Blue,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .align(Alignment.Center),
                            color = Color.Gray,
                            thickness = 2.dp
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            stepIndicator(false)
                            stepIndicator(true)
                            stepIndicator(false)
                            stepIndicator(false)
                        }
                    }

                    educationRecords.forEachIndexed { index, educationRecord ->
                        EducationInputSection(
                            educationRecord = educationRecord,
                            educationLevels = educationLevels,
                            onEducationLevelChange = { level ->
                                educationRecords = educationRecords.toMutableList().apply {
                                    this[index] = this[index].copy(level = level)

                                    if (level == "Uneducated") {
                                        this[index] = this[index].copy(
                                            degree = "",
                                            fieldOfStudy = "",
                                            university = "",
                                            yearOfPassing = "",
                                            percentageOrCGPA = ""
                                        )
                                    }
                                }
                            },
                            onDegreeChange = { degree ->
                                educationRecords = educationRecords.toMutableList().apply {
                                    this[index] = this[index].copy(degree = degree)
                                }
                            },
                            onFieldOfStudyChange = { fieldOfStudy ->
                                educationRecords = educationRecords.toMutableList().apply {
                                    this[index] = this[index].copy(fieldOfStudy = fieldOfStudy)
                                }
                            },
                            onUniversityChange = { university ->
                                educationRecords = educationRecords.toMutableList().apply {
                                    this[index] = this[index].copy(university = university)
                                }
                            },
                            onYearOfPassingChange = { year ->
                                educationRecords = educationRecords.toMutableList().apply {
                                    this[index] = this[index].copy(yearOfPassing = year)
                                }
                            },
                            onPercentageOrCGPAChange = { cgpa ->
                                educationRecords = educationRecords.toMutableList().apply {
                                    this[index] = this[index].copy(percentageOrCGPA = cgpa)
                                }
                            }
                        )
                    }

                    Button(
                        onClick = {
                            val newRecord = EducationRecord("", "", "", "", "", "")
                            educationRecords = educationRecords + newRecord
                        },
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text(text = "Add More Education")
                    }

                    Button(
                        onClick = {
                            val isValid = educationRecords.all {
                                it.level == "Uneducated" || (it.degree.isNotEmpty() &&
                                        it.fieldOfStudy.isNotEmpty() &&
                                        it.university.isNotEmpty() &&
                                        it.yearOfPassing.isNotEmpty() &&
                                        it.percentageOrCGPA.isNotEmpty())
                            }

                            if (isValid) {
                                coroutineScope.launch(Dispatchers.IO) {
                                    try {
                                        educationRecords.forEach { record ->
                                            if (record.level != "Uneducated") {
                                                apiService.Candidateeducationladata(
                                                    EducationDetails(
                                                        userId = userId,
                                                        degree = record.degree,
                                                        fieldOfStudy = record.fieldOfStudy,
                                                        universityName = record.university,
                                                        yearOfPassing = record.yearOfPassing,
                                                        percentageOrCGPA = record.percentageOrCGPA,
                                                        certificationName = "",
                                                        issuingAuthority = "",
                                                        yearOfCompletion = ""
                                                    )
                                                )
                                            }
                                        }
                                    } catch (e: Exception) {
                                    }
                                }
                                onNext()
                            } else {
                                Toast.makeText(context, "Please fill all required fields.", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        colors = ButtonDefaults.buttonColors(Color.Blue)
                    ) {
                        Text(text = "Next", color = Color.White)
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EducationInputSection(
    educationRecord: EducationRecord,
    educationLevels: List<String>,
    onEducationLevelChange: (String) -> Unit,
    onDegreeChange: (String) -> Unit,
    onFieldOfStudyChange: (String) -> Unit,
    onUniversityChange: (String) -> Unit,
    onYearOfPassingChange: (String) -> Unit,
    onPercentageOrCGPAChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedEducationLevel by remember { mutableStateOf(educationRecord.level) }

    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    value = selectedEducationLevel,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Education Level*") },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Dropdown Arrow",
                            modifier = Modifier.clickable { expanded = !expanded }
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.Blue,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )
            }

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                educationLevels.forEach { level ->
                    DropdownMenuItem(
                        text = { Text(level) },
                        onClick = {
                            selectedEducationLevel = level
                            onEducationLevelChange(level)
                            expanded = false
                        }
                    )
                }
            }
        }

        if (selectedEducationLevel != "Uneducated") {
            InputField("Degree Name*", educationRecord.degree, onDegreeChange, "e.g. B.Sc., B.A., B.Tech.")
            InputField("Field of Study*", educationRecord.fieldOfStudy, onFieldOfStudyChange, "e.g. Computer Science")
            InputField("Institute/University Name*", educationRecord.university, onUniversityChange, "e.g. MIT")
            InputField("Year of Passing*", educationRecord.yearOfPassing, onYearOfPassingChange, "e.g. 2025")
            InputField("Percentage/CGPA*", educationRecord.percentageOrCGPA, onPercentageOrCGPAChange, "e.g. 85% or 9.2 CGPA")
        }
    }
}

data class EducationRecord(
    val level: String,
    val degree: String,
    val fieldOfStudy: String,
    val university: String,
    val yearOfPassing: String,
    val percentageOrCGPA: String  
)

@Composable
fun InputField(label: String, value: String, onValueChange: (String) -> Unit, placeholder: String) {
    Text(
        text = label,
        fontSize = 16.sp,
        color = Color.Black,
        modifier = Modifier.padding(bottom = 4.dp)
    )
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(text = placeholder) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                cursorColor = Color.Blue,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExperienceDetailsScreen(
    userId: String,
    apiService: ApiService,
    onNext: () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var experienceRecords by remember { mutableStateOf(listOf(ExperienceRecord("", "", "", "", ""))) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Image(
                            painter = painterResource(id = R.drawable.projob_logo1_12fc55031a756ac453bf),
                            contentDescription = "App Logo",
                            modifier = Modifier.size(96.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { /* Handle Back Navigation */ }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back Button")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Experience Details",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Blue,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .align(Alignment.Center),
                            color = Color.Gray,
                            thickness = 2.dp
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            stepIndicator(false)
                            stepIndicator(false)
                            stepIndicator(true)
                            stepIndicator(false)
                        }
                    }

                    experienceRecords.forEachIndexed { index, experienceRecord ->
                        ExperienceInputSection(
                            experienceRecord = experienceRecord,
                            onJobTitleChange = { jobTitle ->
                                experienceRecords = experienceRecords.toMutableList().apply {
                                    this[index] = this[index].copy(jobTitle = jobTitle)
                                }
                            },
                            onCompanyNameChange = { companyName ->
                                experienceRecords = experienceRecords.toMutableList().apply {
                                    this[index] = this[index].copy(companyName = companyName)
                                }
                            },
                            onStartDateChange = { startDate ->
                                experienceRecords = experienceRecords.toMutableList().apply {
                                    this[index] = this[index].copy(startDate = startDate)
                                }
                            },
                            onEndDateChange = { endDate ->
                                experienceRecords = experienceRecords.toMutableList().apply {
                                    this[index] = this[index].copy(endDate = endDate)
                                }
                            },
                            onResponsibilitiesChange = { responsibilities ->
                                experienceRecords = experienceRecords.toMutableList().apply {
                                    this[index] = this[index].copy(responsibilities = responsibilities)
                                }
                            },
                            onPresentChecked = { isChecked ->
                                experienceRecords = experienceRecords.toMutableList().apply {
                                    this[index] = this[index].copy(endDate = if (isChecked) "Present" else "")
                                }
                            }
                        )
                    }

                    if (experienceRecords.isNotEmpty()) {
                        Button(
                            onClick = {
                                val newRecord = ExperienceRecord("", "", "", "", "")
                                experienceRecords = experienceRecords + newRecord
                            },
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            Text(text = "Add More Experience")
                        }
                    }

                    Button(
                        onClick = {
                            val isValid = experienceRecords.all {
                                it.jobTitle.isNotEmpty() &&
                                        it.companyName.isNotEmpty() &&
                                        it.startDate.isNotEmpty() &&
                                        (it.endDate.isNotEmpty() || it.endDate == "Present")
                            }

                            if (isValid) {
                                coroutineScope.launch(Dispatchers.IO) {
                                    try {
                                        experienceRecords.forEach { record ->
                                            apiService.Candidateexperienceladata(
                                                ExperienceDetails(
                                                    userId = userId,
                                                    jobTitle = record.jobTitle,
                                                    companyName = record.companyName,
                                                    startDate = record.startDate,
                                                    endDate = record.endDate,
                                                    responsibilities = record.responsibilities
                                                )
                                            )
                                        }
                                    } catch (e: Exception) {
                                        withContext(Dispatchers.Main) {
                                            Toast.makeText(context, "Failed to save data.", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                                onNext()
                            } else {
                                Toast.makeText(context, "Please fill all required fields.", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        colors = ButtonDefaults.buttonColors(Color.Blue)
                    ) {
                        Text(text = "Next", color = Color.White)
                    }
                }
            }
        }
    }
}
@Composable
fun ExperienceInputSection(
    experienceRecord: ExperienceRecord,
    onJobTitleChange: (String) -> Unit,
    onCompanyNameChange: (String) -> Unit,
    onStartDateChange: (String) -> Unit,
    onEndDateChange: (String) -> Unit,
    onResponsibilitiesChange: (String) -> Unit,
    onPresentChecked: (Boolean) -> Unit
) {
    var isPresentChecked by remember { mutableStateOf(experienceRecord.endDate == "Present") }

    Column(modifier = Modifier.fillMaxWidth()) {
        InputField(
            label = "Job Title",
            value = experienceRecord.jobTitle,
            onValueChange = onJobTitleChange,
            placeholder = "Enter job title"
        )
        InputField(
            label = "Company Name",
            value = experienceRecord.companyName,
            onValueChange = onCompanyNameChange,
            placeholder = "Enter company name"
        )
        InputField(
            label = "Start Date",
            value = experienceRecord.startDate,
            onValueChange = onStartDateChange,
            placeholder = "Enter start date"
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Checkbox(
                checked = isPresentChecked,
                onCheckedChange = { checked ->
                    isPresentChecked = checked
                    onPresentChecked(checked)
                }
            )
            Text(text = "Currently Working Here")
        }

        if (!isPresentChecked) {
            InputField(
                label = "End Date",
                value = experienceRecord.endDate,
                onValueChange = onEndDateChange,
                placeholder = "Enter end date"
            )
        }

        InputField(
            label = "Responsibilities",
            value = experienceRecord.responsibilities,
            onValueChange = onResponsibilitiesChange,
            placeholder = "Enter responsibilities"
        )
    }
}


data class ExperienceRecord(
    val jobTitle: String,
    val companyName: String,
    val startDate: String,
    val endDate: String,
    val responsibilities: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Jobprefrence(userId: String, onNext: () -> Unit) {

    var jobLocations by remember { mutableStateOf(listOf<String>()) }
    var selectedLocation by remember { mutableStateOf("") }
    var selectedSkills by remember { mutableStateOf(listOf<String>()) }
    var customSkill by remember { mutableStateOf("") }
    var skillsList by remember { mutableStateOf(listOf<String>()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.projob_logo1_12fc55031a756ac453bf),
                            contentDescription = "App Logo",
                            modifier = Modifier.size(96.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back Button")
                    }
                },
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Job Preference",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Blue,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .align(Alignment.Center),
                            color = Color.Gray,
                            thickness = 2.dp
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            stepIndicator(true)
                            stepIndicator(false)
                            stepIndicator(false)
                            stepIndicator(false)
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Preferred Job Location",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                InputField(
                    label = "Job Location",
                    value = selectedLocation,
                    onValueChange = { selectedLocation = it },
                    placeholder = "Select a city"
                )

                Button(
                    onClick = {
                        if (selectedLocation.isNotEmpty() && !jobLocations.contains(selectedLocation)) {
                            jobLocations = jobLocations + selectedLocation
                            selectedLocation = ""
                        }
                    },
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text("Add More Locations")
                }

                jobLocations.forEach { location ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Text(text = location, fontSize = 14.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(onClick = { jobLocations = jobLocations - location }) {
                            Icon(Icons.Default.Close, contentDescription = "Remove Location")
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Skills Set",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                SkillSelector(selectedSkills = selectedSkills, onSkillSelected = {
                    if (!selectedSkills.contains(it)) {
                        selectedSkills = selectedSkills + it
                    }
                })

                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = customSkill,
                    onValueChange = { customSkill = it },
                    label = { Text("Enter custom skill") },
                    placeholder = { Text("Create Preview") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.Blue,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )
                Button(
                    onClick = {
                        if (customSkill.isNotEmpty() && !selectedSkills.contains(customSkill)) {
                            selectedSkills = selectedSkills + customSkill
                            customSkill = ""
                        }
                    },
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text("Add Custom Skill")
                }

                selectedSkills.forEach { skill ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Text(text = skill, fontSize = 14.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(onClick = { selectedSkills = selectedSkills - skill }) {
                            Icon(Icons.Default.Close, contentDescription = "Remove Skill")
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkillSelector(selectedSkills: List<String>, onSkillSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val skills = listOf("React.js", "Node.js", "Android Development", "Marketing", "UI/UX Design", "Data Science", "Machine Learning")

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = "Select Skills",
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.menuAnchor().fillMaxWidth(),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown Arrow",
                    modifier = Modifier.clickable { expanded = !expanded }
                )
            },
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                cursorColor = Color.Blue,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            skills.forEach { skill ->
                DropdownMenuItem(
                    text = { Text(skill) },
                    onClick = {
                        onSkillSelected(skill)
                    }
                )
            }
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDetailsScreen(userId: String, apiService: ApiService, onNext: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var alternatePhoneNumber by remember { mutableStateOf("") }
    var currentAddress by remember { mutableStateOf("") }
    var permanentAddress by remember { mutableStateOf("") }
    var linkedInProfile by remember { mutableStateOf("") }
    var portfolioWebsite by remember { mutableStateOf("") }

    var detailedAddress by remember { mutableStateOf("") }
    var roadName by remember { mutableStateOf("") }
    var areaName by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var pincode by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Image(
                            painter = painterResource(id = R.drawable.projob_logo1_12fc55031a756ac453bf),
                            contentDescription = "App Logo",
                            modifier = Modifier.size(96.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back Button")
                    }
                },
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Contact Details",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Blue,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .align(Alignment.Center),
                            color = Color.Gray,
                            thickness = 2.dp
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            stepIndicator(false)
                            stepIndicator(false)
                            stepIndicator(false)
                            stepIndicator(true)
                        }
                    }
                }
            }

            item { InputField(label = "Email Address*", value = email, onValueChange = { email = it }, placeholder = "e.g. john.doe@example.com") }
            item { InputField(label = "Phone Number*", value = phoneNumber, onValueChange = { phoneNumber = it }, placeholder = "e.g. +91-9876543210") }
            item { InputField(label = "Alternate Phone Number (Optional)", value = alternatePhoneNumber, onValueChange = { alternatePhoneNumber = it }, placeholder = "e.g. +91-9123456789") }
            item { InputField(label = "Detailed Address*", value = detailedAddress, onValueChange = { detailedAddress = it }, placeholder = "e.g. Flat No. 101, XYZ Apartment") }
            item { InputField(label = "Road Name*", value = roadName, onValueChange = { roadName = it }, placeholder = "e.g. MG Road") }
            item { InputField(label = "Area Name*", value = areaName, onValueChange = { areaName = it }, placeholder = "e.g. Koramangala") }
            item { InputField(label = "City*", value = city, onValueChange = { city = it }, placeholder = "e.g. Bangalore") }

            item {
                Column(modifier = Modifier.padding(bottom = 16.dp)) {
                    Text(
                        text = "State*",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    DropdownField(
                        label = "State*",
                        value = state,
                        onValueChange = { state = it },
                        options = listOf(
                            "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh", "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jharkhand",
                            "Karnataka", "Kerala", "Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Punjab", "Rajasthan",
                            "Sikkim", "Tamil Nadu", "Telangana", "Tripura", "Uttar Pradesh", "Uttarakhand", "West Bengal", "Andaman and Nicobar Islands",
                            "Chandigarh", "Dadra and Nagar Haveli and Daman and Diu", "Lakshadweep", "Delhi", "Puducherry"
                        )
                    )
                }
            }

            item { InputField(label = "Pincode*", value = pincode, onValueChange = { pincode = it }, placeholder = "e.g. 560001") }

            // Optional links
            item { InputField(label = "LinkedIn Profile (Optional)", value = linkedInProfile, onValueChange = { linkedInProfile = it }, placeholder = "e.g. linkedin.com/in/johndoe") }
            item { InputField(label = "Portfolio/Website (Optional)", value = portfolioWebsite, onValueChange = { portfolioWebsite = it }, placeholder = "e.g. johndoeportfolio.com") }

            item {
                Button(
                    onClick = {
                        val contactInfo = ContactInfo(
                            userId = userId,
                            email = email,
                            phoneNumber = phoneNumber,
                            alternatePhoneNumber = alternatePhoneNumber,
                            currentAddress = currentAddress,
                            permanentAddress = permanentAddress,
                            linkedInProfile = linkedInProfile,
                            portfolioWebsite = portfolioWebsite,
                            detailedAddress = detailedAddress,
                            roadName = roadName,
                            areaName = areaName,
                            city = city,
                            state = state,
                            pincode = pincode
                        )
                        coroutineScope.launch(Dispatchers.IO) {
                            try {
                                apiService.Candidatecontactladata(contactInfo)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                        onNext()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(Color.Blue)
                ) {
                    Text(text = "Next", color = Color.White)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    options: List<String>
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = value,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown Arrow",
                        modifier = Modifier.clickable { expanded = !expanded }
                    )
                },
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.Blue,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
        }

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onValueChange(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun JobPagePreview() {
    Jobprefrence(
        userId = "test_user",
        onNext = {}
    )
}
