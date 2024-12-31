package motiapps.melodify.common.permissions.data

import dagger.hilt.android.scopes.ActivityRetainedScoped
import motiapps.melodify.common.permissions.domain.model.PermissionResultCallback
import motiapps.melodify.core.data.lifecycle.ActivityContextProvider
import javax.inject.Inject

@ActivityRetainedScoped
class PermissionManager @Inject constructor(
    val activityContextProvider: ActivityContextProvider
){

    fun requestPermission(permission: String, callback: PermissionResultCallback) {
        try {
            activityContextProvider.launchPermissionRequest(permission) { isGranted ->
                callback.onPermissionResult(permission, isGranted)
            }
        } catch (e: IllegalStateException) {
            throw IllegalStateException("Error launching permission request: ${e.message}")
        }
    }
}