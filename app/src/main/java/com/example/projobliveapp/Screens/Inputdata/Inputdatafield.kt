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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.projobliveapp.DataBase.ApiService
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projobliveapp.DataBase.ContactInfo
import com.example.projobliveapp.DataBase.EducationDetails
import com.example.projobliveapp.DataBase.ExperienceDetails
import com.example.projobliveapp.DataBase.PersonalData

import com.example.projobliveapp.R
import kotlinx.coroutines.Dispatchers

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
    var degree by remember { mutableStateOf("") }
    var fieldOfStudy by remember { mutableStateOf("") }
    var university by remember { mutableStateOf("") }
    var yearOfPassing by remember { mutableStateOf("") }  // String input
    var percentageCgpa by remember { mutableStateOf("") }
    var certificationName by remember { mutableStateOf("") }
    var issuingAuthority by remember { mutableStateOf("") }
    var yearOfCompletion by remember { mutableStateOf("") }  // String input
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
                }
            }

            item { InputField(label = "Degree*", value = degree, onValueChange = { degree = it }, placeholder = "e.g. Bachelor of Technology") }
            item { InputField(label = "Field of Study*", value = fieldOfStudy, onValueChange = { fieldOfStudy = it }, placeholder = "e.g. Computer Science") }
            item { InputField(label = "University/Institution Name*", value = university, onValueChange = { university = it }, placeholder = "e.g. MIT") }
            item { InputField(label = "Year of Passing*", value = yearOfPassing, onValueChange = { yearOfPassing = it }, placeholder = "e.g. 2025") }
            item { InputField(label = "Percentage/CGPA*", value = percentageCgpa, onValueChange = { percentageCgpa = it }, placeholder = "e.g. 8.5 CGPA or 85%") }

            item {
                Text(
                    text = "Certifications (Optional)",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
                )
            }
            item { InputField(label = "Certification Name", value = certificationName, onValueChange = { certificationName = it }, placeholder = "e.g. AWS Certified Developer") }
            item { InputField(label = "Issuing Authority", value = issuingAuthority, onValueChange = { issuingAuthority = it }, placeholder = "e.g. Amazon Web Services") }
            item { InputField(label = "Year of Completion", value = yearOfCompletion, onValueChange = { yearOfCompletion = it }, placeholder = "e.g. 2023") }

            item {
                Button(
                    onClick = {
                        if (yearOfPassing.isNotEmpty() && yearOfCompletion.isNotEmpty()) {
                            val Educationdata = EducationDetails(
                                userId = userId,
                                degree = degree,
                                fieldOfStudy = fieldOfStudy,
                                universityName = university,
                                yearOfPassing = yearOfPassing,
                                percentageOrCGPA = percentageCgpa,
                                certificationName = certificationName,
                                issuingAuthority = issuingAuthority,
                                yearOfCompletion = yearOfCompletion
                            )
                            coroutineScope.launch(Dispatchers.IO) {
                                try {
                                    apiService.Candidateeducationladata(Educationdata)
                                } catch (e: Exception) {
                                    // Handle error
                                }
                            }
                            onNext()
                        } else {
                            Toast.makeText(context, "Please enter valid years.", Toast.LENGTH_SHORT).show()
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
    userId: String, apiService: ApiService, onNext: () -> Unit
) {
    var jobTitle by remember { mutableStateOf("") }
    var companyName by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var jobLocation by remember { mutableStateOf("") }
    var responsibilities by remember { mutableStateOf("") }
    var achievements by remember { mutableStateOf("") }

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
                }
            }

            item { InputField(label = "Job Title*", value = jobTitle, onValueChange = { jobTitle = it }, placeholder = "e.g. Software Engineer") }
            item { InputField(label = "Company Name*", value = companyName, onValueChange = { companyName = it }, placeholder = "e.g. Google") }
            item { InputField(label = "Start Date (Year)*", value = startDate, onValueChange = { startDate = it }, placeholder = "e.g. 2022") }
            item { InputField(label = "End Date (Year)*", value = endDate, onValueChange = { endDate = it }, placeholder = "e.g. 2024") }
            item { InputField(label = "Job Location*", value = jobLocation, onValueChange = { jobLocation = it }, placeholder = "e.g. New York, USA") }
            item { InputField(label = "Responsibilities*", value = responsibilities, onValueChange = { responsibilities = it }, placeholder = "e.g. Developed Android applications") }


            item {
                Text(
                    text = "Achievements (Optional)",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
                )
            }
            item { InputField(label = "Achievements", value = achievements, onValueChange = { achievements = it }, placeholder = "e.g. Won Hackathon 2025") }


            item {
                Button(
                    onClick = {
                        val experiencedata = ExperienceDetails(
                            userId = userId,
                            jobTitle = jobTitle,
                            companyName = companyName,
                            experience = "$startDate - $endDate",
                            startDate = startDate,
                            endDate = endDate,
                            jobLocation = jobLocation,
                            responsibilities = responsibilities,
                            achievements = achievements.ifEmpty { "N/A" }
                        )
                        coroutineScope.launch(Dispatchers.IO) {
                            try {
                                apiService.Candidateexperienceladata(experiencedata)
                            }
                            catch (e: Exception) {
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
fun ContactDetailsScreen(userId: String, apiService: ApiService, onNext: () -> Unit)
 {
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var alternatePhoneNumber by remember { mutableStateOf("") }
    var currentAddress by remember { mutableStateOf("") }
    var permanentAddress by remember { mutableStateOf("") }
    var linkedInProfile by remember { mutableStateOf("") }
    var portfolioWebsite by remember { mutableStateOf("") }

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
            item { InputField(label = "Current Address*", value = currentAddress, onValueChange = { currentAddress = it }, placeholder = "e.g. Flat No. 101, XYZ Apartment, New Delhi") }
            item { InputField(label = "Permanent Address (Optional)", value = permanentAddress, onValueChange = { permanentAddress = it }, placeholder = "e.g. Hometown address") }
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
                            portfolioWebsite = portfolioWebsite
                        )
                        coroutineScope.launch(Dispatchers.IO) {
                            try {
                                apiService.Candidatecontactladata(contactInfo)
                            } catch (e: Exception) {

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
