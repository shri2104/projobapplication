package com.example.projobliveapp.Screens.Login


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projobliveapp.Component.EmailInput
import com.example.projobliveapp.Component.InputField
import com.example.projobliveapp.Component.PasswordInput
import com.example.projobliveapp.Navigation.Screen
import com.example.projobliveapp.R
@ExperimentalComposeUiApi
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val showLoginForm = rememberSaveable { mutableStateOf(true) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            logo() // Display the logo at the top

            if (showLoginForm.value) {
                // Login form
                LoginForm(
                    loading = false
                ) { email, password ->
                    viewModel.signInWithEmailAndPassword(email, password) {
                        navController?.navigate(Screen.HomeScreen.name)
                    }
                }
            } else {

                RegistrationForm(
                    loading = false
                ) { email, password, firstName, lastName, location ->
                    viewModel.createUserWithEmailAndPassword(email, password, firstName, lastName, location) {
                        navController.navigate(Screen.HomeScreen.name)
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
                val actionText = if (showLoginForm.value) "Sign up" else "Log in"
                Text(
                    text = if (showLoginForm.value) "New User?" else "Already have an account?",
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = actionText,
                    modifier = Modifier
                        .clickable { showLoginForm.value = !showLoginForm.value }
                        .padding(start = 5.dp),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(20.dp))


            Button(
                onClick = {
                    navController.navigate(Screen.PhoneAuthScreen.name)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(Color.DarkGray)
            ) {
                Text(text = "Sign in with Phone Number")
            }
        }
    }
}

@Composable
fun LoginForm(
    loading: Boolean,
    onDone: (String, String) -> Unit
) {
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val passwordVisibility = rememberSaveable { mutableStateOf(false) }
    val passwordFocusRequest = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(email.value, password.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }

    Column(
        modifier = Modifier
            .height(300.dp)
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Log in", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(4.dp))

        EmailInput(
            emailState = email,
            enabled = true,
            onAction = KeyboardActions { passwordFocusRequest.requestFocus() },
        )

        PasswordInput(
            modifier = Modifier.focusRequester(passwordFocusRequest),
            passwordState = password,
            labelId = "Password",
            enabled = !loading,
            passwordVisibility = passwordVisibility,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onDone(email.value.trim(), password.value.trim())
            }
        )
    }

    SubmitButton(
        textId = "Login",
        loading = loading,
        validInputs = valid
    ) {
        onDone(email.value.trim(), password.value.trim())
        keyboardController?.hide()
    }
}

@Composable
fun RegistrationForm(
    loading: Boolean,
    onDone: (String, String, String, String, String) -> Unit
) {
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val firstName = rememberSaveable { mutableStateOf("") }
    val lastName = rememberSaveable { mutableStateOf("") }
    val location = rememberSaveable { mutableStateOf("") }
    val passwordVisibility = rememberSaveable { mutableStateOf(false) }
    val passwordFocusRequest = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(email.value, password.value, firstName.value, lastName.value, location.value) {
        email.value.trim().isNotEmpty() &&
                password.value.trim().isNotEmpty() &&
                firstName.value.trim().isNotEmpty() &&
                lastName.value.trim().isNotEmpty() &&
                location.value.trim().isNotEmpty()
    }

    Column(
        modifier = Modifier
            .height(400.dp)
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Create Account", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(4.dp))

        EmailInput(
            emailState = email,
            enabled = true,
            onAction = KeyboardActions { passwordFocusRequest.requestFocus() },
        )

        PasswordInput(
            modifier = Modifier.focusRequester(passwordFocusRequest),
            passwordState = password,
            labelId = "Password",
            enabled = !loading,
            passwordVisibility = passwordVisibility,
            onAction = KeyboardActions.Default
        )

        InputField(
            valueState = firstName,
            labelId = "First Name",
            enabled = !loading,
            onAction = KeyboardActions.Default
        )

        InputField(
            valueState = lastName,
            labelId = "Last Name",
            enabled = !loading,
            onAction = KeyboardActions.Default
        )

        InputField(
            valueState = location,
            labelId = "Location",
            enabled = !loading,
            onAction = KeyboardActions.Default
        )
    }

    SubmitButton(
        textId = "Create Account",
        loading = loading,
        validInputs = valid
    ) {
        onDone(email.value.trim(), password.value.trim(), firstName.value.trim(), lastName.value.trim(), location.value.trim())
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
