package com.example.projobliveapp.Screens.Inputdata

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.projobliveapp.Component.EmailInput
import com.example.projobliveapp.DataBase.ApiService
import com.example.projobliveapp.DataBase.JobApplication
import com.example.projobliveapp.Navigation.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun JobApplicationForm(navController: NavController,apiService: ApiService) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Form Fields
    val firstName = rememberSaveable { mutableStateOf("") }
    val lastName = rememberSaveable { mutableStateOf("") }
    val email = rememberSaveable { mutableStateOf("") }
    val phoneNumber = rememberSaveable { mutableStateOf("") }
    val location = rememberSaveable { mutableStateOf("") }
    val skills = rememberSaveable { mutableStateOf("") }
    val about = rememberSaveable { mutableStateOf("") }
    val workExperience = rememberSaveable { mutableStateOf("") }
    val jobCity = rememberSaveable { mutableStateOf("") }
    val roleLooking = rememberSaveable { mutableStateOf("") }

    // Dropdown for location suggestions
    val locationSuggestions = listOf("New York", "Los Angeles", "Chicago", "San Francisco", "Boston")
    val filteredSuggestions = remember(location.value) {
        locationSuggestions.filter { it.contains(location.value, ignoreCase = true) }
    }
    var showSuggestions by remember { mutableStateOf(false) }

    // Validation Check
    val isFormValid = remember(
        firstName.value,
        lastName.value,
        email.value,
        phoneNumber.value,
        location.value,
        skills.value
    ) {
        firstName.value.isNotBlank() &&
                lastName.value.isNotBlank() &&
                email.value.endsWith("@gmail.com", ignoreCase = true) &&
                phoneNumber.value.isNotBlank() &&
                location.value.isNotBlank() &&
                skills.value.isNotBlank()
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            Text(
                text = "Profile",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            InputField(valueState = firstName, label = "First Name", isRequired = true, icon = Icons.Default.Person)
            InputField(valueState = lastName, label = "Last Name", isRequired = true, icon = Icons.Default.Person)

            EmailInput(
                emailState = email,
                labelId = "Email",
                modifier = Modifier.fillMaxWidth()
            )

            InputField(
                valueState = phoneNumber,
                label = "Phone Number",
                isRequired = true,
                isNumeric = true,
                icon = Icons.Default.Phone
            )

            // Location with dropdown suggestions
            Box(modifier = Modifier.fillMaxWidth()) {
                InputField(
                    valueState = location,
                    label = "Location",
                    isRequired = true,
                    icon = Icons.Default.LocationOn,
                    onFocusChanged = { showSuggestions = it }
                )
                DropdownMenu(
                    expanded = showSuggestions && filteredSuggestions.isNotEmpty(),
                    onDismissRequest = { showSuggestions = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    filteredSuggestions.forEach { suggestion ->
                        DropdownMenuItem(
                            text = { Text(suggestion) },
                            onClick = {
                                location.value = suggestion
                                showSuggestions = false
                            }
                        )
                    }
                }
            }

            InputField(valueState = skills, label = "Skills", isRequired = true, icon = Icons.Default.Build)
            InputField(valueState = about, label = "About (Optional)", icon = Icons.Default.Info)
            InputField(valueState = workExperience, label = "Work Experience (Optional)", icon = Icons.Default.Work)
            InputField(valueState = jobCity, label = "Looking Job in Which City (Optional)", icon = Icons.Default.LocationCity)
            InputField(valueState = roleLooking, label = "Role Looking (Optional)", icon = Icons.Default.Search)

            // Resume Upload Button
            Button(
                onClick = { /* TODO: Implement resume import logic */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(imageVector = Icons.Default.AttachFile, contentDescription = "Upload Resume")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Import Resume")
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Submit Button
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
                        resume = "" // Replace with appropriate resume value
                    )

                    coroutineScope.launch(Dispatchers.IO) {
                        try {
                            // Replace `apiService.storeUserData` with actual API call
                            val response = apiService.storeUserData(userData)
                            withContext(Dispatchers.Main) {
                                if (response.isSuccessful) {
                                    Toast.makeText(context, "User data added successfully!", Toast.LENGTH_SHORT).show()
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
                    .padding(top = 16.dp),
                enabled = isFormValid
            ) {
                Text("Submit")
            }
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
    icon: ImageVector,
    onFocusChanged: ((Boolean) -> Unit)? = null
) {
    val keyboardType = when {
        isEmail -> KeyboardType.Email
        isNumeric -> KeyboardType.Number
        else -> KeyboardType.Text
    }

    OutlinedTextField(
        value = valueState.value,
        onValueChange = { valueState.value = it },
        label = { Text("$label ${if (isRequired) "*" else ""}") },
        singleLine = true,
        leadingIcon = { Icon(imageVector = icon, contentDescription = "$label Icon") },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType, imeAction = ImeAction.Next),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .onFocusChanged { onFocusChanged?.invoke(it.isFocused) },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            cursorColor = MaterialTheme.colorScheme.primary
        )
    )
}



//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun JobApplicationFormPreview() {
//    JobApplicationForm(navController = NavController(LocalContext.current))
//}