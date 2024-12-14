import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.projobliveapp.Navigation.Screen
import com.example.projobliveapp.R



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobSearchHomePage(navController: NavHostController) {
    val scrollState = rememberScrollState()
    val openMenu = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Home Dashboard", style = MaterialTheme.typography.headlineSmall) },
                actions = {
                    IconButton(onClick = { openMenu.value = true }) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = "Profile Icon"
                        )
                    }
                    DropdownMenu(
                        expanded = openMenu.value,
                        onDismissRequest = { openMenu.value = false }
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                openMenu.value = false
                                navController.navigate(Screen.profilesection.name) // Navigate to Profile Screen
                            },
                            text = { Text("Profile") }
                        )
                        DropdownMenuItem(
                            onClick = {
                                openMenu.value = false
                                navController.navigate(Screen.profilesection.name) // Navigate to Login Screen
                            },
                            text = { Text("Login") }
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar() {
                IconButton(onClick = {  }) {
                    Icon(Icons.Default.Home, contentDescription = "Home")
                }
                IconButton(onClick = {}) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
                IconButton(onClick = { }) {
                    Icon(Icons.Default.Check, contentDescription = "Applied Jobs")
                }
                IconButton(onClick = {  }) {
                    Icon(Icons.Default.Person, contentDescription = "Profile")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
        ) {
            SearchBarSection()
            TrendingJobsSection()
            TrustedByCompaniesSection()
            BrowseByCategorySection()
            RecentlyViewedJobsSection()
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
            items(10) {
                Card(
                    modifier = Modifier
                        .size(150.dp)
                        .padding(8.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Job $it", textAlign = TextAlign.Center)
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

                    println("Clicked on $category")
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
            items(5) {
                Card(
                    modifier = Modifier
                        .size(150.dp)
                        .padding(8.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Job $it", textAlign = TextAlign.Center)
                    }
                }
            }
        }
    }
}

