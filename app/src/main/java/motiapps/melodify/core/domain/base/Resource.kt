package motiapps.melodify.core.domain.base

import motiapps.melodify.core.presentation.base.error.BaseErrorType

sealed class Resource<out T> {

    class Success<out T>(val data: T, val nonFatalErrorType: BaseErrorType? = null) : Resource<T>() {
        init {
            println("Success: $data")
        }
    }
    class Error(val exception: Throwable, val errorType: BaseErrorType = BaseErrorType.Unknown) : Resource<Nothing>() {

        init {
            println("Error: ${exception.message}, errorType: $errorType, exception: $exception")
        }
    }
}

//    class Success<T>(data: T) : Resource<T>(data)
//    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
//    class Loading<T>(data: T? = null) : Resource<T>(data)