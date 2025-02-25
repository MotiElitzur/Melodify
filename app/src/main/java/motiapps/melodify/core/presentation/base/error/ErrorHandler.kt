package motiapps.melodify.core.presentation.base.error

import android.content.Context
import melodify.core.domain.BaseErrorType

class ErrorHandler(private val context: Context) {

    companion object {
        fun BaseErrorType.getErrorMessage(): String {
            return when (this) {

                LoginErrorType.InvalidEmail -> "Invalid email"
                LoginErrorType.InvalidPassword -> "Password must be at least 6 characters long"
                LoginErrorType.InvalidCredentials -> "The email or password is incorrect"
                LoginErrorType.UserNotFound -> "User not found"
                LoginErrorType.NoInternet -> "No internet connection"
                RegisterErrorType.WeakPassword -> "Password must be at least 6 characters long"
                RegisterErrorType.UserAlreadyExists -> "User already exists"

                else -> "Unknown error"
            }
        }
    }

}
