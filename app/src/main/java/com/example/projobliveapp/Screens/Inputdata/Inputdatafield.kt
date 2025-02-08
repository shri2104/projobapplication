package com.example.projobliveapp.Screens.Inputdata

import android.net.Uri
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
import androidx.compose.material.icons.filled.Work
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



import android.widget.Toast
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.navigation.NavController
import com.example.projobliveapp.DataBase.ApiService
import com.example.projobliveapp.DataBase.JobApplication
import com.example.projobliveapp.Navigation.Screen
import kotlinx.coroutines.Dispatchers
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavHostController
import com.example.projobliveapp.DataBase.ContactInfo
import com.example.projobliveapp.DataBase.EducationDetails
import com.example.projobliveapp.DataBase.ExperienceDetails
import com.example.projobliveapp.DataBase.PersonalData
import com.example.projobliveapp.R
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobApplicationForm(navController: NavController, apiService: ApiService, onNext: () -> Unit) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val firstName = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val phoneNumber = remember { mutableStateOf("") }
    val location = remember { mutableStateOf("") }
    val skills = remember { mutableStateOf("") }
    val about = remember { mutableStateOf("Write about yourself here...") }
    val workExperience = remember { mutableStateOf("") }
    val jobCity = remember { mutableStateOf("") }
    val roleLooking = remember { mutableStateOf("") }

    val isFormValid = firstName.value.isNotBlank() &&
            lastName.value.isNotBlank() &&
            email.value.isNotBlank() &&
            phoneNumber.value.length == 10 &&
            location.value.isNotBlank() &&
            skills.value.isNotBlank() &&
            email.value.contains("@")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TopAppBar(
            title = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.projob_logo1_12fc55031a756ac453bf), // Replace with your logo resource
                        contentDescription = "App Logo",
                        modifier = Modifier.size(82.dp)
                    )
                }
            }
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Registration",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            InputField(valueState = firstName, label = "First Name", isRequired = true, icon = Icons.Filled.Person)
            InputField(valueState = lastName, label = "Last Name", isRequired = true, icon = Icons.Filled.Person)
            InputField(valueState = email, label = "Email", isRequired = true, isEmail = true, icon = Icons.Filled.Email)
            InputField(valueState = phoneNumber, label = "Phone Number", isRequired = true, isNumeric = true, icon = Icons.Filled.Phone)
            InputField(valueState = location, label = "Location", isRequired = true, icon = Icons.Filled.Place)
            InputField(valueState = skills, label = "Skills", isRequired = true, icon = Icons.Filled.Build)
            InputField(valueState = workExperience, label = "Work Experience", icon = Icons.Filled.Work)
            InputField(valueState = jobCity, label = "Preferred Job City", icon = Icons.Filled.LocationCity)
            InputField(valueState = roleLooking, label = "Role You're Looking For", icon = Icons.Filled.Search)
            TextField(
                value = about.value,
                onValueChange = { about.value = it },
                label = { Text("About You") },
                placeholder = { Text("Write about yourself here...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 120.dp)
                    .padding(vertical = 8.dp),
                maxLines = 5, // Restrict to 5 lines
                singleLine = false,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent, // Transparent background when focused
                    unfocusedContainerColor = Color.Transparent, // Transparent background when unfocused
                    disabledContainerColor = Color.Transparent, // Transparent background when disabled
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary, // Primary color when focused
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f), // Subtle border when unfocused
                    focusedLabelColor = MaterialTheme.colorScheme.primary, // Label color when focused
                    unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f), // Label color when unfocused
                    cursorColor = MaterialTheme.colorScheme.primary // Cursor color
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
            ) {
                TextButton(
                    onClick = {
                        navController.navigate(Screen.LoginScreen.name)
                    }
                ) {
                    Text(
                        text = "Already Registered? Log In",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                val userData = JobApplication(
                    firstName = firstName.value,
                    lastName = lastName.value,
                    email = email.value,
                    phoneNumber = phoneNumber.value,
                    location = location.value,
                    skills = skills.value,
                    about = about.value,
                    workExperience = workExperience.value,
                    jobCity = jobCity.value,
                    roleLooking = roleLooking.value,
                    resume = ""
                )
                coroutineScope.launch(Dispatchers.IO) {
                    try {
                        val response = apiService.storeUserData(userData)
                        withContext(Dispatchers.Main) {
                            if (response.isSuccessful) {
                                Toast.makeText(context, "User data added successfully!", Toast.LENGTH_SHORT).show()
                                onNext()
                            } else {
                                Toast.makeText(context, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            enabled = isFormValid
        ) {
            Text("Next")
        }
    }
}

@Composable
fun InputField(
    valueState: MutableState<String>,
    label: String,
    isRequired: Boolean = false,
    isEmail: Boolean = false,
    isNumeric: Boolean = false,
    icon: ImageVector
) {
    val keyboardType = when {
        isEmail -> KeyboardType.Email
        isNumeric -> KeyboardType.Number
        else -> KeyboardType.Text
    }

    OutlinedTextField(
        value = valueState.value,
        onValueChange = { valueState.value = it },
        label = { Text(text = "$label ${if (isRequired) "*" else ""}") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType, imeAction = ImeAction.Next)
    )
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
fun PersonalDetailsScreen(navController: NavController) {

    var Firstname by remember { mutableStateOf("") }
    var Lastname by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    val Gender = remember { mutableStateOf("Male") }
    val Nationality = remember { mutableStateOf("Indian") }
    val MaritalStatus = remember { mutableStateOf("Single") }
    val languagesKnown = remember { mutableStateOf(listOf("English")) }
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
                    val personalData = PersonalData(
                        Firstname = Firstname,
                        Lastname=Lastname,
                        dateOfBirth = dob,
                        gender = Gender.value,
                        nationality = Nationality.value,
                        maritalStatus = MaritalStatus.value,
                        languagesKnown = languagesKnown.value
                    )
                    val PersonalData = Uri.encode(Gson().toJson(personalData))
                    navController.navigate("jobDetailsScreen/$PersonalData")
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
    Personaldata: PersonalData,
    apiService: ApiService,
    navController: NavHostController
) {
    val context = LocalContext.current
    var degree by remember { mutableStateOf("") }
    var fieldOfStudy by remember { mutableStateOf("") }
    var university by remember { mutableStateOf("") }
    var yearOfPassing by remember { mutableStateOf("") }  // String input, will convert to Int
    var percentageCgpa by remember { mutableStateOf("") }
    var certificationName by remember { mutableStateOf("") }
    var issuingAuthority by remember { mutableStateOf("") }
    var yearOfCompletion by remember { mutableStateOf("") }  // String input, will convert to Int

    // Function to convert string to int safely
    fun parseYear(value: String): Int? {
        return value.toIntOrNull()
    }

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
                        val yearOfPassingInt = parseYear(yearOfPassing)
                        val yearOfCompletionInt = parseYear(yearOfCompletion)
                        if (yearOfPassingInt != null && yearOfCompletionInt != null) {
                            val Educationdata = EducationDetails(
                                degree = degree,
                                fieldOfStudy = fieldOfStudy,
                                universityName = university,
                                yearOfPassing = yearOfPassingInt,
                                percentageOrCGPA = percentageCgpa,
                                certificationName = certificationName,
                                issuingAuthority = issuingAuthority,
                                yearOfCompletion = yearOfCompletionInt
                            )
                            val educationdataJson = Uri.encode(Gson().toJson(Educationdata))
                            navController.navigate("jobDetailsScreen/$educationdataJson/$Personaldata")
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
    personalData: PersonalData,
    educationdata: EducationDetails,
    apiService: ApiService,
    navController: NavHostController
) {
    var jobTitle by remember { mutableStateOf("") }
    var companyName by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var jobLocation by remember { mutableStateOf("") }
    var responsibilities by remember { mutableStateOf("") }
    var achievements by remember { mutableStateOf("") }

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
                        val experienceData = ExperienceDetails(
                            jobTitle = jobTitle,
                            companyName = companyName,
                            experience = "",
                            startDate = startDate,
                            endDate = endDate,
                            jobLocation = jobLocation,
                            responsibilities = responsibilities,
                            achievements = achievements
                        )
                        val experienceDataJson = Uri.encode(Gson().toJson(experienceData))
                        navController.navigate("nextScreen/$experienceDataJson$educationdata$personalData")
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
fun ContactDetailsScreen(
    experienceDetails: ExperienceDetails,
    educationdata: EducationDetails,
    personaldata: PersonalData,
    apiService: ApiService,
    navController: NavHostController
) {
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var alternatePhoneNumber by remember { mutableStateOf("") }
    var currentAddress by remember { mutableStateOf("") }
    var permanentAddress by remember { mutableStateOf("") }
    var linkedInProfile by remember { mutableStateOf("") }
    var portfolioWebsite by remember { mutableStateOf("") }

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

            item { InputField(label = "Email Address*", value = email, onValueChange = { if (android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()) email = it }, placeholder = "e.g.john.doe@example.com") }
            item { InputField(label = "Phone Number*", value = phoneNumber, onValueChange = { if (it.length in 10..15) phoneNumber = it }, placeholder = "e.g. +91-9876543210") }
            item { InputField(label = "Alternate Phone Number (Optional)", value = alternatePhoneNumber, onValueChange = { if (it.length in 10..15 && it != phoneNumber) alternatePhoneNumber = it }, placeholder = "e.g. +91-9123456789") }
            item { InputField(label = "Current Address*", value = currentAddress, onValueChange = { currentAddress = it }, placeholder = "e.g. Flat No. 101, XYZ Apartment, New Delhi") }
            item { InputField(label = "Permanent Address (Optional)", value = permanentAddress, onValueChange = { permanentAddress = it }, placeholder = "e.g. Hometown address") }
            item { InputField(label = "LinkedIn Profile (Optional)", value = linkedInProfile, onValueChange = { linkedInProfile = it }, placeholder = "e.g. linkedin.com/in/johndoe") }
            item { InputField(label = "Portfolio/Website (Optional)", value = portfolioWebsite, onValueChange = { portfolioWebsite = it }, placeholder = "e.g. johndoeportfolio.com") }
            item {
                Button(
                    onClick ={
                        val contactInfo = ContactInfo(
                            email = email,
                            phoneNumber = phoneNumber,
                            alternatePhoneNumber = alternatePhoneNumber,
                            currentAddress = currentAddress,
                            permanentAddress = permanentAddress,
                            linkedInProfile = linkedInProfile,
                            portfolioWebsite = portfolioWebsite
                        )
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


