package com.bawp.freader.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.projobliveapp.Component.EmailInput
import com.example.projobliveapp.Component.PasswordInput
import com.example.projobliveapp.DataBase.ApiService
import com.example.projobliveapp.Navigation.Screen
import com.example.projobliveapp.R
import com.example.projobliveapp.Screens.Inputdata.JobApplicationForm
import com.example.projobliveapp.Screens.Login.LoginScreenViewModel
import com.example.projobliveapp.Screens.Login.logo



@ExperimentalComposeUiApi
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val userType = rememberSaveable { mutableStateOf("Candidate") }
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            logo()
            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "Candidate",
                    fontWeight = if (userType.value == "Candidate") FontWeight.Bold else FontWeight.Normal,
                    modifier = Modifier.clickable { userType.value = "Candidate" },
                    color = if (userType.value == "Candidate") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Employer",
                    fontWeight = if (userType.value == "Employer") FontWeight.Bold else FontWeight.Normal,
                    modifier = Modifier.clickable { userType.value = "Employer" },
                    color = if (userType.value == "Employer") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                )
            }
            UserForm(
                loading = false,
                isCreateAccount = false
            ) { email, password ->
                viewModel.signInWithEmailAndPassword(email, password) {
                    // Conditional navigation based on userType
                    if (userType.value == "Candidate") {
                        navController.navigate("homeScreen/$email")
                    } else {
                        navController.navigate("Employersignup/$email")
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "New User?", style = MaterialTheme.typography.labelLarge)
                Text(
                    text = "Sign up",
                    modifier = Modifier
                        .clickable {
                            if (userType.value == "Candidate") {
                                navController.navigate("${Screen.Signupscreen.name}/Candidate")
                            } else {
                                navController.navigate("${Screen.EmployerSignUP.name}/Employer")
                            }
                        }
                        .padding(start = 5.dp),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    navController.navigate(Screen.PHHomeScreen.name)
                },
                modifier = Modifier.padding(horizontal = 12.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
            ) {
                Text(text = "Login with Phone Number")
            }
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun SignupScreen(
    navController: NavController,
    viewModel: LoginScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    apiService: ApiService,
    userType: String // Pass the userType from LoginScreen
) {
    var showUserForm by remember { mutableStateOf(false) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            if (!showUserForm) {
                JobApplicationForm(navController = navController, apiService = apiService) {
                    showUserForm = true
                }
            } else {
                logo()
                UserForm(
                    loading = false,
                    isCreateAccount = true
                ) { email, password ->
                    viewModel.createUserWithEmailAndPassword(email, password) {
                        if (userType == "Candidate") {
                            navController.navigate("homeScreen/$email")
                        } else {
                            navController.navigate("EmployerHomeScreen/$email") // Specific employer navigation
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            if (showUserForm) {
                Button(
                    onClick = {
                        if (userType == "Candidate") {
                            navController.navigate(Screen.Signupscreen.name) // Candidate Login Screen
                        } else {
                            navController.navigate(Screen.EmployerSignUP.name) // Employer Login Screen
                        }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                ) {
                    Text(text = "Already have an account? Log in")
                }
            }
        }
    }
}


@Composable
fun UserForm(
    loading: Boolean = false,
    isCreateAccount: Boolean = false,
    onDone: (String, String) -> Unit = { email, pwd ->}
) {
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val passwordVisibility = rememberSaveable { mutableStateOf(false) }
    val passwordFocusRequest = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(email.value, password.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }
    val modifier = Modifier
        .height(250.dp)
        .background(MaterialTheme.colorScheme.background)
        .verticalScroll(rememberScrollState())
    Column(modifier,
        horizontalAlignment = Alignment.CenterHorizontally) {
        if (isCreateAccount) Text(text = stringResource(id = R.string.create_acct),
            modifier = Modifier.padding(4.dp)) else Text("")
        EmailInput(
            emailState = email, enabled = true,
            onAction = KeyboardActions {
                passwordFocusRequest.requestFocus()
            },
        )
        PasswordInput(
            modifier = Modifier.focusRequester(passwordFocusRequest),
            passwordState = password,
            labelId = "Password",
            enabled = !loading, //Todo change this
            passwordVisibility = passwordVisibility,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onDone(email.value.trim(), password.value.trim())
            })
    }
    SubmitButton(
        textId = if (isCreateAccount) "Create Account" else "Login",
        loading = loading,
        validInputs = valid
    ){
        onDone(email.value.trim(), password.value.trim())
        keyboardController?.hide()
    }
}
@Composable
fun SubmitButton(textId: String,
                 loading: Boolean,
                 validInputs: Boolean,
                 onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        enabled = !loading && validInputs,
        shape = CircleShape
    ) {
        if (loading) CircularProgressIndicator(modifier = Modifier.size(25.dp))
        else Text(text = textId, modifier = Modifier.padding(5.dp))

    }

}
