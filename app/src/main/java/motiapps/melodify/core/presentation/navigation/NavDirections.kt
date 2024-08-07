package motiapps.melodify.core.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable // To support new compose navigation argument passing using only data classes.
sealed class NavDirections {

    val route: String
        get() = this::class.simpleName?.lowercase() ?: ""

    data object Loading : NavDirections()
    data object Login : NavDirections()
    data object Register : NavDirections()

    data object HomeFeature : NavDirections()
    data object Home : NavDirections()
    data object Profile : NavDirections()
    data object CreateChat : NavDirections()

    companion object {
        private val subclasses = NavDirections::class.sealedSubclasses

        fun fromName(name: String?): NavDirections {
            return subclasses.firstOrNull { it.simpleName.equals(name, true) }?.objectInstance ?: Loading
        }
    }
}