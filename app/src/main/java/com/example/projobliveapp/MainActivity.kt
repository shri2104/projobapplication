package com.example.projobliveapp


import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.projobliveapp.DataBase.JobDatabase
import com.example.projobliveapp.DataBase.JobViewModel
import com.example.projobliveapp.DataBase.JobViewModelFactory
import com.example.projobliveapp.Navigation.Navigation
import com.example.projobliveapp.ui.theme.ProJobLiveAppTheme
import com.example.projobliveapp.DataBase.createApiService
import com.jakewharton.threetenabp.AndroidThreeTen


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidThreeTen.init(this)
        val apiService = createApiService()
        enableEdgeToEdge()

        // Initializing the Room database and ViewModel
        val jobDao = JobDatabase.getDatabase(this).jobDao()
        val jobViewModel: JobViewModel = ViewModelProvider(
            this,
            JobViewModelFactory(jobDao)
        )[JobViewModel::class.java]

        setContent {
            ProJobLiveAppTheme {
                Navigation(apiService = apiService, jobViewModel = jobViewModel)
            }
        }
    }
}






