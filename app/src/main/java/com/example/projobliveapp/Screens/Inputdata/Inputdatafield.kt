package com.example.projobliveapp.Screens.Inputdata

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.projobliveapp.Navigation.Screen

@Composable
fun JobApplicationForm(navController: NavHostController) {
    val firstName = rememberSaveable { mutableStateOf("") }
    val lastName = rememberSaveable { mutableStateOf("") }
    val email = rememberSaveable { mutableStateOf("") }
    val phoneNumber = rememberSaveable { mutableStateOf("") }
    val resume = rememberSaveable { mutableStateOf("") }
    val location = rememberSaveable { mutableStateOf("") }
    val skills = rememberSaveable { mutableStateOf("") }
    val about = rememberSaveable { mutableStateOf("") }
    val workExperience = rememberSaveable { mutableStateOf("") }
    val jobCity = rememberSaveable { mutableStateOf("") }
    val roleLooking = rememberSaveable { mutableStateOf("") }

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
                email.value.isNotBlank() &&
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
                text = "PROFILE",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            InputField(valueState = firstName, label = "First Name", isRequired = true, icon = Icons.Default.Person)
            InputField(valueState = lastName, label = "Last Name", isRequired = true, icon = Icons.Default.Person)
            InputField(valueState = email, label = "Email", isRequired = true, isEmail = true, icon = Icons.Default.Email)
            InputField(valueState = phoneNumber, label = "Phone Number", isRequired = true, isNumeric = true, icon = Icons.Default.Phone)
            InputField(valueState = location, label = "Location", isRequired = true, icon = Icons.Default.LocationOn)
            InputField(valueState = skills, label = "Skills", isRequired = true, icon = Icons.Default.Build)
            InputField(valueState = about, label = "About (Optional)", icon = Icons.Default.Info)
            InputField(valueState = workExperience, label = "Work Experience (Optional)", icon = Icons.Default.Work)
            InputField(valueState = jobCity, label = "Looking Job in Which City (Optional)", icon = Icons.Default.LocationCity)
            InputField(valueState = roleLooking, label = "Role Looking (Optional)", icon = Icons.Default.Search)
            InputField(valueState = resume, label = "Resume (Optional)", icon = Icons.Default.AttachFile)
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = { navController.navigate(Screen.HomeScreen.name) },
                enabled = isFormValid,
                colors = ButtonDefaults.buttonColors(
                    disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                    disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = CircleShape
            ) {
                Text(text = "Save", modifier = Modifier.padding(5.dp))
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
        label = { Text("$label ${if (isRequired) "*" else ""}") },
        singleLine = true,
        leadingIcon = { Icon(imageVector = icon, contentDescription = "$label Icon") },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType, imeAction = ImeAction.Next),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = TextFieldDefaults.colors(
           focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            focusedLabelColor =MaterialTheme.colorScheme.primary,
            cursorColor = MaterialTheme.colorScheme.primary
        )
    )
}

