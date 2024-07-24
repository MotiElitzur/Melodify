package motiapps.melodify.core.domain.base

import motiapps.melodify.core.presentation.base.error.BaseErrorType

sealed class Resource<out T> {
    class Success<out T>(val data: T) : Resource<T>()
    class Error(val exception: Exception, val errorType: BaseErrorType = BaseErrorType.Unknown) : Resource<Nothing>()
}

//    class Success<T>(data: T) : Resource<T>(data)
//    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
//    class Loading<T>(data: T? = null) : Resource<T>(data)