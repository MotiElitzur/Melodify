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
    private var permissionResultCallback: PermissionResultCallback? = null
    private var currentPermission: String? = null

    override fun onCreate(activity: ComponentActivity) {
        Logger.log("PermissionManager: onCreate called with activity: ${activity.localClassName}")
        this.activity = activity

        // Register the permission launcher
        permissionLauncher = activity.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            Logger.log("PermissionManager: Permission $currentPermission result received: $isGranted")
            permissionResultCallback?.onPermissionResult(currentPermission ?: "", isGranted)
        }
    }

    override fun onResume(activity: ComponentActivity?) {
        Logger.log("PermissionManager: onResume called with activity: ${activity?.localClassName}")
        permissionResultCallback?.onActivityResumed()
    }

    override fun onDestroy(activity: ComponentActivity?) {
        Logger.log("PermissionManager: onDestroy called. Clearing activity and permission launcher.")
        this.activity = null
        permissionLauncher = null
    }

    fun requestPermission(permission: String, callback: PermissionResultCallback) {
        permissionResultCallback = callback
        currentPermission = permission
        permissionLauncher?.launch(permission) ?: throw IllegalStateException("Permission launcher is not available")
    }
}
