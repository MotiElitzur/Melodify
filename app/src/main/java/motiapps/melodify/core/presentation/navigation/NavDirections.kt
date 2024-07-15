package motiapps.melodify.core.presentation.navigation

sealed class NavDirections {

    val route: String
        get() = this::class.simpleName?.lowercase() ?: ""

    object Loading : NavDirections()
    object Login : NavDirections()
    object Register : NavDirections()
    object Home : NavDirections()
    object Profile : NavDirections()
    object CreateChat : NavDirections()
}