package com.example.projobliveapp


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.projobliveapp.Navigation.Navigation
import com.example.projobliveapp.ui.theme.ProJobLiveAppTheme

import com.example.projobliveapp.DataBase.createApiService
import com.example.projobliveapp.Screens.Login.LoginScreenViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val apiService = createApiService()

        enableEdgeToEdge()
        setContent {
            ProJobLiveAppTheme {
                  Navigation(apiService = apiService)
            }
        }
    }
}





