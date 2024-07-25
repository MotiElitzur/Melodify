package motiapps.melodify.features.login

import motiapps.melodify.core.presentation.base.IViewEvent
import motiapps.melodify.core.presentation.navigation.NavDirections

sealed class LoginEvent: IViewEvent {
//    class SetLoginState(val initialScreen: NavDirections) : LoginEvent()
    data object SetContinueAsGuestState : LoginEvent()
    data object SetLoginSuccessState : LoginEvent()
    data class SetErrorState(val error: String) : LoginEvent()
    data class SetEmailAndPasswordState(val email: String, val password: String) : LoginEvent()
    data object SetStartRegister : LoginEvent()
}