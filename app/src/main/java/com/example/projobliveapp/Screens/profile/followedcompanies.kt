package com.example.projobliveapp.Screens.profile

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projobliveapp.DataBase.ApiService
import com.example.projobliveapp.DataBase.CompanyDetails
import com.example.projobliveapp.Screens.Employer.CompanyLogo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FollowedCompaniesScreen(navController: NavController, apiService: ApiService, userId: String) {
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var followedCompanies by remember { mutableStateOf<List<String>>(emptyList()) }
    var companyDetails by remember { mutableStateOf<List<CompanyDetails>>(emptyList()) }

    val context = LocalContext.current

    LaunchedEffect(userId) {
        if (userId.isNotEmpty()) {
            isLoading = true
            errorMessage = null
            try {
                val response = apiService.getFollowedCompanies(userId)
                if (response.success) {
                    followedCompanies = response.companies
                    Log.d("FollowedCompanies", "User follows: ${response.companies}")
                } else {
                    errorMessage = "Failed to fetch followed companies."
                }
            } catch (e: Exception) {
                errorMessage = "Error: ${e.message}"
                Log.e("FollowedCompanies", "Error: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    // Fetch company details for each followed company
    LaunchedEffect(followedCompanies) {
        if (followedCompanies.isNotEmpty()) {
            isLoading = true
            try {
                val companyDataList = followedCompanies.mapNotNull { employerId ->
                    try {
                        apiService.getcomapnyData(employerId) // Fetch each company's details
                    } catch (e: Exception) {
                        null
                    }
                }
                companyDetails = companyDataList
            } catch (e: Exception) {
                Log.e("CompanyDetails", "Error fetching company details: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Followed Companies", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            when {
                isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                errorMessage != null -> Text(errorMessage!!, color = Color.Red, modifier = Modifier.align(Alignment.Center))
                companyDetails.isEmpty() -> Text("No followed companies", modifier = Modifier.align(Alignment.Center))
                else -> LazyColumn(modifier = Modifier.padding(16.dp)) {
                    items(companyDetails) { company ->
                        CompanyCard(
                            company, navController,
                            apiService = apiService,userId
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CompanyCard(
    company: CompanyDetails,
    navController: NavController,
    apiService: ApiService,
    userId: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                navController.navigate("CompanyProfileScreenforcandidates/${company.userId}/${userId}")
            },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CompanyLogo(company.userId,apiService) // Assuming you have a CompanyLogo composable
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(company.companyName, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("${company.industryType} | ${company.companyAddress}", fontSize = 14.sp, color = Color.Gray)
            }
        }
    }
}
