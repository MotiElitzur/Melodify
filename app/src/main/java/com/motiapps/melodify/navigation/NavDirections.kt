package com.motiapps.melodify.navigation

sealed class NavDirections {

    val route: String
        get() = this::class.simpleName?.lowercase() ?: ""

    //    object Splash : NavDirections()
    object Login : NavDirections()
    object Register : NavDirections()
    object Home : NavDirections()
    object Profile : NavDirections()
    object CreateChat : NavDirections()
}