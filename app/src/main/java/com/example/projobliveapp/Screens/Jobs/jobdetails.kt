package com.example.projobliveapp.Screens.Jobs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTimeFilled
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.projobliveapp.Component.formatDateTime
import com.example.projobliveapp.DataBase.ApiService
import com.example.projobliveapp.DataBase.JobPost

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobDetailScreen(
    navController: NavHostController,
    jobid: String,
    apiService: ApiService,
    userEmail: String
) {
    val scrollState = rememberLazyListState()
    val isTitleVisible = remember { mutableStateOf(false) }
    val isFavorite = remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var jobData by remember { mutableStateOf<JobPost?>(null) }

    LaunchedEffect(jobid) {
        if (jobid.isNotEmpty()) {
            isLoading = true
            errorMessage = null
            try {
                jobData = apiService.jobbyid(jobid)
            } catch (e: Exception) {
                errorMessage = "Error fetching job data: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    LaunchedEffect(scrollState.firstVisibleItemIndex, scrollState.firstVisibleItemScrollOffset) {
        isTitleVisible.value =
            scrollState.firstVisibleItemIndex > 0 || scrollState.firstVisibleItemScrollOffset > 200
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (isTitleVisible.value && jobData != null) {
                        Text(
                            text = jobData?.jobTitle ?: "",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        isFavorite.value = !isFavorite.value
                    }) {
                        val icon = if (isFavorite.value) Icons.Default.Favorite else Icons.Default.FavoriteBorder
                        val iconTint = if (isFavorite.value) Color.Red else Color.Gray
                        Icon(icon, contentDescription = "Favorite", tint = iconTint)
                    }
                }
            )
        },bottomBar = {
            BottomAppBar(
                modifier = Modifier.fillMaxWidth(),
            ) {
                IconButton(
                    onClick = { navController.navigate("homeScreen/$userEmail")},
                    modifier = Modifier.weight(1f)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Home, contentDescription = "Home",)
                        Text(text = "Home", style = MaterialTheme.typography.titleSmall)
                    }
                }
                IconButton(
                    onClick = {navController.navigate("AvailableInterns/$userEmail") },
                    modifier = Modifier.weight(1f)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Groups, contentDescription = "Internships")
                        Text(text = "Internships", style = MaterialTheme.typography.titleSmall)
                    }
                }
                IconButton(
                    onClick = { navController.navigate("AvailableJobs/$userEmail")},
                    modifier = Modifier.weight(1f)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Work, contentDescription = "Jobs")
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
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (errorMessage != null) {
                Text(
                    text = errorMessage ?: "Unknown Error",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else if (jobData != null) {
                LazyColumn(
                    state = scrollState,
                    contentPadding = innerPadding
                ) {
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp)),
                            colors = CardDefaults.cardColors(Color.Transparent),
                        ) {
                            Text(
                                text = jobData?.jobTitle ?: "",
                                fontWeight = FontWeight.Bold,
                                fontSize = 28.sp,
                                color = Color.Blue,
                                modifier = Modifier.padding(16.dp)
                            )

                            Text(
                                text = jobData?.Companyname ?: "",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                            )
                            Column(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .fillMaxWidth()
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.LocationOn,
                                        contentDescription = "Location Icon",
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = jobData?.jobLocation ?: "",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Medium,
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Money,
                                        contentDescription = "Salary Icon",
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Rs ${jobData?.minSalary} - ${jobData?.maxSalary} p.a.",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Medium,
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AccessTimeFilled,
                                        contentDescription = "Posted Time Icon",
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Posted on: ${jobData?.createdAt}",
                                        fontSize = 20.sp,
                                        color = Color.Gray,
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }
                        }
                    }

                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.White)
                        )
                    }

                    item {
                        Text(
                            text = "Description",
                            fontWeight = FontWeight.Bold,
                            fontSize = 23.sp,
                            modifier = Modifier.padding(16.dp)
                        )
                        Text(
                            text = jobData?.jobDescription ?: "",
                            fontSize = 20.sp,
                            lineHeight = 24.sp,
                            modifier = Modifier.padding(16.dp)
                        )
                    }

                    item {
                        Text(
                            text = "Key Skills: ${jobData?.jobDescription ?:""} ",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                        Text(
                            text = "Experience: ${jobData?.maxExperience} - ${jobData?.minExperience} years",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                        Text(
                            text = "Posted By: ${jobData?.Companyname ?:""}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(75.dp))
                    }
                }
                FloatingActionButton(
                    onClick = { navController.navigate("Applicationscreen/${jobid}/${userEmail}") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .align(Alignment.BottomEnd)
                        .offset(y = (-125).dp),
                    containerColor = Color(0xFF1E3A8A)
                ) {
                    Text(text = "Apply", color = Color.White)
                }

            }
        }
    }
}