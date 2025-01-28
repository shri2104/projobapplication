package com.example.projobliveapp.Screens.PhoneAuth


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun PHHomeScreen(navController: NavController, onVerified: () -> Unit){
    val context = LocalContext.current
    var phoneNum by remember { mutableStateOf("") }
    val bgColor = Color(0xFFECFADC)
    val tColor = Color(0xFF2B472B)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Verification",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = tColor
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(text = "We will send you an One-Time Password", color = tColor)
        Text(text = "on your phone number", color = tColor)
        Spacer(modifier = Modifier.size(50.dp))

        OutlinedTextField(
            value = phoneNum,
            onValueChange = { newPhoneNum: String ->
                if (newPhoneNum.length <= 10) phoneNum = newPhoneNum
            },
            label = { Text(text = "Enter Phone Number") },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = bgColor,
                unfocusedLabelColor = bgColor,
                focusedIndicatorColor = tColor,
                cursorColor = tColor
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            textStyle = TextStyle(color = tColor)
        )
        Spacer(modifier = Modifier.size(30.dp))
        Button(
            onClick = {
                onLoginClicked(context,navController,phoneNum){
                    Log.d("PhoneBook","sending OTP")
                    navController.navigate("otp")
                }
            },
            colors = ButtonDefaults.buttonColors(
                if(phoneNum.length>=10) tColor else
                    Color.Gray
            )
        ) {
            Text(
                text = "Send OTP",
                color = Color.White
            )
        }
    }
}