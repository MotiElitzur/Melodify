package motiapps.melodify.core.presentation.base.error

import melodify.core.domain.BaseErrorType

sealed class LoginErrorType: BaseErrorType {
    data object InvalidEmail: LoginErrorType()
    data object InvalidPassword: LoginErrorType()
    data object InvalidCredentials: LoginErrorType()
    data object UserNotFound: LoginErrorType()
    data object NoInternet: LoginErrorType()
}