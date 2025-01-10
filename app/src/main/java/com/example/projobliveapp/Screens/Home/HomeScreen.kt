import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.projobliveapp.R

import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.projobliveapp.DataBase.ApiService
import java.time.LocalTime


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UserGreetingScreen(email: String, apiService: ApiService) {
    var userName by remember { mutableStateOf<String?>(null) }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    val currentTime = LocalTime.now()
    val greeting = when (currentTime.hour) {
        in 5..11 -> "Good morning"
        in 12..17 -> "Good afternoon"
        else -> "Good evening"
    }
    LaunchedEffect(email) {
        if (email.isNotBlank()) {
            loading = true
            try {
                val userData = apiService.getProfileDataByEmail(email)
                userName = userData.firstName
            } catch (e: Exception) {
                error = "Failed to fetch user data: ${e.message}"
            } finally {
                loading = false
            }
        } else {
            error = "Please enter a valid email"
        }
    }

    Surface(modifier = Modifier.fillMaxWidth()) {
        when {
            loading -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }
            }
            error != null -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(text = error ?: "Unknown error")
                }
            }
            userName != null -> {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize().padding(16.dp)
                ) {
                    Text(
                        text = "Hi, $userName! 👋",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Your next opportunity is just a click away!",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun JobAppSlidingMenuScreen(
    navController: NavHostController,
    userEmail: String,
    apiservice: ApiService
) {
    var isMenuVisible by remember { mutableStateOf(false) }
    val menuWidth by animateFloatAsState(
        targetValue = if (isMenuVisible) 0.85f else 0f,
        label = "MenuWidthAnimation"
    )
    Box(modifier = Modifier.fillMaxSize()) {
        if (!isMenuVisible) {
            MainJobScreenContent(
                onMenuClick = { isMenuVisible = true },
                navController = navController,
                userEmail=userEmail,
                apiservice=apiservice
            )
        }
        if (isMenuVisible) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(menuWidth)
                    .align(Alignment.CenterStart)
                    .background(
                        Color(0xFF1E1E1E),
                        RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)
                    )
            ) {
                JobAppMenuContent(
                    onCloseMenu = { isMenuVisible = false },
                    navController=navController,
                    userEmail=userEmail,
                    apiservice= apiservice
                )
            }
        }
    }
}

@Composable
fun HowItWorksSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "How It Works?",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        val steps = listOf(
            Step(
                title = "Register an account to start",
                description = "To register as a recruiter, visit the recruitment platform’s website, select the recruiter sign-up option, fill in the required details, verify your email, and complete your profile to start recruiting.",
                icon = Icons.Default.PersonAdd
            ),
            Step(
                title = "Explore over thousands of resumes",
                description = "Explore over thousands of resumes to find the perfect candidates for your job openings, leveraging a vast database to match skill sets and experiences with your requirements.",
                icon = Icons.Default.Lock
            ),
            Step(
                title = "Find the most suitable candidate",
                description = "Explore thousands of resumes to discover ideal candidates for your vacancies, tapping into a broad pool of talents and experiences tailored to your needs.",
                icon = Icons.Default.List
            )
        )

        steps.forEach { step ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = step.icon,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp).padding(end = 16.dp),
                    tint = LocalContentColor.current
                )
                Column {
                    Text(
                        text = step.title,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = step.description)
                }
            }
        }
    }
}

data class Step(
    val title: String,
    val description: String,
    val icon: ImageVector
)

@Composable
fun ActiveJobsInCitiesSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Active Jobs in Cities",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        val cities = listOf(
            City("New York", Icons.Default.Home),
            City("San Francisco", Icons.Default.DirectionsBoat),
            City("Austin", Icons.Default.Place),
            City("Los Angeles", Icons.Default.Star),
            City("Chicago", Icons.Default.LocationCity)
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(cities) { city ->
                CityCard(city = city)
            }
        }
    }
}

data class City(
    val name: String,
    val icon: ImageVector
)

@Composable
fun CityCard(city: City) {
    Card(
        modifier = Modifier
            .size(150.dp)
            .clickable { println("Clicked on ${city.name}") },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF3E5F5)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(
                    imageVector = city.icon,
                    contentDescription = city.name,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = city.name,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun ContactDetailsSection() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Text(
            text = "Contact Details",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "ProJob",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "Raheja Platinum, off Andheri – Kurla Road, Sag Baug, Marol, Andheri East, Mumbai, Maharashtra 400059",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(modifier = Modifier.padding(vertical = 8.dp)) {
            Icon(imageVector = Icons.Default.Phone, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
            Text(text = "+91 88502 31081", style = MaterialTheme.typography.bodyLarge)
        }
        Row(modifier = Modifier.padding(vertical = 8.dp)) {
            Icon(imageVector = Icons.Default.Email, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
            Text(text = "info@projob.co.in", style = MaterialTheme.typography.bodyLarge)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Visit Our Website",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Text(
            text = "Explore all the features of our platform to help you find the best candidates and advertise your job openings to millions of users.",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))


        Button(
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.projob.co.in"))
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Visit Our Website")
        }
    }
}




@Composable
fun SearchBarSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Search Jobs by Title or Location") },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = null)
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {  },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Search")
        }
    }
}

@Composable
fun TrendingJobsSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Trending Jobs",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(10) { index ->
                Card(
                    modifier = Modifier
                        .size(150.dp)
                        .padding(8.dp)
                        .clickable {
                            println("Clicked on Trending Job $index")

                        },
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Job $index", textAlign = TextAlign.Center)
                    }
                }
            }
        }
    }
}

@Composable
fun TrustedByCompaniesSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Trusted by Companies",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(10) {
                Image(
                    painter = painterResource(id = R.drawable.microsoft),
                    contentDescription = "Company Logo",
                    modifier = Modifier
                        .size(64.dp)
                        .padding(8.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}
@Composable
fun BrowseByCategorySection() {
    val categories = listOf(
        "Development", "Management", "Health & Care",
        "Finance", "Design", "Education",
        "Marketing", "Engineering", "Sales"
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Browse by Category",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(categories) { category ->
                CategoryCard(category = category) {
                    println("Clicked on $category category")

                }
            }
        }
    }
}
@Composable
fun CategoryCard(category: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(120.dp)
            .height(150.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFBBDEFB)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = category,
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}
@Composable
fun RecentlyViewedJobsSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Recently Viewed Jobs",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(5) { index ->
                Card(
                    modifier = Modifier
                        .size(150.dp)
                        .padding(8.dp)
                        .clickable {
                            println("Clicked on Recently Viewed Job $index")
                        },
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Job $index", textAlign = TextAlign.Center)
                    }
                }
            }
        }
    }
}
