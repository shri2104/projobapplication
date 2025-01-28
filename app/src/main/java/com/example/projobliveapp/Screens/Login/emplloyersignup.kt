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
import com.bawp.freader.screens.login.Signup
import com.example.projobliveapp.DataBase.ApiService
import com.example.projobliveapp.Navigation.Screen
import com.example.projobliveapp.R
import com.example.projobliveapp.Screens.Inputdata.JobApplicationForm

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployerDetailsScreen(
    navController: NavController,
    apiService: ApiService,
    userType: String
) {
    var companyName by remember { mutableStateOf(TextFieldValue()) }
    var companyAddress by remember { mutableStateOf(TextFieldValue()) }
    var registrationNumber by remember { mutableStateOf(TextFieldValue()) }
    var additionalDetails by remember { mutableStateOf(TextFieldValue()) }
    var showUserFor by remember { mutableStateOf(false) }

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
            Image(
                painter = painterResource(id = R.drawable.projob_logo1_12fc55031a756ac453bf), // Ensure this resource exists
                contentDescription = "App Logo",
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))
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
                    showUserFor = true
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Next")
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (showUserFor) {
                // Call Signup composable
                Signup(userType = userType, navController = navController)

                Spacer(modifier = Modifier.height(30.dp))

                Button(
                    onClick = {
                        if (userType == "Employer") {
                            navController.navigate(Screen.Signupscreen.name)
                        } else {
                            navController.navigate(Screen.EmployerSignUP.name)
                        }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                ) {
                    Text(text = "Already have an account? Log in")
                }
            }
        }
    }
}
