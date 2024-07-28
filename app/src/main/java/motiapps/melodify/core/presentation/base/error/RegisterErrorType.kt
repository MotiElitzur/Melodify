package motiapps.melodify.core.presentation.base.error

sealed class RegisterErrorType : BaseErrorType {
    data object WeakPassword : RegisterErrorType()
    data object UserAlreadyExists : RegisterErrorType()
}