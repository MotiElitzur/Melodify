package melodify.permission.domain.repository

import melodify.permission.domain.model.PermissionResult

interface PermissionRepository {
    suspend fun checkPermission(permission: String): PermissionResult
    suspend fun requestPermission(permission: String): PermissionResult
    fun openAppSettings()
}