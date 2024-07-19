package motiapps.melodify.features.splash.presentation

import kotlinx.parcelize.Parcelize
import motiapps.melodify.core.presentation.base.IViewState

@Parcelize
data class SplashState(
    val isLoading: Boolean,
    val initialRoute: String? = null
): IViewState