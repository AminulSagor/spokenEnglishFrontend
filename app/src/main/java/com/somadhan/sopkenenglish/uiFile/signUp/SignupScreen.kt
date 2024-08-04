package com.somadhan.sopkenenglish.uiFile.signUp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.somadhan.sopkenenglish.R
import com.somadhan.sopkenenglish.utils.isEmailAlreadyExistsError
import com.somadhan.sopkenenglish.utils.validateConfirmPassword
import com.somadhan.sopkenenglish.utils.validateEmail
import com.somadhan.sopkenenglish.utils.validatePassword
import com.somadhan.sopkenenglish.utils.validateUsername


@Composable
fun SignupScreen(navController: NavController, signupViewModel: SignupViewModel = hiltViewModel()) {
    val username by signupViewModel.username.observeAsState("")
    val email by signupViewModel.email.observeAsState("")
    val password by signupViewModel.password.observeAsState("")
    val isLoading by signupViewModel.isLoading.observeAsState(false)
    val confirmPassword by signupViewModel.confirmPassword.observeAsState("")
    val isTermsAccepted by signupViewModel.isTermsAccepted.observeAsState(false)
    val signupResult by signupViewModel.signupResult.observeAsState(SignupResult.Idle)
    var usernameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }
    val scrollState = rememberScrollState()

    fun validateInput(errorMessage: String? = null): Boolean {
        usernameError = validateUsername(username)
        emailError = validateEmail(email)
        passwordError = validatePassword(password)
        confirmPasswordError = validateConfirmPassword(password, confirmPassword)

        if (isEmailAlreadyExistsError(errorMessage)) {
            emailError = "Email already exists"
        }

        return usernameError == null && emailError == null && passwordError == null && confirmPasswordError == null
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Top,
    ) {
        Spacer(modifier = Modifier.height(64.dp))
        Image(
            painter = painterResource(id = R.drawable.english_logo), // Replace with your logo resource ID
            contentDescription = "Logo",
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(bottom = 16.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        OutlinedTextField(
            value = username,
            onValueChange = { signupViewModel.onUsernameChanged(it) },
            label = { Text("Your Name") },
            modifier = Modifier.fillMaxWidth(),
            isError = usernameError != null
        )
        if (usernameError != null) {
            Text(text = usernameError!!, color = MaterialTheme.colorScheme.error)
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { signupViewModel.onEmailChanged(it) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            isError = emailError != null
        )
        if (emailError != null) {
            Text(text = emailError!!, color = MaterialTheme.colorScheme.error)
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { signupViewModel.onPasswordChanged(it) },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            isError = passwordError != null
        )
        if (passwordError != null) {
            Text(text = passwordError!!, color = MaterialTheme.colorScheme.error)
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { signupViewModel.onConfirmPasswordChanged(it) },
            label = { Text("Confirm Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            isError = confirmPasswordError != null
        )
        if (confirmPasswordError != null) {
            Text(text = confirmPasswordError!!, color = MaterialTheme.colorScheme.error)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isTermsAccepted,
                onCheckedChange = { signupViewModel.onTermsAcceptedChanged(it) }
            )
            val annotatedString = buildAnnotatedString {
                append("Agree to ")
                pushStringAnnotation(tag = "URL", annotation = "https://trigardeningbd.vercel.app/?fbclid=IwAR29hS_hOpg6Ry7dNsWPZvaBw6uP9sDVMxQqF_CvmGBTESI8LfmbqAUUCz8_aem_AReMLuW1-Gu6dlkHgfDnQZS1BfTgFgTQfgUBpr4xOD94kwWcRbCbmm2IeCi1X5UPD5JrV3ThpSTRoeQuh9copnXL")
                withStyle(style = SpanStyle(color = Color.Blue, textDecoration = TextDecoration.Underline)) {
                    append("Terms and Conditions")
                }
                pop()
            }
            val uriHandler = LocalUriHandler.current
            ClickableText(
                text = annotatedString,
                onClick = { offset ->
                    annotatedString.getStringAnnotations(tag = "URL", start = offset, end = offset)
                        .firstOrNull()?.let { annotation ->
                            uriHandler.openUri(annotation.item)
                        }
                }
            )

        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (validateInput() && isTermsAccepted) {
                    signupViewModel.initiateSignup(username, email, password)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = isTermsAccepted
        ) {
            Text("Sign Up")
        }
        when (signupResult) {
            is SignupResult.Success -> {
                // Reset the signup result to prevent repeated navigation
                signupViewModel.resetSignupResult()
                navController.navigate("otp?email=$email") {
                    popUpTo("signup") { inclusive = false }
                }
            }
            is SignupResult.Error -> {
                val errorMessage = (signupResult as SignupResult.Error).errorMessage
                validateInput(errorMessage) // This function updates the error state
            }
            is SignupResult.Idle -> {
                // Do nothing
            }
        }
        if (isLoading) {
            // Show loading animation (e.g., CircularProgressIndicator)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x80000000)), // Semi-transparent background
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

