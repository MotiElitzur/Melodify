package melodify.permission.domain.model

interface PermissionResultCallback {
    fun onPermissionResult(permission: String, isGranted: Boolean)
    fun onActivityResumed()
}