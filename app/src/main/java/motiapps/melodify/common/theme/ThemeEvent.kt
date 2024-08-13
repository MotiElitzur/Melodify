package motiapps.melodify.common.theme

import motiapps.melodify.core.presentation.base.IViewEvent

sealed class ThemeEvent: IViewEvent {
    data class ThemeDarkModeChanged(val darkMode: Boolean): ThemeEvent()
}