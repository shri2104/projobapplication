package com.example.projobliveapp.Screens.Employer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projobliveapp.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyProfileScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Company Profile",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Share profile */ }) {
                        Icon(Icons.Default.Share, contentDescription = "Share")
                    }
                    IconButton(onClick = { /* Save to favorites */ }) {
                        Icon(Icons.Default.FavoriteBorder, contentDescription = "Favorite")
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Company Logo & Name
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.projob_logo1_12fc55031a756ac453bf), // Replace with actual logo
                    contentDescription = "Company Logo",
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "TechNova Solutions",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Software & IT | Bangalore, India",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }

            Text(
                text = "About Us",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "TechNova Solutions is a leading IT firm specializing in AI-powered business solutions. Our expertise lies in software development, cloud computing, and digital transformation.",
                style = MaterialTheme.typography.bodyMedium
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                HighlightCard("Employees", "500+")
                HighlightCard("Founded", "2015")
                HighlightCard("Rating", "4.8 ★")
            }

            Text(
                text = "Contact & Socials",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                ContactItem(Icons.Default.Email, "contact@technova.com")
                ContactItem(Icons.Default.Language, "www.technova.com")
                ContactItem(Icons.Default.Message, "linkedin.com/company/technova")
            }
            Text(
                text = "Open Positions",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            JobCard("Android Developer", "Bangalore", "₹12-18 LPA")
            JobCard("Backend Engineer", "Remote", "₹15-22 LPA")
            JobCard("Data Scientist", "Mumbai", "₹18-25 LPA")

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate("jobpost") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Post a Job", fontWeight = FontWeight.Bold)
            }
        }
    }
}
@Composable
fun HighlightCard(title: String, value: String) {
    Card(
        modifier = Modifier

            .padding(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = value, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            Text(text = title, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }
    }
}
// Contact Item Row
@Composable
fun ContactItem(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = text, modifier = Modifier.size(20.dp), tint = Color.Blue)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
    }
}

// Job Listing Card
@Composable
fun JobCard(position: String, location: String, salary: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = position, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(text = location, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                Text(text = salary, style = MaterialTheme.typography.bodyMedium, color = Color.Green)
            }
            IconButton(onClick = { /* View Details */ }) {
                Icon(Icons.Default.ArrowForward, contentDescription = "View Job")
            }
        }
    }
}
