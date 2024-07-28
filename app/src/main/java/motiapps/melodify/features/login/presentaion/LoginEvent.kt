package motiapps.melodify.features.login.presentaion

import motiapps.melodify.core.presentation.base.IViewEvent
import motiapps.melodify.features.login.LoginData

sealed class LoginEvent: IViewEvent {
    data object SetLoginSuccessState : LoginEvent()
    data class SetErrorState(val error: String) : LoginEvent()
    data class SetLoginDataState(val loginData: LoginData) : LoginEvent()
    data object SaveToNextState : LoginEvent()
    data object SetContinueAsGuestState : LoginEvent()
    data object SetStartRegister : LoginEvent()
}