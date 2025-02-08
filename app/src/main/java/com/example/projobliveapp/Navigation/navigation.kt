package com.example.projobliveapp.Navigation


import AboutScreen

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
import com.bawp.freader.screens.login.Signup
import com.bawp.freader.screens.login.SignupScreen
import com.bawp.freader.screens.login.UserForm
import com.example.projobliveapp.DataBase.ApiService
import com.example.projobliveapp.DataBase.EducationDetails
import com.example.projobliveapp.DataBase.ExperienceDetails
import com.example.projobliveapp.DataBase.Job
import com.example.projobliveapp.DataBase.JobPost
import com.example.projobliveapp.DataBase.PersonalData
import com.example.projobliveapp.Screens.Employer.JobDetailsScreen
import com.example.projobliveapp.Screens.Employer.JobPostedScreen
import com.example.projobliveapp.Screens.Employer.JobpostScreen


import com.example.projobliveapp.Screens.Home.NotificationScreen
import com.example.projobliveapp.Screens.Inputdata.ContactDetailsScreen
import com.example.projobliveapp.Screens.Inputdata.EducationDetailsScreen
import com.example.projobliveapp.Screens.Inputdata.ExperienceDetailsScreen
import com.example.projobliveapp.Screens.Inputdata.PersonalDetailsScreen
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
            SplashScreen(navController=navController)
        }
        composable("jobpost") {
            JobpostScreen(
                navController = navController,
                apiService = apiService,
            )
        }
        composable(Screen.LoginScreen.name){
            LoginScreen(navController=navController)
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
                navController = navController
            )
        }
        composable(Screen.FrontScreen.name){
            LoginSelectionScreen(navController=navController)
        }
        composable("profileSection/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email")
            ScrollableProfileScreen(navController = navController, userEmail = email)
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
        composable("${Screen.SignUp.name}/{userType}") { backStackEntry ->
            val userType = backStackEntry.arguments?.getString("userType") ?: "Employer"
            Signup(navController = navController, userType = userType)
        }
        composable("${Screen.Signupscreen.name}/{userType}") { backStackEntry ->
            val userType = backStackEntry.arguments?.getString("userType") ?: "Candidate"
            SignupScreen(navController = navController, apiService = apiService, userType = userType)
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
        composable("Applicationscreen") {
            JobApplicationScreenPreview(
                navController = navController
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
        composable("jobDetailsScreen/{jobPost}",
            arguments = listOf(navArgument("jobPost") { type = NavType.StringType })
        ) { backStackEntry ->
            val jobPostJson = backStackEntry.arguments?.getString("jobPost")
            val jobPost = Gson().fromJson(jobPostJson, JobPost::class.java)
            JobDetailsScreen(jobPost,apiService,navController)
        }
        composable("educationDetails/{PersonalData}",
            arguments = listOf(navArgument("PersonalData") { type = NavType.StringType })
        ) { backStackEntry ->
            val personaldataJson = backStackEntry.arguments?.getString("jobPost")
            val personaldata = Gson().fromJson(personaldataJson, PersonalData::class.java)
            EducationDetailsScreen(personaldata,apiService,navController)
        }
        composable(
            "experienceDetails/{EducationData}/{PersonalData}",
            arguments = listOf(
                navArgument("PersonalData") { type = NavType.StringType },
                navArgument("EducationData") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val personaldataJson = backStackEntry.arguments?.getString("PersonalData")
            val educationdataJson = backStackEntry.arguments?.getString("EducationData")
            val personaldata = Gson().fromJson(personaldataJson, PersonalData::class.java)
            val educationdata = Gson().fromJson(educationdataJson, EducationDetails::class.java)
            ExperienceDetailsScreen(personaldata, educationdata, apiService, navController)
        }
        composable(
            "experienceDetails/{experienceDataJson}/{EducationData}/{PersonalData}",
            arguments = listOf(
                navArgument("experienceDataJson") { type = NavType.StringType },
                navArgument("EducationData") { type = NavType.StringType },
                navArgument("PersonalData") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val experienceDataJson = backStackEntry.arguments?.getString("experienceDataJson")
            val educationdataJson = backStackEntry.arguments?.getString("EducationData")
            val personaldataJson = backStackEntry.arguments?.getString("PersonalData")

            val experienceData = Gson().fromJson(experienceDataJson, ExperienceDetails::class.java)
            val educationdata = Gson().fromJson(educationdataJson, EducationDetails::class.java)
            val personaldata = Gson().fromJson(personaldataJson, PersonalData::class.java)

            ContactDetailsScreen(experienceData, educationdata, personaldata, apiService, navController)
        }

        composable("personalDetails") {
            PersonalDetailsScreen(navController)
        }

    }
}

