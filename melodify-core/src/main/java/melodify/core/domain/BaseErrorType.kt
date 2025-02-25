package melodify.core.domain

interface BaseErrorType {
    data object Unknown: BaseErrorType
    data object Network: BaseErrorType
    data object DataStore : BaseErrorType
}