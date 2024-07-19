package motiapps.melodify.features.splash.presentation

import motiapps.melodify.core.presentation.base.IViewEvent
import motiapps.melodify.core.presentation.navigation.NavDirections

sealed class SplashEvent: IViewEvent {
    class SetSplashState(val initialScreen: NavDirections) : SplashEvent()
}