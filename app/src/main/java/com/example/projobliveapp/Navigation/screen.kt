package com.example.projobliveapp.Navigation



enum class Screen{
    SplashScreen,
    LoginScreen,
    CreateAccountScreen,
    HomeScreen,
    FrontScreen,
    PhoneAuthScreen,
    JobSearchHomePage,
    profilesection,
    phMainScreen,
    OtpScreen,
    PHHomeScreen,
    InputDataScreen,
    ContactUsScreen,
    HelpandSupport,
    MoreScreen,
    SafetyTips,
    AboutScreen,
    MenuScreen;
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
            MenuScreen.name->MenuScreen
            phMainScreen.name->phMainScreen
            OtpScreen.name->OtpScreen
            PHHomeScreen.name->PHHomeScreen
            InputDataScreen.name->InputDataScreen
            MoreScreen.name->MoreScreen
            HelpandSupport.name->HelpandSupport
            SafetyTips.name->SafetyTips
            AboutScreen.name->AboutScreen
            ContactUsScreen.name->ContactUsScreen
            null->HomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }
}