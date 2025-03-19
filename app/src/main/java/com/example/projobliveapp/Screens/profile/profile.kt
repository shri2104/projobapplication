package com.example.projobliveapp.Screens.profile

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.projobliveapp.DataBase.ApiService
import com.example.projobliveapp.R

@Composable
fun ScrollableProfileScreen(
    navController: NavHostController,
    userEmail: String?,
    apiService: ApiService
) {
    Scaffold(
        bottomBar = {
            BoxWithConstraints {
                val density = LocalDensity.current
                val textSize = with(density) { (maxWidth / 30).toSp() } // Dynamically adjust text size

                BottomAppBar(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    IconButton(
                        onClick = {navController.navigate("homeScreen/$userEmail") },
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Home, contentDescription = "Home")
                            Text(text = "Home", style = MaterialTheme.typography.labelSmall.copy(fontSize = textSize))
                        }
                    }
                    IconButton(
                        onClick = {  navController.navigate("AvailableInterns/$userEmail") },
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Groups, contentDescription = "Internships")
                            Text(text = "Internships", style = MaterialTheme.typography.labelSmall.copy(fontSize = textSize))
                        }
                    }
                    IconButton(
                        onClick = { navController.navigate("AvailableJobs/$userEmail") },
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Work, contentDescription = "Jobs")
                            Text(text = "Jobs", style = MaterialTheme.typography.labelSmall.copy(fontSize = textSize))
                        }
                    }
                    IconButton(
                        onClick = { },
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Message, contentDescription = "Messages")
                            Text(text = "Messages", style = MaterialTheme.typography.labelSmall.copy(fontSize = textSize))
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .background(Color(0xFFF8F8F8))
        ) {
            ProfileHeader()
            NavigationMenu(
                navController = navController,
                userEmail = userEmail,
                apiService
            )
        }
    }
}

@Composable
fun ProfileHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.projob_logo1_12fc55031a756ac453bf), // Replace with your app logo
            contentDescription = "App Logo",
            modifier = Modifier.size(150.dp)
        )
    }
}

@Composable
fun NavigationMenu(navController: NavHostController, userEmail: String?, apiService: ApiService) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)

    ) {
        Text(
            text = "User Dashboard",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF5CB85C),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE8F5E9))
                .padding(8.dp)
                .clickable {
                    println("User Dashboard clicked!")
                }
        )
        Spacer(modifier = Modifier.height(16.dp))
        val menuItems = listOf(
            Pair("Profile", Icons.Default.Person),
            Pair("My Resume", Icons.Default.Description),
            Pair("My Applied Jobs", Icons.Default.Work),
            Pair("Following Employers", Icons.Default.People),
            Pair("Alert Jobs", Icons.Default.Notifications),
            Pair("Messages", Icons.Default.Message),
            Pair("Meetings", Icons.Default.CalendarToday)
        )

        menuItems.forEach { item ->
            NavigationMenuItem(
                title = item.first, icon = item.second,
                navController =navController,userEmail=userEmail,apiService
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
@Composable
fun NavigationMenuItem(
    title: String,
    icon: ImageVector,
    navController: NavHostController,
    userEmail: String?,
    apiService: ApiService
) {
    var userId by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    LaunchedEffect(userEmail) {
        if (userEmail != null) {
            if (userEmail.isNotEmpty()) {
                isLoading = true
                errorMessage = null
                try {
                    val userIdResponse = apiService.getuserid(userEmail)
                    userId = userIdResponse.userId

                    if (!userId.isNullOrBlank()) {
                        Log.d("UserIDFetch", "Fetched userId: $userId")
                    } else {
                        errorMessage = "User ID not found."
                    }
                } catch (e: Exception) {
                    errorMessage = "Error fetching user ID: ${e.message}"
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                    Log.e("UserIDFetch", "Error: ${e.message}", e)
                } finally {
                    isLoading = false
                }
            }
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                when (title) {
                    "Profile" -> navController.navigate("profilePage/$userEmail")
                    "My Resume" -> navController.navigate("MyResume/$userEmail")
                    "My Applied Jobs" -> navController.navigate("Appliedjobs/$userEmail")
                    "Following Employers" -> navController.navigate("followedcompanies/$userId")
                }
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = Color.Gray,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )
    }
}

