package com.example.projobliveapp.Navigation


import AboutScreen
import JobAppSlidingMenuScreen
import ProJobSafetyTipsScreen
import SavedJobs
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bawp.freader.screens.login.LoginScreen
import com.bawp.freader.screens.login.SignupScreen
import com.bawp.freader.screens.login.UserForm
import com.example.projobliveapp.DataBase.ApiService
import com.example.projobliveapp.DataBase.Job
import com.example.projobliveapp.Screens.Jobs.JobDetailScreen
import com.example.projobliveapp.Screens.Jobs.JobList
import com.example.projobliveapp.Screens.Login.SplashScreen
import com.example.projobliveapp.Screens.Menu.ContactUsPage
import com.example.projobliveapp.Screens.Menu.HelpAndSupportPage
import com.example.projobliveapp.Screens.Menu.More.MorePage
import com.example.projobliveapp.Screens.PhoneAuth.OtpScreen
import com.example.projobliveapp.Screens.PhoneAuth.PHHomeScreen

import com.example.projobliveapp.Screens.frontscreen.LoginSelectionScreen
import com.example.projobliveapp.Screens.profile.ProfilePage
import com.example.projobliveapp.Screens.profile.ProfileSection

import com.example.projobliveapp.Screens.profile.ScrollableProfileScreen

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Navigation(apiService: ApiService){
    val savedJobs = remember { mutableStateListOf<Job>() }
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
            JobAppSlidingMenuScreen(navController = navController, userEmail = userEmail,apiservice=apiService)
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
        composable("AvailableJobs/{email}") { backStackEntry ->
            val userEmail = backStackEntry.arguments?.getString("email") ?: ""
            JobList(navController = navController, apiService = apiService, userEmail = userEmail)
        }
        composable(
            route = "jobDetailScreen/{jobTitle}/{jobDescription}/{jobLocation}/{company}/{minSalary}/{maxSalary}/{createdAt}/{minExperience}/{maxExperience}/{keySkills}/{createdBy}/{createdByEmp}/{shortlisted}/{applications}/{updatedAt}/{email}"
        ) { backStackEntry ->
            val jobTitle = backStackEntry.arguments?.getString("jobTitle") ?: ""
            val jobDescription = backStackEntry.arguments?.getString("jobDescription") ?: ""
            val jobLocation = backStackEntry.arguments?.getString("jobLocation") ?: ""
            val company = backStackEntry.arguments?.getString("company") ?: ""
            val minSalary = backStackEntry.arguments?.getString("minSalary") ?: ""
            val maxSalary = backStackEntry.arguments?.getString("maxSalary") ?: ""
            val createdAt = backStackEntry.arguments?.getString("createdAt") ?: ""
            val minExperience = backStackEntry.arguments?.getString("minExperience") ?: ""
            val maxExperience = backStackEntry.arguments?.getString("maxExperience") ?: ""
            val keySkills = backStackEntry.arguments?.getString("keySkills") ?: ""
            val createdBy = backStackEntry.arguments?.getString("createdBy") ?: ""
            val createdByEmp = backStackEntry.arguments?.getString("createdByEmp") ?: ""
            val shortlisted = backStackEntry.arguments?.getString("shortlisted") ?: ""
            val applications = backStackEntry.arguments?.getString("applications") ?: ""
            val updatedAt = backStackEntry.arguments?.getString("updatedAt") ?: ""
            val userEmail = backStackEntry.arguments?.getString("email") ?: ""

            JobDetailScreen(
                navController = navController,
                jobTitle = jobTitle,
                jobDescription = jobDescription,
                jobLocation = jobLocation,
                company = company,
                minSalary = minSalary,
                maxSalary = maxSalary,
                createdAt = createdAt,
                minExperience = minExperience,
                maxExperience = maxExperience,
                keySkills = keySkills,
                createdBy = createdBy,
                createdByEmp = createdByEmp,
                shortlisted = shortlisted,
                applications = applications,
                updatedAt = updatedAt,
                userEmail = userEmail
            )
        }
        composable("SavedJobs/{email}") { backStackEntry ->
            val userEmail = backStackEntry.arguments?.getString("email") ?: ""
            SavedJobs(
                apiService = apiService, userEmail,
                navController =navController
            )
        }
        composable("MyResume/{email}") { backStackEntry ->
            val userEmail = backStackEntry.arguments?.getString("email") ?: ""
            ProfileSection(
                apiService = apiService, userEmail,
                navController =navController
            )
        }
    }
}
