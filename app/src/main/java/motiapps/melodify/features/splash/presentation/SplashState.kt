package motiapps.melodify.features.splash.presentation

import motiapps.melodify.core.presentation.base.BaseSavedState
import motiapps.melodify.core.presentation.navigation.NavDirections

data class SplashState(
    val isLoading: Boolean,
    val initialRoute: NavDirections? = null
): BaseSavedState()