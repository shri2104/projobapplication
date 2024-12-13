package com.example.projobliveapp.Screens.Login



import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.projobliveapp.Navigation.Screen
import com.example.projobliveapp.R
import com.google.firebase.auth.FirebaseAuth

import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navController: NavHostController) {
    // Animation scale state
    val scale = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.9f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(8f).getInterpolation(it)
                }
            )
        )
        delay(2000L)
        if (FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()) {
            navController.navigate(Screen.FrontScreen.name)
        } else {
            navController.navigate(Screen.HomeScreen.name)
        }
    }

    // Centering the Splash Screen content
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .padding(15.dp)
                .size(330.dp)
                .scale(scale.value), // Apply scale animation
            shape = CircleShape,
            color = Color.White,
            border = BorderStroke(width = 2.dp, color = Color.LightGray)
        ) {
            Column(
                modifier = Modifier.padding(1.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                logo() // Logo
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "\"Simplifying Connections.\"",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.LightGray
                )
            }
        }
    }
}

@Composable
fun logo(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(2.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.projob_logo1_12fc55031a756ac453bf), // Replace with your image resource
            contentDescription = "Logo",
            modifier = Modifier
                .size(120.dp)
        )
    }
}

