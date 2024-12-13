package com.example.projobliveapp.Screens.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.Text
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.projobliveapp.R

@Composable
fun ScrollableProfileScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color(0xFFF8F8F8))
    ) {
        ProfileHeader()
        ProfileSection()
        NavigationMenu()
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
            painter = painterResource(id = R.drawable.screenshot_2024_12_11_at_12_17_32am), // Replace with your app logo
            contentDescription = "App Logo",
            modifier = Modifier.size(150.dp)
        )
    }
}

@Composable
fun ProfileSection() {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

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
            onClick = { launcher.launch("image/*") }, // Open file picker
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5CB85C)) // Green button
        ) {
            Text(text = "Browse Image", color = Color.White)
        }
    }
}

@Composable
fun NavigationMenu() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)

    ) {
        // Section heading
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
            NavigationMenuItem(title = item.first, icon = item.second)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun NavigationMenuItem(title: String, icon: ImageVector) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { /* Handle navigation item click */ },
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
@Preview(showBackground = true)
@Composable
fun ScrollableProfileScreenPreview() {
    ScrollableProfileScreen()
}

