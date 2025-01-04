import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.projobliveapp.DataBase.ApiService
import com.example.projobliveapp.DataBase.Job
import com.example.projobliveapp.DataBase.SaveJob
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedJobs(apiService: ApiService, email: String, navController: NavHostController) {
    val coroutineScope = rememberCoroutineScope()
    var savedJobDetails by remember { mutableStateOf<List<Job>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                val response = apiService.getSavedJobs(email)
                val jobIds = response.jobIds.flatten()

                val jobDetails = mutableListOf<Job>()
                for (id in jobIds) {
                    try {
                        val job = apiService.getJobById(id)
                        jobDetails.add(job)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                savedJobDetails = jobDetails
                isLoading = false
            } catch (e: Exception) {
                errorMessage = "Failed to load saved jobs: ${e.localizedMessage}"
                isLoading = false
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Saved Jobs") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    ProfileMenu(navController,email)
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                errorMessage != null -> Text(
                    text = errorMessage ?: "",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
                savedJobDetails.isNotEmpty() -> LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(savedJobDetails) { job ->
                        SavedJobItem(
                            job = job,
                            navController = navController,
                            apiService = apiService,
                            userEmail = email
                        )
                    }
                }
                else -> Text(
                    text = "No saved jobs found.",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
fun ProfileMenu(navController: NavHostController, userEmail: String) {
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Box {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(Icons.Default.AccountCircle, contentDescription = "Profile")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                onClick = {
                    navController.navigate("homeScreen/$userEmail")
                    expanded = false
                },
                text = { Text("Home") }
            )
            DropdownMenuItem(
                onClick = {
                    navController.navigate("profileSection/$userEmail")
                    expanded = false
                },
                text = { Text("Profile") }
            )
        }
    }
}

@Composable
fun SavedJobItem(
    job: Job,
    navController: NavHostController,
    apiService: ApiService,
    userEmail: String
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var isFavorite by remember { mutableStateOf(false) }

    LaunchedEffect(job._id) {
        try {
            val response = apiService.isJobInFavorites(userEmail, job._id)
            if (response.isSuccessful) {
                isFavorite = response.body()?.success ?: false
            } else {
                Toast.makeText(context, "Error fetching favorite status", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    Card(
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = job.jobTitle,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = Color.Blue,
                fontSize = 20.sp
            )
            Text(text = job.company, style = MaterialTheme.typography.bodyMedium)

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",
                    modifier = Modifier.size(20.dp),
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = job.jobLocation, style = MaterialTheme.typography.bodyMedium)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            val newFavoriteState = !isFavorite
                            try {
                                // Prepare the SaveJob object
                                val saveJob = SaveJob(userEmail, listOf(job._id))

                                // Log to check the structure of SaveJob
                                Log.d("SaveJob", "Saving job: $saveJob")

                                // Make the API call
                                val response = if (newFavoriteState) {
                                    apiService.addJobToFavorite(saveJob)
                                } else {
                                    apiService.deleteFavorite(saveJob)
                                }

                                // Handle the response
                                withContext(Dispatchers.Main) {
                                    if (response.isSuccessful) {
                                        isFavorite = newFavoriteState
                                        Toast.makeText(
                                            context,
                                            if (isFavorite) "Added to Favorites" else "Removed from Favorites",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Error: ${response.message()}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            } catch (e: Exception) {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        tint = if (isFavorite) Color.Red else Color.Gray,
                        contentDescription = if (isFavorite) "Remove from Favorites" else "Add to Favorites"
                    )
                }

                Button(
                    onClick = {
                        navController.navigate(
                            "jobDetailScreen/${job.jobTitle}/${job.jobDescription}/${job.jobLocation}/${job.company}/${job.minSalary}/${job.maxSalary}/${job.createdAt}/${job.minExperience}/${job.maxExperience}/${job.keySkills}/${job.createdBy}/${job.createdByEmp}/${job.shortlisted}/${job.applications}/${job.updatedAt}"
                        )
                    }
                ) {
                    Text("View and Apply")
                }
            }
        }
    }
}
