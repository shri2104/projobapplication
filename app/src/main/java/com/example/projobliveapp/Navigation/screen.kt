package com.example.projobliveapp.Navigation



enum class Screen{
    SplashScreen,
    LoginScreen,
    CreateAccountScreen,
    HomeScreen,
    FrontScreen,
    PhoneAuthScreen,
    JobSearchHomePage,
    profilesection;
    companion object{
        fun fromRoute(route: String?):Screen
                =when(route?.substringBefore('/')){
            SplashScreen.name->SplashScreen
            LoginScreen.name->LoginScreen
            CreateAccountScreen.name->CreateAccountScreen
            HomeScreen.name->HomeScreen
            FrontScreen.name->FrontScreen
            PhoneAuthScreen.name->PhoneAuthScreen
            JobSearchHomePage.name->JobSearchHomePage
            profilesection.name->profilesection
            null->HomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }
}