package com.example.projobliveapp.Screens.PhoneAuth


import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projobliveapp.Navigation.Screen
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit



val auth=FirebaseAuth.getInstance()
var storedVerificationId=""

fun SignInWithPhoneAuth(
    context:Context,credential: PhoneAuthCredential,navController: NavController
){
    auth.signInWithCredential(credential)
        .addOnCompleteListener(context as Activity){task->
            if(task.isSuccessful){
                Toast.makeText(context,"login Successful",Toast.LENGTH_SHORT).show()
                navController.navigate(Screen.HomeScreen.name)
                val user= task.result?.user
            }
            else{
                if(task.exception is FirebaseAuthInvalidCredentialsException){
                    Toast.makeText(context,"wrong Otp",Toast.LENGTH_SHORT).show()
                }
            }
        }
}

fun onLoginClicked(
    context: Context,
    navController: NavController,
    phoneNumber: String,
    onCodeSend: () -> Unit
) {

    auth.setLanguageCode("en") // Set language for authentication

    val callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(p0: PhoneAuthCredential) {
            Log.d("phoneBook", "Verification completed")
            SignInWithPhoneAuth(context, p0, navController)
        }

        override fun onVerificationFailed(p0: FirebaseException) {
            Log.d("phoneBook", "Verification failed: $p0")
        }

        override fun onCodeSent(
            p0: String,
            p1: PhoneAuthProvider.ForceResendingToken
        ) {
            Log.d("phoneBook", "Code sent: $p0")
            storedVerificationId = p0
            onCodeSend() // Callback to notify that the code was sent
        }
    }

    val options = PhoneAuthOptions.newBuilder(auth)
        .setPhoneNumber("+91$phoneNumber")
        .setTimeout(60L, TimeUnit.SECONDS)
        .setActivity(context as Activity)
        .setCallbacks(callback)
        .build()

    PhoneAuthProvider.verifyPhoneNumber(options)
}


fun Verifyphonenowithcode(
    context: Context,verificationId:String,code:String,navController: NavController
){
    val p0=PhoneAuthProvider.getCredential(verificationId,code)
    SignInWithPhoneAuth(context,p0,navController)
}
