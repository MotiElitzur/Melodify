package motiapps.melodify.features.register.presentation

import kotlinx.parcelize.Parcelize
import motiapps.melodify.core.presentation.base.IViewState

@Parcelize
data class RegisterState (
    val isLoading: Boolean = false,
    val registrationFields: Map<RegistrationField, String> = emptyMap(),
    val fieldErrors: Map<RegistrationField, String> = emptyMap(),
    val generalError: String? = null,
    val navigateTo: String? = null
)
: IViewState