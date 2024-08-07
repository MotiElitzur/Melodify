package motiapps.melodify.core.common.permissions.domain.repository

import motiapps.melodify.core.common.permissions.domain.model.PermissionResult

interface PermissionRepository {
    suspend fun checkPermission(permission: String): PermissionResult
    suspend fun requestPermission(permission: String): PermissionResult
}