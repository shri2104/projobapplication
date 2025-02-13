package com.example.projobliveapp.Screens.Login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import com.bawp.freader.screens.login.Signup
import com.example.projobliveapp.DataBase.ApiService
import com.example.projobliveapp.Navigation.Screen
import com.example.projobliveapp.R
import com.example.projobliveapp.Screens.Inputdata.JobApplicationForm
import java.util.UUID

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun EmployerDetailsScreen(
    navController: NavController,
    apiService: ApiService,
    userType: String
) {
    var showUserForm by remember { mutableStateOf(false) }
    val userId = remember { UUID.randomUUID().toString() }
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            if (!showUserForm) {
                EmployerDetailsForm(navController = navController) {
                    showUserForm = true
                }
            } else {
                logo()
                Signup(userType, userId, navController, apiService)
            }
            Spacer(modifier = Modifier.height(30.dp))
            if (showUserForm) {
                // Log in Button
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployerDetailsForm(
    navController: NavController,
    onNext: () -> Unit
) {
    var companyName by remember { mutableStateOf(TextFieldValue()) }
    var companyAddress by remember { mutableStateOf(TextFieldValue()) }
    var registrationNumber by remember { mutableStateOf(TextFieldValue()) }
    var companyWebsite by remember { mutableStateOf(TextFieldValue()) }
    var industryType by remember { mutableStateOf("") }
    var companySize by remember { mutableStateOf("") }
    var companyLogo by remember { mutableStateOf("") }  // URL or path for logo
    var yearOfEstablishment by remember { mutableStateOf(TextFieldValue()) }
    var socialMediaLinks by remember { mutableStateOf(TextFieldValue()) }
    var contactNumber by remember { mutableStateOf(TextFieldValue()) }
    var companyEmail by remember { mutableStateOf(TextFieldValue()) }
    var contactPerson by remember { mutableStateOf(TextFieldValue()) }
    var contactPersonTitle by remember { mutableStateOf(TextFieldValue()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Image(
                        painter = painterResource(id = R.drawable.projob_logo1_12fc55031a756ac453bf), // Replace with your logo resource
                        contentDescription = "App Logo",
                        modifier = Modifier.size(90.dp) // Adjust the size of the logo as needed
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            item {
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    TextField(
                        value = companyName,
                        onValueChange = { companyName = it },
                        placeholder = { Text(text = "Company Name") },
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
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    TextField(
                        value = companyAddress,
                        onValueChange = { companyAddress = it },
                        placeholder = { Text(text = "Company Address") },
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
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    TextField(
                        value = registrationNumber,
                        onValueChange = { registrationNumber = it },
                        placeholder = { Text(text = "Registration Number") },
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
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    TextField(
                        value = companyWebsite,
                        onValueChange = { companyWebsite = it },
                        placeholder = { Text(text = "Company Website") },
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
                // Industry Type Field
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    TextField(
                        value = industryType,
                        onValueChange = { industryType = it },
                        placeholder = { Text(text = "Industry Type") },
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
                // Company Size Field
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    TextField(
                        value = companySize,
                        onValueChange = { companySize = it },
                        placeholder = { Text(text = "Company Size") },
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
                // Year of Establishment Field
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    TextField(
                        value = yearOfEstablishment,
                        onValueChange = { yearOfEstablishment = it },
                        placeholder = { Text(text = "Year of Establishment") },
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
                // Social Media Links Field
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    TextField(
                        value = socialMediaLinks,
                        onValueChange = { socialMediaLinks = it },
                        placeholder = { Text(text = "Social Media Links") },
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
                // Contact Number Field
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    TextField(
                        value = contactNumber,
                        onValueChange = { contactNumber = it },
                        placeholder = { Text(text = "Contact Number") },
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
                // Company Email Field
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    TextField(
                        value = companyEmail,
                        onValueChange = { companyEmail = it },
                        placeholder = { Text(text = "Company Email") },
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
                // Contact Person Field
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    TextField(
                        value = contactPerson,
                        onValueChange = { contactPerson = it },
                        placeholder = { Text(text = "Contact Person") },
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
                // Contact Person Title Field
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    TextField(
                        value = contactPersonTitle,
                        onValueChange = { contactPersonTitle = it },
                        placeholder = { Text(text = "Contact Person Title") },
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
                // Next Button
                Button(
                    onClick = onNext,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Blue,
                        contentColor = Color.White
                    )
                ) {
                    Text("Next")
                }
            }
        }
    }
}
