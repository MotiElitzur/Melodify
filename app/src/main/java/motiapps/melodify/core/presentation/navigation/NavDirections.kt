package motiapps.melodify.core.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable // To support new compose navigation argument passing using only data classes.
sealed class NavDirections {

    val route: String
        get() = this::class.simpleName?.lowercase() ?: ""

    object Loading : NavDirections()
    object Login : NavDirections()
    object Register : NavDirections()

    object HomeFeature : NavDirections()
    object Home : NavDirections()
    object Profile : NavDirections()
    object CreateChat : NavDirections()
}