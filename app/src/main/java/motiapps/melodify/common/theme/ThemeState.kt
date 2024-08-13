package motiapps.melodify.common.theme

import kotlinx.parcelize.Parcelize
import motiapps.melodify.core.presentation.base.IViewState

@Parcelize
data class ThemeState(
    val isDarkTheme: Boolean? = null
) : IViewState