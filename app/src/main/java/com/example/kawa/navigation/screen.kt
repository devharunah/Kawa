package com.example.kawa.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome_screen")
    object Terms : Screen("terms_screen")
    object Home : Screen("home_screen")
    object Signup : Screen(route = "signup_screen")
    object Login : Screen(route = "login_screen")
    object postproduct : Screen(route = "postproduct_screen")
    object ProfileScreen : Screen(route = "profile_screen")
}