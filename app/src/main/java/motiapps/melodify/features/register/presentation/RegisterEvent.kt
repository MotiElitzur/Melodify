package motiapps.melodify.features.register.presentation

import motiapps.melodify.core.presentation.base.IViewEvent

sealed class RegisterEvent: IViewEvent {
    data class UpdateField(val field: RegistrationField, val value: String) : RegisterEvent()
    data class ValidateField(val field: RegistrationField) : RegisterEvent()
    object SubmitRegistration : RegisterEvent()
}