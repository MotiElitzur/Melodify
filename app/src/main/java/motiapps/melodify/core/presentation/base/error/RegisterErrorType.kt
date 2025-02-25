package motiapps.melodify.core.presentation.base.error

import melodify.core.domain.BaseErrorType

sealed class RegisterErrorType : BaseErrorType {
    data object WeakPassword : RegisterErrorType()
    data object UserAlreadyExists : RegisterErrorType()
}