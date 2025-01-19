package motiapps.melodify.common.permissions.domain.model

interface PermissionResultCallback {
    fun onPermissionResult(permission: String, isGranted: Boolean)
    fun onActivityResumed()
}