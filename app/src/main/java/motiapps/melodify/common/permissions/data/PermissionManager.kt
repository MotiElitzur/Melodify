package motiapps.melodify.common.permissions.data

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import dagger.hilt.android.scopes.ActivityRetainedScoped
import motiapps.melodify.common.Logger
import motiapps.melodify.common.permissions.domain.model.PermissionResultCallback
import motiapps.melodify.core.data.lifecycle.ActivityContextProvider
import javax.inject.Inject

@ActivityRetainedScoped
class PermissionManager @Inject constructor() : ActivityContextProvider.LifecycleListener {

    var activity: ComponentActivity? = null
    private var permissionLauncher: ActivityResultLauncher<String>? = null
    private var pendingPermissionRequest: Pair<String, PermissionResultCallback>? = null

    override fun onCreate(activity: ComponentActivity) {
        Logger.log("PermissionManager: onCreate called with activity: ${activity.localClassName}")
        this.activity = activity

        // Register the permission launcher
        permissionLauncher = activity.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            Logger.log("PermissionManager: Permission result received: ${pendingPermissionRequest?.first}, granted: $isGranted")
            pendingPermissionRequest?.second?.onPermissionResult(
                pendingPermissionRequest?.first ?: "",
                isGranted
            )
            clearPendingPermissionRequest()
        }

        // Handle any pending permission requests
        pendingPermissionRequest?.let { (permission, callback) ->
            Logger.log("PermissionManager: Handling pending permission request: $permission")
            requestPermission(permission, callback)
        }
    }

    override fun onDestroy(activity: ComponentActivity?) {
        Logger.log("PermissionManager: onDestroy called. Clearing activity and permission launcher.")
        this.activity = null
        permissionLauncher = null
    }

    fun requestPermission(permission: String, callback: PermissionResultCallback) {
        Logger.log("PermissionManager: Requesting permission: $permission")
        if (permissionLauncher == null) {
            Logger.log("PermissionManager: Permission launcher is not available. Queuing permission request: $permission")
            pendingPermissionRequest = Pair(permission, callback)
        } else {
            Logger.log("PermissionManager: Launching permission request: $permission")
            permissionLauncher?.launch(permission)
            pendingPermissionRequest = Pair(permission, callback)
        }
    }

    private fun clearPendingPermissionRequest() {
        Logger.log("PermissionManager: Clearing pending permission request.")
        pendingPermissionRequest = null
    }
}
