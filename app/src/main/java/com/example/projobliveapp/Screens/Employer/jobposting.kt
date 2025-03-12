package com.example.projobliveapp.Screens.Employer

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Work
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.example.projobliveapp.DataBase.ApiService
import com.example.projobliveapp.DataBase.CompanyDetails
import com.example.projobliveapp.DataBase.JobPost
import com.example.projobliveapp.R
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobpostScreen(
    navController: NavHostController,
    apiService: ApiService,
    employerid: String,
    userEmail: String
) {
    var jobtitle by remember { mutableStateOf("") }
    val country = remember { mutableStateOf("United States") }
    val selectedContractType = remember { mutableStateOf("Permanent") }
    val selectedWorkingHours = remember { mutableStateOf("Full Time") }
    var minexp by remember { mutableStateOf("") }
    var maxexp by remember { mutableStateOf("") }
    var Keyskills by remember { mutableStateOf("") }
    var minSalary by remember { mutableStateOf("") }
    var maxSalary by remember { mutableStateOf("") }
    var jobDescription by remember { mutableStateOf("") }
    var jobLocation by remember { mutableStateOf("") }
    val applicationMethod = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val externalLink = remember { mutableStateOf("") }
    val phoneNumber = remember { mutableStateOf("") }
    val selectedOption = remember { mutableStateOf("Yes") }
    var companyData by remember { mutableStateOf<CompanyDetails?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(employerid) {
        try {
            if (!employerid.isNullOrBlank()) {
                companyData = apiService.getcomapnyData(employerid)
            } else {
                errorMessage = "User ID not found"
            }
        } catch (e: Exception) {
            errorMessage = "Error fetching data: ${e.message}"
            Toast.makeText(context, "Error fetching data: ${e.message}", Toast.LENGTH_SHORT).show()
        } finally {
            isLoading = false
        }
    }
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
                    IconButton(onClick = {  }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back Button")
                    }
                },
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = { navController.navigate("EmployerHomeScreen/$userEmail") },
                    modifier = Modifier.weight(1f)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Home, contentDescription = "Home")
                        Text(text = "Home", style = MaterialTheme.typography.titleSmall)
                    }
                }
                IconButton(
                    onClick = { },
                    modifier = Modifier.weight(1f)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Groups, contentDescription = "Applications")
                        Text(text = "Applications", style = MaterialTheme.typography.titleSmall)
                    }
                }
                IconButton(
                    onClick = {navController.navigate("postedjobs/${employerid}/${userEmail}")  },
                    modifier = Modifier.weight(1f)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Work, contentDescription = "Post Jobs")
                        Text(text = "Jobs", style = MaterialTheme.typography.titleSmall)
                    }
                }
                IconButton(
                    onClick = { },
                    modifier = Modifier.weight(1f)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Message, contentDescription = "Messages")
                        Text(text = "Messages", style = MaterialTheme.typography.titleSmall)
                    }
                }
            }
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
                        text = "Post A Job",
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
                            StepIndicator(true)
                            StepIndicator(false)
                            StepIndicator(false)
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(10.dp))
            }
            item {
                Text(
                    text = "Job title*",
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
                        value = jobtitle,
                        onValueChange = { jobtitle = it },
                        placeholder = { Text(text = "UI/UX") },
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
                    text = "Job location*",
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .width(300.dp)
                        .padding(bottom = 16.dp)
                ) {
                    TextField(
                        value = jobLocation,
                        onValueChange = {jobLocation = it},
                        placeholder = { Text(text = "Town or Region") },
                        trailingIcon = {
                            Icon(Icons.Filled.LocationOn, contentDescription = "Location icon")
                        },
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
                    text = "Country*",
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                CountryDropdown(selectedCountry = country)
            }

            item {
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = "Contract Type",
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                ContractType(selectedContractType = selectedContractType)
            }

            item {
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = "Working Hours",
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                WorkingHours(selectedWorkingHours = selectedWorkingHours)
            }

            item {
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = "Salary",
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Card(
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        TextField(
                            value = minSalary,
                            onValueChange = {minSalary = it},
                            placeholder = { Text(text = "Min") },
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

                    Card(
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        TextField(
                            value = maxSalary,
                            onValueChange = {maxSalary = it },
                            placeholder = { Text(text = "Max") },
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
            }
            item {
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = "Experience in Years",
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Card(
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        TextField(
                            value = minexp,
                            onValueChange = {minexp = it},
                            placeholder = { Text(text = "Min") },
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

                    Card(
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        TextField(
                            value = maxexp,
                            onValueChange = {maxexp = it },
                            placeholder = { Text(text = "Max") },
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
            }
            item {
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = "Job Description",
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
                        value = jobDescription,
                        onValueChange = {jobDescription = it},
                        placeholder = { Text(text = "Enter job details here...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            cursorColor = Color.Blue,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        ),
                        maxLines = 10,
                        singleLine = false
                    )
                }
            }
            item {
                Text(
                    text = "Key Skills*",
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
                        value = Keyskills,
                        onValueChange = { Keyskills = it },
                        placeholder = { Text(text = "placeholder") },
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
                Spacer(modifier = Modifier.height(14.dp))
                ApplicationMethodSelection(
                    applicationMethod = applicationMethod,
                    email = email,
                    externalLink = externalLink,
                    phoneNumber = phoneNumber
                )
            }
            item {
                Spacer(modifier = Modifier.height(14.dp))
                RadioButtonRow(selectedOption = selectedOption)
            }
            item {
                val jobid = remember { UUID.randomUUID().toString() }
                val currentTime = remember { SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).format(
                    Date()
                ) }

                Button(
                    onClick = {
                        val jobPost = companyData?.companyName?.let {
                            JobPost(
                                jobid = jobid,
                                Employerid = employerid,
                                jobTitle = jobtitle,
                                country = country.value,
                                contractType = selectedContractType.value,
                                workingHours = selectedWorkingHours.value,
                                minExperience = (minexp.toIntOrNull() ?: 0).toString(),
                                maxExperience = (maxexp.toIntOrNull() ?: 0).toString(),
                                keySkills = Keyskills,
                                minSalary = (minSalary.toIntOrNull() ?: 0).toString(),
                                maxSalary = (maxSalary.toIntOrNull() ?: 0).toString(),
                                jobDescription = jobDescription,
                                jobLocation = jobLocation,
                                applicationMethod = applicationMethod.value,
                                contactEmail = email.value,
                                externalLink = externalLink.value,
                                phoneNumber = phoneNumber.value,
                                relocationSupport = (selectedOption.value == "Yes").toString(),
                                Companyname = it, // Add the company name here
                                createdAt = currentTime
                            )
                        }
                        val jobPostJson = Uri.encode(Gson().toJson(jobPost))
                        navController.navigate("jobDetailsScreen/$jobPostJson")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(Color.Blue)
                ) {
                    Text(text = "Preview", color = Color.White)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryDropdown(selectedCountry: MutableState<String>) {
    var expanded by remember { mutableStateOf(false) }
    val countries = listOf("United States", "India", "Canada", "United Kingdom", "Australia")

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
fun ContractType(selectedContractType: MutableState<String>) {
    var expanded by remember { mutableStateOf(false) }
    val contractTypes = listOf("Job", "Internship")
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
                value = selectedContractType.value,
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
            contractTypes.forEach { type ->
                DropdownMenuItem(
                    text = { Text(type) },
                    onClick = {
                        selectedContractType.value = type
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkingHours(selectedWorkingHours: MutableState<String>) {
    var expanded by remember { mutableStateOf(false) }
    val workingHoursList = listOf("Full Time", "Part Time")

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
                value = selectedWorkingHours.value,
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
            workingHoursList.forEach { hours ->
                DropdownMenuItem(
                    text = { Text(hours) },
                    onClick = {
                        selectedWorkingHours.value = hours
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun ApplicationMethodSelection(
    applicationMethod: MutableState<String>,
    email: MutableState<String>,
    externalLink: MutableState<String>,
    phoneNumber: MutableState<String>
) {
    Column {
        Text("How to Accept Applications", fontSize = 18.sp, color = Color.Black)

        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = applicationMethod.value == "Email",
                onClick = { applicationMethod.value = "Email" }
            )
            Text("Email")
            Spacer(modifier = Modifier.width(8.dp))

            RadioButton(
                selected = applicationMethod.value == "External Link",
                onClick = { applicationMethod.value = "External Link" }
            )
            Text("External Link")
            Spacer(modifier = Modifier.width(8.dp))

            RadioButton(
                selected = applicationMethod.value == "Phone Number",
                onClick = { applicationMethod.value = "Phone Number" }
            )
            Text("Phone Number")
        }

        Spacer(modifier = Modifier.height(8.dp))
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            TextField(
                value = email.value,
                onValueChange = { email.value = it },
                placeholder = { Text(text = "Your Email Address") },
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

        when (applicationMethod.value) {
            "External Link" -> {
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    TextField(
                        value = externalLink.value,
                        onValueChange = { externalLink.value = it },
                        placeholder = { Text(text = "Enter External Link") },
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
            "Phone Number" -> {
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    TextField(
                        value = phoneNumber.value,
                        onValueChange = { phoneNumber.value = it },
                        placeholder = { Text(text = "Enter Phone Number") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
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
        }
    }
}

@Composable
fun RadioButtonRow(selectedOption: MutableState<String>) {
    val options = listOf("Yes", "No", "Optional")

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Do you require applicants to submit the CV?", fontWeight = FontWeight.Bold)

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(top = 8.dp)
        ) {
            options.forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { selectedOption.value = option }
                ) {
                    RadioButton(
                        selected = (selectedOption.value == option),
                        onClick = { selectedOption.value = option }
                    )
                    Text(text = option, modifier = Modifier.padding(start = 4.dp))
                }
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

@Composable
fun CustomButton(text:String) {
    Button(
        onClick = {  },
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE6F7FF)),
        shape = RoundedCornerShape(4.dp),
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier
            .width(80.dp)
            .height(40.dp)
    ) {
        Text(
            text = text,
            color = Color.Blue,
            fontWeight = FontWeight.Bold
        )
    }
}
@Composable
fun JobDetailsScreen(jobPost: JobPost, apiService: ApiService, navController: NavHostController) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var isPosting by remember { mutableStateOf(false) }
    Spacer(Modifier.height(16.dp))
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        HeaderSection()
        Spacer(Modifier.height(16.dp))
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
                StepIndicator(false)
                StepIndicator(true)
                StepIndicator(false)
            }
        }
        Spacer(Modifier.height(16.dp))
        Text(
            text = "Your job posting is ready!",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        JobDetailsCard(
            jobTitle = jobPost.jobTitle,
            companyName = jobPost.Companyname,
            country = jobPost.country,
            contractType = jobPost.contractType,
            minSalary = jobPost.minSalary,
            maxSalary = jobPost.maxSalary,
            jobLocation = jobPost.jobLocation,
            workingHours =  jobPost.workingHours
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                coroutineScope.launch(Dispatchers.IO) {
                    isPosting = true
                    try {
                        val response = apiService.storeJob(jobPost)
                        withContext(Dispatchers.Main) {
                            if (response.isSuccessful) {
                                Toast.makeText(context, "Job posted successfully!", Toast.LENGTH_SHORT).show()
                                navController.navigate("Jobposted")
                            } else {
                                Toast.makeText(context, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    } finally {
                        isPosting = false
                    }
                }
            },modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF90CAF9)),
            enabled = !isPosting
        ) {
            if (isPosting) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
            } else {
                Text("Post Job",color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun HeaderSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = {  }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.Gray)
        }
        Text(
            text = "Preview",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF283593)
        )
        IconButton(onClick = { /* More action */ }) {
            Icon(Icons.Default.MoreVert, contentDescription = "More", tint = Color.Gray)
        }
    }
}

@Composable
fun JobDetailsCard(
    jobTitle: String,
    companyName: String,
    country: String,
    contractType: String,
    minSalary: String,
    maxSalary: String,
    jobLocation: String,
    workingHours: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .drawBehind {
                val stroke = Stroke(
                    width = 4f,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                )
                drawRoundRect(
                    color = Color.Gray,
                    style = stroke,
                    cornerRadius = CornerRadius(10.dp.toPx())
                )
            },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(Color.White),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = jobTitle,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Business,
                    contentDescription = "Company",
                    tint = Color.Gray,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = companyName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.DarkGray
                )
            }
            Divider(color = Color.Gray, thickness = 1.dp)
            JobDetailItem("Job Location", jobLocation)
            JobDetailItem("Country", country)
            JobDetailItem("Salary", "$minSalary - $maxSalary k/Month")
            JobDetailItem("Contract Type", contractType)
            JobDetailItem("Working Hours", workingHours)
        }
    }
}

@Composable
fun JobDetailItem(title: String, value: String, subtitle: String? = null) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(text = title, color = Color.Gray, fontSize = 14.sp)
        Text(text = value, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        if (subtitle != null) {
            Text(text = subtitle, color = Color.Gray, fontSize = 14.sp)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobPostedScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Posted", fontWeight = FontWeight.Bold, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF283593))
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Spacer(Modifier.height(16.dp))
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
                    StepIndicator(false)
                    StepIndicator(false)
                    StepIndicator(true)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Success",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(80.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Your job has been posted successfully!",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Candidates will be able to view and apply for the job soon.",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.fillMaxWidth(0.7f),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF283593))
                ) {
                    Text(text = "Go Back", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}




