package com.example.kawa.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.kawa.auth.LoginScreen
import com.example.kawa.auth.SignUpScreen
import com.example.kawa.auth.classes.AuthManager
import com.example.kawa.navigation.components.KawaBottomBar
import com.example.kawa.navigation.screens.Screen
import com.example.kawa.ui.theme.products.PostProductScreen
import com.example.kawa.ui.theme.profile.screens.ProfileScreen
import com.example.kawa.ui.theme.welcome.screens.TermsAndConditionsScreen
import com.example.kawa.ui.theme.welcome.screens.WelcomeScreen

@Composable
fun KawaNavGraph(
    navController: NavHostController = rememberNavController()
) {
    // Determine if the bottom bar should be shown
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // List of screens where the bottom bar is visible
    val bottomBarRoutes = listOf(
        Screen.Home.route,
        Screen.ProfileScreen.route,
        Screen.Postproduct.route,
        "coffee_screen"
    )

    Scaffold(
        bottomBar = {
            // Only show bottom bar if current route is in the list
            if (currentRoute in bottomBarRoutes) {
                KawaBottomBar(navController = navController)
            }
        }
    ) { innerPadding ->
        // The NavHost sits inside the Scaffold's padding
        NavHost(
            navController = navController,
            startDestination = Screen.Welcome.route, // Or Screen.Login.route depending on auth state
            modifier = Modifier.padding(innerPadding)
        ) {
            // --- AUTH FLOW (No Bottom Bar) ---
            composable(route = Screen.Welcome.route) {
                WelcomeScreen(onNavigateToTerms = { navController.navigate(Screen.Terms.route) })
            }
            composable(route = Screen.Terms.route) {
                TermsAndConditionsScreen(
                    onDecline = { navController.popBackStack() },
                    onAgree = { navController.navigate(Screen.Login.route) }
                )
            }
            composable(route = Screen.Login.route) {
                LoginScreen(
                    onLoginClicked = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Welcome.route) { inclusive = true }
                        }
                    },
                    onNavigateToSignup = { navController.navigate(Screen.Signup.route) }
                )
            }
            composable(route = Screen.Signup.route) {
                SignUpScreen(
                    onSignUpClicked = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Welcome.route) { inclusive = true }
                        }
                    }
                )
            }

            // --- MAIN APP FLOW (Has Bottom Bar) ---

            // 1. HOME
            composable(route = Screen.Home.route) {
                // Your actual Home Screen
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Home Screen (Coming Soon)")
                }
            }

            // 2. PRODUCTS (Currently Post Product)
            composable(route = Screen.Postproduct.route) {
                PostProductScreen(
                    onNavigateBack = { navController.navigate(Screen.Home.route) },
                    onPostSuccess = { navController.navigate(Screen.Home.route) }
                )
            }

            // 3. COFFEE (Placeholder)
            composable(route = "coffee_screen") {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Coffee Screen (Coming Soon)")
                }
            }

            // 4. PROFILE
            composable(route = Screen.ProfileScreen.route) {
                ProfileScreen(
                    onNavigateBack = { navController.navigate(Screen.Home.route) },
                    onLogout = {
                        AuthManager().signOut()
                        navController.navigate(Screen.Welcome.route) {
                            popUpTo(0)
                        }
                    }
                )
            }
        }
    }
}
