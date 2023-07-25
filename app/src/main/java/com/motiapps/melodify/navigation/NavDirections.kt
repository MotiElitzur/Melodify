package com.motiapps.melodify.navigation

sealed class NavDirections(val route: String) {
    object Splash : NavDirections("splash")
    object Login : NavDirections("login")
    object Register : NavDirections("register")
    object Home : NavDirections("home")
    object Profile : NavDirections("profile")
    object CreateChat : NavDirections("create_chat")
}