package com.example.kawa.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kawa.ui.theme.auth.LoginScreen
import com.example.kawa.ui.theme.auth.SignUpScreen
import com.example.kawa.ui.theme.welcome.screens.TermsAndConditionsScreen
import com.example.kawa.ui.theme.welcome.screens.WelcomeScreen

@Composable
fun KawaNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route
    ) {
        composable(route = Screen.Welcome.route) {
            WelcomeScreen(
                onNavigateToTerms = {
                    navController.navigate(Screen.Terms.route)
                }
            )
        }

        // 2. Terms and Conditions
        composable(route = Screen.Terms.route) {
            TermsAndConditionsScreen(
                onDecline = {
                    navController.navigate(Screen.Welcome.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                },
                onAgree = {
                    //Navigate to signup screen
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Signup.route) { inclusive = true }
                    }
                }
            )
        }
        composable(route = Screen.Signup.route) {
            SignUpScreen(
                onSignUpClicked = {
                    navController.navigate(Screen.Home.route) {
                    }
                }
            )

        }
        composable(route = Screen.Login.route) {
            LoginScreen(
                onLoginClicked = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                },
                onNavigateToSignup = {
                    navController.navigate(Screen.Signup.route)
                }
            )
        }

        composable(route = Screen.Home.route) {
            // Your HomeScreen content here
        }
    }
}
