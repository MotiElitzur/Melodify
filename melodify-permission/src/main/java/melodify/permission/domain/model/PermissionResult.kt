package melodify.permission.domain.model

sealed class PermissionResult {
    data object NotRequested : PermissionResult()
    data object Granted : PermissionResult()
    data object Denied : PermissionResult()
    data object Blocked : PermissionResult()
}