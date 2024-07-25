package motiapps.melodify.features.register

import motiapps.melodify.core.presentation.base.IViewEvent
import motiapps.melodify.core.presentation.navigation.NavDirections

sealed class RegisterEvent: IViewEvent {
    data object SetContinueAsGuestState : RegisterEvent()
    data object SetLoginSuccessState : RegisterEvent()
    data class SetErrorState(val error: String) : RegisterEvent()
    data class SetEmailAndPasswordState(val email: String, val password: String) : RegisterEvent()
    data object SetStartRegister : RegisterEvent()
}