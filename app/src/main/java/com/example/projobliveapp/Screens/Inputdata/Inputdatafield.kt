package com.example.projobliveapp.Screens.Inputdata

import android.widget.Toast
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projobliveapp.DataBase.ApiService
import com.example.projobliveapp.DataBase.JobApplication
import com.example.projobliveapp.Navigation.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.compose.foundation.Image
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import com.example.projobliveapp.R

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

