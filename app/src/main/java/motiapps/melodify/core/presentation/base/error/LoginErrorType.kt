package motiapps.melodify.core.presentation.base.error

sealed class LoginErrorType: BaseErrorType {
    data object InvalidEmail: LoginErrorType()
    data object InvalidPassword: LoginErrorType()
    data object UserNotFound: LoginErrorType()
    data object NoInternet: LoginErrorType()
}