package motiapps.melodify.core.common.permissions.di

import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject
import java.util.concurrent.atomic.AtomicReference

@ActivityRetainedScoped
class ActivityContextProvider @Inject constructor() : DefaultLifecycleObserver {
    private val activityRef = AtomicReference<ComponentActivity>()
    val activity: ComponentActivity
        get() = activityRef.get() ?: throw IllegalStateException("Activity is not available")

    private var requestPermissionLauncher: ActivityResultLauncher<String>? = null
    private var permissionResultCallback: PermissionResultCallback? = null
    private var currentPermission: String? = null

    override fun onCreate(owner: LifecycleOwner) {
        activityRef.set(owner as? ComponentActivity)
        println("ActivityContextProvider: onCreate")

        activityRef.get()?.let { activity ->
            requestPermissionLauncher = activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                currentPermission?.let { permission ->
                    permissionResultCallback?.onPermissionResult(permission, isGranted)
                }
            }
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        activityRef.set(null)
        requestPermissionLauncher = null
        permissionResultCallback = null
        println("ActivityContextProvider: onDestroy")
    }

    fun requestPermission(permission: String, callback: PermissionResultCallback) {
        permissionResultCallback = callback
        currentPermission = permission
        requestPermissionLauncher?.launch(permission) ?: println("RequestPermissionLauncher is null")
    }
}
interface PermissionResultCallback {
    fun onPermissionResult(permission: String, isGranted: Boolean)
}