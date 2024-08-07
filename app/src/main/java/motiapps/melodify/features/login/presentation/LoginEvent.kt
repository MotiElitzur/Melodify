package motiapps.melodify.features.login.presentation

import motiapps.melodify.core.presentation.base.IViewEvent

sealed class LoginEvent : IViewEvent {
    data class EmailChanged(val email: String) : LoginEvent()
    data class PasswordChanged(val password: String) : LoginEvent()
    data class FieldFocusChanged(val field: LoginField, val isFocused: Boolean) : LoginEvent()
    data object LoginClicked : LoginEvent()
    data object ContinueAsGuestClicked : LoginEvent()
    data object RegisterClicked : LoginEvent()
    data object NavigationHandled : LoginEvent()
}