package com.example.kawa.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome_screen")
    object Terms : Screen("terms_screen")
    object Home : Screen("home_screen") // Placeholder for where you go after Agree
}
