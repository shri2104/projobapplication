package com.example.projobliveapp.Screens.Login

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberImagePainter
import com.bawp.freader.screens.login.Signup
import com.example.projobliveapp.DataBase.ApiService
import com.example.projobliveapp.DataBase.CompanyDetails
import com.example.projobliveapp.Navigation.Screen
import com.example.projobliveapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.UUID

fun getOrCreateUserId(context: Context): String {
    val sharedPref = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    return sharedPref.getString("USER_ID", null) ?: UUID.randomUUID().toString().also {
        sharedPref.edit().putString("USER_ID", it).apply()
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun EmployerDetailsScreen(
    navController: NavController,
    apiService: ApiService,
    userType: String
) {
    val context = LocalContext.current
    val userId = remember { getOrCreateUserId(context) }  // Stable across app restarts

    var showUserForm by remember { mutableStateOf(false) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            if (!showUserForm) {
                EmployerDetailsForm(navController = navController, apiService, userId) {
                    showUserForm = true
                }
            } else {
                logo()
                Signup(userType, userId, navController, apiService)
            }
            Spacer(modifier = Modifier.height(30.dp))
            if (showUserForm) {
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
    apiService: ApiService,
    userId: String,
    onNext: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var companyName by remember { mutableStateOf("") }
    var companyAddress by remember { mutableStateOf("") }
    var registrationNumber by remember { mutableStateOf("") }
    var companyWebsite by remember { mutableStateOf("") }
    var industryType by remember { mutableStateOf("") }
    var companySize by remember { mutableStateOf("") }
    var yearOfEstablishment by remember { mutableStateOf("") }
    var socialMediaLinks by remember { mutableStateOf("") }
    var contactNumber by remember { mutableStateOf("") }
    var companyEmail by remember { mutableStateOf("") }
    var contactPerson by remember { mutableStateOf("") }
    var contactPersonTitle by remember { mutableStateOf("") }
    var aboutCompany by remember { mutableStateOf("") }

    var logoUri by remember { mutableStateOf<Uri?>(null) }
    var uploadedLogoUrl by remember { mutableStateOf<String?>(null) }
    var isUploading by remember { mutableStateOf(false) }

    val allFieldsFilled = listOf(
        companyName, companyAddress, registrationNumber, companyWebsite, industryType,
        companySize, yearOfEstablishment, socialMediaLinks, contactNumber,
        companyEmail, contactPerson, contactPersonTitle
    ).all { it.isNotBlank() }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        logoUri = uri
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Image(
                        painter = painterResource(id = R.drawable.projob_logo1_12fc55031a756ac453bf),
                        contentDescription = "App Logo",
                        modifier = Modifier.size(90.dp)
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
            item { CustomTextField(companyName, { companyName = it }, "Company Name") }
            item { CustomTextField(companyAddress, { companyAddress = it }, "Company Address") }
            item { CustomTextField(registrationNumber, { registrationNumber = it }, "Registration Number") }
            item { CustomTextField(companyWebsite, { companyWebsite = it }, "Company Website") }
            item { CustomTextField(industryType, { industryType = it }, "Industry Type") }
            item { CustomTextField(companySize, { companySize = it }, "Company Size") }
            item { CustomTextField(yearOfEstablishment, { yearOfEstablishment = it }, "Year of Establishment") }
            item { CustomTextField(socialMediaLinks, { socialMediaLinks = it }, "Social Media Links") }
            item { CustomTextField(contactNumber, { contactNumber = it }, "Contact Number") }
            item { CustomTextField(companyEmail, { companyEmail = it }, "Company Email") }
            item { CustomTextField(contactPerson, { contactPerson = it }, "Contact Person") }
            item { CustomTextField(contactPersonTitle, { contactPersonTitle = it }, "Contact Person Title") }
            item { CustomTextField(aboutCompany, { aboutCompany = it }, "About Company") }

            // Logo Picker
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    logoUri?.let {
                        Image(
                            painter = rememberImagePainter(it),
                            contentDescription = "Selected Logo",
                            modifier = Modifier.size(200.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    Button(onClick = { imagePicker.launch("image/*") }) {
                        Text("Pick Logo")
                    }
                }
            }

            item {
                Button(
                    onClick = {
                        logoUri?.let { uri ->
                            isUploading = true
                            coroutineScope.launch {
                                uploadLogo(
                                    context = navController.context,
                                    apiService = apiService,
                                    logoUri = uri,
                                    userId = userId
                                ) { success, imageUrl ->
                                    isUploading = false
                                    if (success) {
                                        uploadedLogoUrl = imageUrl
                                    } else {
                                        Toast.makeText(
                                            navController.context,
                                            "Upload failed",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                        } ?: Toast.makeText(navController.context, "Please select an image", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    enabled = logoUri != null && !isUploading
                ) {
                    Text(if (isUploading) "Uploading..." else "Upload Logo")
                }
            }


            item {
                Button(
                    onClick = {
                        val companyDetails = CompanyDetails(
                            userId = userId,
                            companyName = companyName,
                            companyAddress = companyAddress,
                            registrationNumber = registrationNumber,
                            companyWebsite = companyWebsite,
                            industryType = industryType,
                            companySize = companySize,
                            yearOfEstablishment = yearOfEstablishment,
                            socialMediaLinks = socialMediaLinks.takeIf { it.isNotBlank() },
                            contactNumber = contactNumber,
                            companyEmail = companyEmail,
                            contactPerson = contactPerson,
                            contactPersonTitle = contactPersonTitle,
                            aboutCompany = aboutCompany
                        )
                        coroutineScope.launch(Dispatchers.IO) {
                            try {
                                apiService.PostcomapnyData(companyDetails)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                        onNext()
                    },
                    enabled = allFieldsFilled,
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (allFieldsFilled) Color.Blue else Color.Gray,
                        contentColor = Color.White
                    )
                ) {
                    Text("Next")
                }
            }
        }
    }
}


fun uploadLogo(
    context: Context,
    apiService: ApiService,
    logoUri: Uri,
    userId: String,
    onResult: (Boolean, String?) -> Unit
) {
    val contentResolver = context.contentResolver
    val inputStream = contentResolver?.openInputStream(logoUri)
    val bytes = inputStream?.readBytes()
    val requestBody = bytes?.toRequestBody("image/*".toMediaTypeOrNull())
    val logoPart = requestBody?.let {
        MultipartBody.Part.createFormData("logo", "logo.png", it)
    }
    val companyIdPart = userId.toRequestBody("text/plain".toMediaTypeOrNull())

    logoPart?.let {
        apiService.uploadLogo(it, companyIdPart).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    onResult(true, response.body()?.string())
                } else {
                    onResult(false, null)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                onResult(false, null)
            }
        })
    }
}




@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
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
