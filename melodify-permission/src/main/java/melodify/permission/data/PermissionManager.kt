package melodify.permission.data

import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import dagger.hilt.android.scopes.ActivityRetainedScoped
import melodify.core.domain.Logger
import melodify.core.domain.lifecycle.ActivityContextProvider
import melodify.permission.domain.model.PermissionResultCallback
import javax.inject.Inject

@ActivityRetainedScoped
class PermissionManager @Inject constructor() : ActivityContextProvider.LifecycleListener {

    var activity: ComponentActivity? = null
    private var permissionLauncher: ActivityResultLauncher<String>? = null
    private var permissionResultCallback: PermissionResultCallback? = null
    private var currentPermission: String? = null

    init {
        ActivityContextProvider.addListener(this)
    }

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
        permissionLauncher = null
        this.activity = null
    }

    fun requestPermission(permission: String, callback: PermissionResultCallback) {
        permissionResultCallback = callback
        currentPermission = permission
        permissionLauncher?.launch(permission) ?: throw IllegalStateException("Permission launcher is not available")
    }
}
