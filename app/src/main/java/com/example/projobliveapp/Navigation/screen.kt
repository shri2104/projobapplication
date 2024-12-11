package com.example.projobliveapp.Navigation



enum class Screen{
    SplashScreen,
    LoginScreen,
    CreateAccountScreen,
    HomeScreen,
    SearchScreen,
    PhoneAuthScreen,
    UpdateScreen,
    ReaderStatsScreen;
    companion object{
        fun fromRoute(route: String?):Screen
                =when(route?.substringBefore('/')){
            SplashScreen.name->SplashScreen
            LoginScreen.name->LoginScreen
            CreateAccountScreen.name->CreateAccountScreen
            HomeScreen.name->HomeScreen
            SearchScreen.name->SearchScreen
            PhoneAuthScreen.name->PhoneAuthScreen
            UpdateScreen.name->UpdateScreen
            ReaderStatsScreen.name->ReaderStatsScreen
            null->HomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }
}