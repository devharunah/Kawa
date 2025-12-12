package com.example.kawa.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
        // 1. Welcome / Onboarding Flow
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
                    // Handle decline (e.g., close app or show toast)
                },
                onAgree = {
                    // Navigate to Home or Login
                    navController.navigate(Screen.Home.route) {
                        // Pop everything up to Welcome so back button doesn't return to terms
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                }
            )
        }

        // 3. Home (Placeholder)
        composable(route = Screen.Home.route) {
            // Your HomeScreen content here
        }
    }
}
