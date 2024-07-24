package motiapps.melodify.core.presentation.base.error

sealed interface BaseErrorType {
    data object Unknown: BaseErrorType
}