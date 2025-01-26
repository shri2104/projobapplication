package com.example.projobliveapp.Screens.Login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import com.example.projobliveapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployerDetailsScreen(navController: NavController) {
    var companyName by remember { mutableStateOf(TextFieldValue()) }
    var companyAddress by remember { mutableStateOf(TextFieldValue()) }
    var registrationNumber by remember { mutableStateOf(TextFieldValue()) }
    var additionalDetails by remember { mutableStateOf(TextFieldValue()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Employer Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo
            Image(
                painter = painterResource(id = R.drawable.projob_logo1_12fc55031a756ac453bf), // Replace with your logo resource
                contentDescription = "App Logo",
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Form Fields
            OutlinedTextField(
                value = companyName,
                onValueChange = { companyName = it },
                label = { Text(text = "Company Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = companyAddress,
                onValueChange = { companyAddress = it },
                label = { Text(text = "Company Address") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = registrationNumber,
                onValueChange = { registrationNumber = it },
                label = { Text(text = "Registration Number") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = additionalDetails,
                onValueChange = { additionalDetails = it },
                label = { Text(text = "Additional Details (Optional)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Submit Button
            Button(
                onClick = {
                    // Handle form submission
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Submit")
            }
        }
    }
}
