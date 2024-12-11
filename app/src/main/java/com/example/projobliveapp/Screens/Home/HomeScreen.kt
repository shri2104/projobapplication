package com.example.projobliveapp.Screens.Home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projobliveapp.Screens.Login.LoginScreenViewModel


@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: LoginScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val userData by viewModel.userData.observeAsState()
    val loading by viewModel.loading.observeAsState(false)

    when {
        loading -> CircularProgressIndicator() // Show progress indicator
        userData == null -> Text("Error fetching user data. Please try again.") // Handle null data
        else -> Text("Welcome, ${userData?.firstName} ${userData?.lastName}!")
    }
}


