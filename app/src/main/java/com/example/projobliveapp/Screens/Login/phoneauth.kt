package com.example.projobliveapp.Screens.Login




import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.example.projobliveapp.Navigation.Screen

import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit


@Composable
fun LoginDialog(navController: NavHostController) {
    val dialogState: MutableState<Boolean> = remember {
        mutableStateOf(true)
    }
    if (dialogState.value) {
        Dialog(
            onDismissRequest = { dialogState.value = false },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            )
        ) {
            CompleteDialogContent(navController = navController)
        }
    }
}
val auth = FirebaseAuth.getInstance()
var storedVerificationId: String = ""
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompleteDialogContent(navController: NavHostController) {
    val context = LocalContext.current
    var phoneNumber by remember { mutableStateOf(TextFieldValue("")) }
    var otp by remember { mutableStateOf(TextFieldValue("")) }
    var isOtpVisible by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .height(400.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Login with phone number",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            TextField(
                value = phoneNumber,
                onValueChange = { if (it.text.length <= 10) phoneNumber = it },
                placeholder = { Text("Enter phone number") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.Gray,
                )
            )
            if (isOtpVisible) {
                TextField(
                    value = otp,
                    onValueChange = { otp = it },
                    placeholder = { Text("Enter OTP") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = Color.Gray,
                    )
                )
            }
            Button(
                onClick = {
                    if (!isOtpVisible) {
                        onLoginClicked(context, phoneNumber.text) {
                            isOtpVisible = true
                        }
                    } else {
                        verifyPhoneNumberWithCode(context, storedVerificationId, otp.text)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    Color.Black
                )
            ) {
                Text(
                    text = if (!isOtpVisible) "Send OTP" else "Verify",
                    color = Color.White
                )
            }
            Button(
                onClick = {
                    navController.navigate(Screen.LoginScreen.name)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    Color.Blue
                )
            ) {
                Text(
                    text = "Login with Email",
                    color = Color.White
                )
            }
        }
    }
}


private fun verifyPhoneNumberWithCode(context: Context, verificationId: String, code: String) {
    val credential = PhoneAuthProvider.getCredential(verificationId, code)
    signInWithPhoneAuthCredential(context, credential)
}

private fun getActivityFromContext(context: Context): Activity? {
    var currentContext = context
    while (currentContext is ContextWrapper) {
        if (currentContext is Activity) {
            return currentContext
        }
        currentContext = currentContext.baseContext
    }
    return null
}

fun onLoginClicked(context: Context, phoneNumber: String, onCodeSent: () -> Unit) {
    if (phoneNumber.isBlank() || phoneNumber.length != 10) {
        Toast.makeText(context, "Enter a valid phone number", Toast.LENGTH_SHORT).show()
        return
    }

    val activity = getActivityFromContext(context)
    if (activity == null) {
        Log.e("PhoneAuth", "Context is not an Activity")
        Toast.makeText(context, "Unexpected error occurred", Toast.LENGTH_SHORT).show()
        return
    }

    auth.setLanguageCode("en")
    val callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            Log.d("PhoneAuth", "Verification completed")
            signInWithPhoneAuthCredential(context, credential)
        }

        override fun onVerificationFailed(exception: FirebaseException) {
            Log.e("PhoneAuth", "Verification failed: ${exception.message}")
            Toast.makeText(context, "Verification failed: ${exception.message}", Toast.LENGTH_SHORT).show()
        }

        override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
            Log.d("PhoneAuth", "Code sent: $verificationId")
            storedVerificationId = verificationId
            onCodeSent()
        }
    }

    val options = PhoneAuthOptions.newBuilder(auth)
        .setPhoneNumber("+91$phoneNumber")
        .setTimeout(60L, TimeUnit.SECONDS)
        .setActivity(activity)
        .setCallbacks(callback)
        .build()

    PhoneAuthProvider.verifyPhoneNumber(options)
}


private fun signInWithPhoneAuthCredential(context: Context, credential: PhoneAuthCredential) {
    (context as? Activity)?.let { activity ->
        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    Log.d("phoneBook", "logged in")
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Log.d("phoneBook", "wrong OTP")
                    }
                }
            }
    }
}

