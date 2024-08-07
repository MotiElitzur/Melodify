package motiapps.melodify.core.presentation.base.error

sealed interface BaseErrorType {
    data object Unknown: BaseErrorType
    data object Network: BaseErrorType
    data object DataStore : BaseErrorType
}