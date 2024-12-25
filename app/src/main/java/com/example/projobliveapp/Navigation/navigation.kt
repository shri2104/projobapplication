package com.example.projobliveapp.Navigation


import AboutScreen
import JobAppSlidingMenuScreen
import ProJobSafetyTipsScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bawp.freader.screens.login.LoginScreen
import com.example.projobliveapp.DataBase.ApiService
import com.example.projobliveapp.Screens.Inputdata.JobApplicationForm
import com.example.projobliveapp.Screens.Login.SplashScreen
import com.example.projobliveapp.Screens.Menu.ContactUsPage
import com.example.projobliveapp.Screens.Menu.HelpAndSupportPage
import com.example.projobliveapp.Screens.Menu.More.MorePage
import com.example.projobliveapp.Screens.PhoneAuth.OtpScreen
import com.example.projobliveapp.Screens.PhoneAuth.PHHomeScreen

import com.example.projobliveapp.Screens.frontscreen.LoginSelectionScreen
import com.example.projobliveapp.Screens.profile.ScrollableProfileScreen

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Navigation(apiService: ApiService){
    val navController= rememberNavController()
    NavHost(navController=navController,startDestination = Screen.SplashScreen.name){
        composable(Screen.SplashScreen.name){
            SplashScreen(navController=navController)
        }
        composable(Screen.LoginScreen.name){
            LoginScreen(navController=navController)
        }
        composable(Screen.HomeScreen.name){
            JobAppSlidingMenuScreen(navController=navController)
        }
        composable(Screen.FrontScreen.name){
            LoginSelectionScreen(navController=navController)
        }
        composable(Screen.profilesection.name){
            ScrollableProfileScreen(navController=navController)
        }
        composable(Screen.profilesection.name){
            ScrollableProfileScreen(navController=navController)
        }
        composable(Screen.PHHomeScreen.name){
            PHHomeScreen(navController=navController)
        }
        composable(Screen.OtpScreen.name){
            OtpScreen(navController=navController)
        }
        composable(Screen.InputDataScreen.name){
            JobApplicationForm(navController =navController,apiService= apiService)
        }
        composable(Screen.ContactUsScreen.name){
            ContactUsPage(navController=navController)
        }
        composable(Screen.MoreScreen.name){
            MorePage(navController=navController)
        }
        composable(Screen.HelpandSupport.name){
            HelpAndSupportPage(navController=navController)
        }
        composable(Screen.SafetyTips.name){
            ProJobSafetyTipsScreen(navController=navController)
        }
        composable(Screen.AboutScreen.name){
            AboutScreen(navController=navController)
        }
    }
}
