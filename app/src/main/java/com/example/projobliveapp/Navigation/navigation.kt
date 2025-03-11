package com.example.projobliveapp.Navigation


import AboutScreen
import DownloadScreen

import JobAppSlidingMenuScreen
import JobPostingScreen
import ProJobSafetyTipsScreen
import SavedJobs
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bawp.freader.screens.login.LoginScreen
import com.bawp.freader.screens.login.SignupScreen
import com.bawp.freader.screens.login.UserForm
import com.example.projobliveapp.DataBase.ApiService
import com.example.projobliveapp.DataBase.Job
import com.example.projobliveapp.DataBase.JobPost
import com.example.projobliveapp.Screens.Employer.CandidateApplications
import com.example.projobliveapp.Screens.Employer.CompanyLogo
import com.example.projobliveapp.Screens.Employer.CompanyProfileScreen
import com.example.projobliveapp.Screens.Employer.postedJobs
import com.example.projobliveapp.Screens.Employer.JobDetailsScreen
import com.example.projobliveapp.Screens.Employer.JobPostedScreen
import com.example.projobliveapp.Screens.Employer.JobpostScreen
import com.example.projobliveapp.Screens.Employer.downloadresume


import com.example.projobliveapp.Screens.Home.NotificationScreen
import com.example.projobliveapp.Screens.Jobs.InternshipList
import com.example.projobliveapp.Screens.Jobs.JobApplicationScreenPreview
import com.example.projobliveapp.Screens.Jobs.JobDetailScreen
import com.example.projobliveapp.Screens.Jobs.JobList
import com.example.projobliveapp.Screens.Jobs.ShowApplicationScreen
import com.example.projobliveapp.Screens.Login.EmployerDetailsScreen
import com.example.projobliveapp.Screens.Login.SplashScreen
import com.example.projobliveapp.Screens.Menu.ContactUsPage
import com.example.projobliveapp.Screens.Menu.HelpAndSupportPage
import com.example.projobliveapp.Screens.Menu.More.MorePage
import com.example.projobliveapp.Screens.PhoneAuth.OtpScreen
import com.example.projobliveapp.Screens.PhoneAuth.PHHomeScreen
import com.example.projobliveapp.Screens.frontscreen.LoginSelectionScreen
import com.example.projobliveapp.Screens.profile.ContactInfoScreen
import com.example.projobliveapp.Screens.profile.EducationDetailsScreen
import com.example.projobliveapp.Screens.profile.ExperienceDetailsScreen
import com.example.projobliveapp.Screens.profile.PersonalInformationScreen
import com.example.projobliveapp.Screens.profile.ProfilePage
import com.example.projobliveapp.Screens.profile.ProfileSection
import com.example.projobliveapp.Screens.profile.ScrollableProfileScreen
import com.google.gson.Gson

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Navigation(apiService: ApiService){
    val savedJobs = remember { mutableStateListOf<Job>() }
    val navController= rememberNavController()

    NavHost(navController=navController,startDestination = Screen.SplashScreen.name){
        composable(Screen.SplashScreen.name){
            SplashScreen(navController=navController,apiService)
        }
        composable("jobpost/{employerid}/{userEmail}") {backStackEntry ->
            val employerid = backStackEntry.arguments?.getString("employerid") ?: ""
            val userEmail = backStackEntry.arguments?.getString("userEmail") ?: ""
            JobpostScreen(
                navController = navController,
                apiService = apiService,employerid,userEmail
            )
        }
        composable("CandidateApplications/{jobid}/{userEmail}/{employerid}") {backStackEntry ->
            val employerid = backStackEntry.arguments?.getString("employerid") ?: ""
            val jobid = backStackEntry.arguments?.getString("jobid") ?: ""
            val userEmail = backStackEntry.arguments?.getString("userEmail") ?: ""
            CandidateApplications(
                apiService = apiService,userEmail, jobid,employerid,navController
            )
        }
        composable(Screen.LoginScreen.name){
            LoginScreen(navController=navController,apiService)
        }
        composable("${Screen.EmployerSignUP.name}/{userType}") { backStackEntry ->
            val userType = backStackEntry.arguments?.getString("userType") ?: "Candidate"
            EmployerDetailsScreen(
                navController = navController,
                apiService = apiService,
                userType = userType
            )
        }
        composable("homeScreen/{email}"){ backStackEntry ->
            val userEmail = backStackEntry.arguments?.getString("email") ?: ""
            JobAppSlidingMenuScreen(navController = navController, userEmail = userEmail,apiservice=apiService)
        }
        composable("EmployerHomeScreen/{email}"){ backStackEntry ->
            val userEmail = backStackEntry.arguments?.getString("email") ?: ""
            JobPostingScreen(
                navController = navController,userEmail,apiService
            )
        }
        composable("postedjobs/{employerid}/{userEmail}"){ backStackEntry ->
            val employerid = backStackEntry.arguments?.getString("employerid") ?: ""
            val userEmail = backStackEntry.arguments?.getString("userEmail") ?: ""
            postedJobs(
                navController = navController,employerid,apiService,userEmail
            )
        }
        composable(Screen.FrontScreen.name){
            LoginSelectionScreen(navController=navController)
        }
        composable("profileSection/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email")
            ScrollableProfileScreen(navController = navController, userEmail = email)
        }
        composable("candidateresume/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?:""
            downloadresume(apiService,userId)
        }
        composable("logo/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?:""
            CompanyLogo(userId,apiService)
        }
        composable(Screen.PHHomeScreen.name){
            PHHomeScreen(
                navController = navController,
            )
        }
        composable(Screen.OtpScreen.name){
            OtpScreen(navController=navController)
        }
        composable(Screen.ContactUsScreen.name){
            ContactUsPage(navController=navController)
        }
        composable("CompanyProfileScreen/{email}"){backStackEntry->
            val email = backStackEntry.arguments?.getString("email")
            CompanyProfileScreen(navController=navController,apiService,email)
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
        composable("${Screen.Signupscreen.name}/{userType}") { backStackEntry ->
            val userType = backStackEntry.arguments?.getString("userType") ?: "Candidate"
            SignupScreen(navController = navController, apiService = apiService, userType = userType)
        }
        composable("profilePage/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email")
            ProfilePage(navController = navController, userEmail = email,apiService = apiService)
        }
        composable("candidatepersonalinfo/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email")
            if (email != null) {
                PersonalInformationScreen(
                    navController = navController,
                    userEmail =email,
                    apiService = apiService,
                )
            }
        }
        composable("candidateeducationinfo/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email")
            if (email != null) {
                EducationDetailsScreen(
                    navController = navController,
                    userEmail =email,
                    apiService = apiService,
                )
            }
        }
        composable("candidateexperienceinfo/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email")
            if (email != null) {
                ExperienceDetailsScreen(
                    navController = navController,
                    userEmail =email,
                    apiService = apiService,
                )
            }
        }

        composable("candidatecontactinfo/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email")
            if (email != null) {
                ContactInfoScreen(
                    navController = navController,
                    userEmail =email,
                    apiService = apiService,
                )
            }
        }
        composable("AvailableJobs/{email}") { backStackEntry ->
            val userEmail = backStackEntry.arguments?.getString("email") ?: ""
            JobList(navController = navController, apiService = apiService, userEmail = userEmail)
        }
        composable("AvailableInterns/{email}") { backStackEntry ->
            val userEmail = backStackEntry.arguments?.getString("email") ?: ""
            InternshipList(navController = navController, apiService = apiService, userEmail = userEmail)
        }
        composable(
            route = "jobDetailScreen/{jobid}/{userEmail}"
        ) { backStackEntry ->
            val jobid = backStackEntry.arguments?.getString("jobid") ?: ""
            val userEmail = backStackEntry.arguments?.getString("userEmail") ?: "" // Fixed here
            JobDetailScreen(
                navController,
                jobid,
                apiService,
                userEmail
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
        composable("Applicationscreen/{jobid}/{userEmail}/{employerid}") {backStackEntry ->
            val jobid = backStackEntry.arguments?.getString("jobid") ?: ""
            val employerid = backStackEntry.arguments?.getString("employerid") ?: ""
            val userEmail = backStackEntry.arguments?.getString("userEmail") ?: ""
            JobApplicationScreenPreview(
                navController = navController,
                jobid,apiService,userEmail,employerid
            )
        }
        composable("Jobposted") {
            JobPostedScreen(
                navController = navController
            )
        }
        composable("notificationscreen/{email}") { backStackEntry ->
            val userEmail = backStackEntry.arguments?.getString("email") ?: ""
            NotificationScreen(
                navController = navController,
                userEmail = userEmail
            )
        }
        composable("showApplications/{email}") { backStackEntry ->
            val userEmail = backStackEntry.arguments?.getString("email") ?: ""
            ShowApplicationScreen(
                navController = navController,
                userEmail = userEmail
            )
        }
        composable("showresume/{email}") { backStackEntry ->
            val userEmail = backStackEntry.arguments?.getString("email") ?: ""
            DownloadScreen(
                apiService,
                userEmail = userEmail
            )
        }
        composable("jobDetailsScreen/{jobPost}",
            arguments = listOf(navArgument("jobPost") { type = NavType.StringType })
        ) { backStackEntry ->
            val jobPostJson = backStackEntry.arguments?.getString("jobPost")
            val jobPost = Gson().fromJson(jobPostJson, JobPost::class.java)
            JobDetailsScreen(jobPost,apiService,navController)
        }

//        composable("JobApplicationscreen") {
//            JobApplicationForm(navController,apiService)
//        }

    }
}

