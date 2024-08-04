package com.somadhan.sopkenenglish

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.somadhan.sopkenenglish.ui.theme.SopkenEnglishTheme
import com.somadhan.sopkenenglish.uiFile.otp.OTPScreen
import com.somadhan.sopkenenglish.uiFile.signUp.SignupScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SopkenEnglishTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "signup") {
                    composable("signup") { SignupScreen(navController) }
                    composable("otp?email={email}") { backStackEntry ->
                        val email = backStackEntry.arguments?.getString("email")
                        OTPScreen(
                            email = email,
                            navController = navController,
                            onOtpVerified = { navController.navigate("home") }
                        )
                    }
                    composable("home") {
                        // HomeScreen content here
                    }
                }
            }
        }
    }
}
