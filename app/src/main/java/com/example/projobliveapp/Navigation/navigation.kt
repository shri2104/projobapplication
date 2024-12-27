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
import com.bawp.freader.screens.login.SignupScreen
import com.bawp.freader.screens.login.UserForm
import com.example.projobliveapp.DataBase.ApiService
import com.example.projobliveapp.Screens.Jobs.JobList
import com.example.projobliveapp.Screens.Login.SplashScreen
import com.example.projobliveapp.Screens.Menu.ContactUsPage
import com.example.projobliveapp.Screens.Menu.HelpAndSupportPage
import com.example.projobliveapp.Screens.Menu.More.MorePage
import com.example.projobliveapp.Screens.PhoneAuth.OtpScreen
import com.example.projobliveapp.Screens.PhoneAuth.PHHomeScreen

import com.example.projobliveapp.Screens.frontscreen.LoginSelectionScreen
import com.example.projobliveapp.Screens.profile.ProfilePage

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
        composable("homeScreen/{email}"){ backStackEntry ->
            val userEmail = backStackEntry.arguments?.getString("email") ?: ""
            JobAppSlidingMenuScreen(navController = navController, userEmail = userEmail)
        }
        composable(Screen.FrontScreen.name){
            LoginSelectionScreen(navController=navController)
        }
        composable("profileSection/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email")
            ScrollableProfileScreen(navController = navController, userEmail = email)
        }
        composable(Screen.PHHomeScreen.name){
            PHHomeScreen(navController=navController)
        }
        composable(Screen.OtpScreen.name){
            OtpScreen(navController=navController)
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
        composable(Screen.Userform.name){
            UserForm()
        }
        composable(Screen.Signupscreen.name){
            SignupScreen(navController=navController,apiService=apiService)
        }
        composable("profilePage/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email")
            ProfilePage(navController = navController, userEmail = email,apiService = apiService)
        }
        composable(Screen.AvailableJobs.name){
            JobList(navController=navController,apiService=apiService)
        }

    }
}
