package com.example.projobliveapp.Screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.projobliveapp.Navigation.Screen
import com.example.projobliveapp.R

@Composable
fun ScrollableProfileScreen(navController: NavHostController, userEmail: String?) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color(0xFFF8F8F8))
    ) {
        ProfileHeader()
        ProfileSection()
        NavigationMenu(
            navController = navController,
            userEmail=userEmail
        )
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
fun ProfileSection() {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val imageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }

    var resumeUri by remember { mutableStateOf<Uri?>(null) }
    val resumeLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        resumeUri = uri
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Profile Image
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Color.Gray),
            contentAlignment = Alignment.Center
        ) {
            if (imageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(imageUri),
                    contentDescription = "Selected Profile Image",
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "User Icon",
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { imageLauncher.launch("image/*") }, // Open file picker for image
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5CB85C)) // Green button
        ) {
            Text(text = "Browse Image", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Resume Upload
        Button(
            onClick = { resumeLauncher.launch("application/pdf") }, // Open file picker for PDF
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5CB85C)) // Green button
        ) {
            Text(text = "Upload Resume", color = Color.White)
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = if (resumeUri != null) "Resume Uploaded: ${resumeUri?.lastPathSegment}" else "No Resume Uploaded",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))


    }
}
@Composable
fun NavigationMenu(navController: NavHostController, userEmail: String?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)

    ) {
        Text(
            text = "User Dashboard",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF5CB85C), // Green color
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE8F5E9))
                .padding(8.dp)
                .clickable {

                    println("User Dashboard clicked!")
                }
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Navigation menu items
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
                navController =navController,userEmail=userEmail
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
    userEmail: String?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                when (title) {
                    "Profile" -> navController.navigate("profilePage/$userEmail")
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

