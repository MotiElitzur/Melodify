package melodify.core.domain

sealed class Resource<out T> {
    class Success<out T>(val data: T) : Resource<T>()
    class Error(val exception: Throwable? = null, val errorType: BaseErrorType = BaseErrorType.Unknown) : Resource<Nothing>()
    data object Loading : Resource<Nothing>()
}

//sealed class Resource<out T> {
//
//    class Success<out T>(val data: T) : Resource<T>() {
//    }
//    class Error(val exception: Throwable, val errorType: BaseErrorType = BaseErrorType.Unknown) : Resource<Nothing>() {
//    }
//}