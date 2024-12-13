package com.example.projobliveapp.Navigation




import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projobliveapp.Screens.Login.LoginDialog
import com.example.projobliveapp.Screens.Login.LoginScreen


import com.example.projobliveapp.Screens.Login.SplashScreen
import com.example.projobliveapp.Screens.frontscreen.LoginSelectionScreen

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Navigation(){
    val navController= rememberNavController()
    NavHost(navController=navController,startDestination = Screen.SplashScreen.name){
        composable(Screen.SplashScreen.name){
            SplashScreen(navController=navController)
        }
        composable(Screen.LoginScreen.name){
            LoginScreen(navController=navController)
        }
        composable(Screen.HomeScreen.name){
//            Screen.HomeScreen(navController = navController)
        }
        composable(Screen.PhoneAuthScreen.name){
            LoginDialog(navController=navController)
        }
        composable(Screen.FrontScreen.name){
            LoginSelectionScreen(navController=navController)
        }

    }
}