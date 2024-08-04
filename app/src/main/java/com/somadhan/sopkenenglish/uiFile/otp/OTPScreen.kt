package com.somadhan.sopkenenglish.uiFile.otp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.somadhan.sopkenenglish.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OTPScreen(
    navController: NavHostController,
    email: String?,
    onOtpVerified: () -> Unit,
    otpViewModel: OTPViewModel = hiltViewModel()
) {
    var otp by remember { mutableStateOf("") }
    var otpError by remember { mutableStateOf<String?>(null) }
    val scrollState = rememberScrollState()
    val resendOtpResult by otpViewModel.resendOtpResult.observeAsState()

    fun validateOtp(): Boolean {
        otpError = if (otp.isEmpty()) {
            "OTP cannot be empty"
        } else {
            null
        }
        return otpError == null
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("OTP Verification") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(64.dp))
                Image(
                    painter = painterResource(id = R.drawable.english_logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .padding(bottom = 16.dp)
                )
                Spacer(modifier = Modifier.height(32.dp))

                // OTP input fields
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (i in 0 until 6) {
                        OutlinedTextField(
                            value = if (otp.length > i) otp[i].toString() else "",
                            onValueChange = {
                                if (it.length <= 1) {
                                    otp = otp.take(i) + it + otp.drop(i + 1)
                                }
                            },
                            modifier = Modifier
                                .width(40.dp)
                                .height(56.dp),
                            visualTransformation = PasswordVisualTransformation()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                if (otpError != null) {
                    Text(text = otpError!!, color = MaterialTheme.colorScheme.error)
                }

                Button(
                    onClick = {
                        if (validateOtp()) {
                            // Perform OTP verification logic here
                            onOtpVerified()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Submit")
                }
                Spacer(modifier = Modifier.height(16.dp))
                val annotatedString = buildAnnotatedString {
                    pushStringAnnotation(tag = "URL", annotation = "")
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary, textDecoration = TextDecoration.Underline)) {
                        append("Resend OTP")
                    }
                    pop()
                }
                ClickableText(
                    text = annotatedString,
                    onClick = { offset ->
                        annotatedString.getStringAnnotations(tag = "URL", start = offset, end = offset)
                            .firstOrNull()?.let {
                                otpViewModel.resendOtp(email)
                            }
                    }
                )
                resendOtpResult?.let {
                    if (it.isSuccessful) {
                        Text(text = "New OTP has been sent to your email", color = MaterialTheme.colorScheme.primary)
                    } else {
                        Text(text = "Failed to resend OTP", color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    )
}
