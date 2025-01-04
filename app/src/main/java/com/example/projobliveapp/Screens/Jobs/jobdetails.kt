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
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Money
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobDetailScreen(
    navController: NavHostController,
    jobTitle: String,
    jobDescription: String,
    jobLocation: String,
    company: String,
    minSalary: String,
    maxSalary: String,
    createdAt: String,
    minExperience: String,
    maxExperience: String,
    keySkills: String,
    createdBy: String,
    createdByEmp: String,
    shortlisted: String,
    applications: String,
    updatedAt: String
) {
    val scrollState = rememberLazyListState()
    val isTitleVisible = remember { mutableStateOf(false) }
    val isFavorite = remember { mutableStateOf(false) }

    LaunchedEffect(scrollState.firstVisibleItemIndex, scrollState.firstVisibleItemScrollOffset) {
        isTitleVisible.value =
            scrollState.firstVisibleItemIndex > 0 || scrollState.firstVisibleItemScrollOffset > 200
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (isTitleVisible.value) {
                        Text(
                            text = jobTitle,
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
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
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
                            text = jobTitle,
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp,
                            color = Color.Blue,
                            modifier = Modifier.padding(16.dp)
                        )

                        Text(
                            text = company,
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
                                    text = jobLocation,
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
                                    text = "Rs $minSalary - $maxSalary p.a.",
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
                                    text = "${formatDateTime(createdAt)}",
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
                        text = jobDescription,
                        fontSize = 20.sp,
                        lineHeight = 24.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                item {
                    Text(
                        text = "Key Skills: $keySkills",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                    Text(
                        text = "Experience: $minExperience - $maxExperience years",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                    Text(
                        text = "Posted By: $createdBy",
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
                onClick = {  },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.BottomEnd)
                    .offset(y = (-50).dp),
                containerColor = Color(0xFF1E3A8A)
            ) {
                Text(text = "Apply", color = Color.White)
            }


        }
    }
}
